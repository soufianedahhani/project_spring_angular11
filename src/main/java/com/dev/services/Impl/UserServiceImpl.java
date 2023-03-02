package com.dev.services.Impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dev.entities.User;
import com.dev.repository.ContactRepository;
import com.dev.repository.UserRepository;
import com.dev.requests.PasswordRequest;
import com.dev.responses.LoginResponse;
import com.dev.responses.MessageResponse;
import com.dev.service.UserService;
import com.dev.util.JwtUtil;
@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private ContactRepository contactRepository;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	@Lazy
	private PasswordEncoder passwordEncoder;
	@Autowired 
	@Lazy
	private AuthenticationManager authenticateManger;
	@Autowired
	private JwtUtil jwtUtil;

	
	@Override
	public MessageResponse save(User user) {
		boolean exist = contactRepository.existsByEmail(user.getEmail());
		if (exist) {
			return new MessageResponse(false, "Attention", "Email existe déjà");
		}
		exist = userRepository.existsByUsername(user.getUsername());
		if (exist) {
			return new MessageResponse(false, "Attention", "Nom d'utilisateur existe déjà");
		}
		String encodedPassword = passwordEncoder.encode(user.getPassword());
		user.setPassword(encodedPassword);

		userRepository.save(user);
		return new MessageResponse(true, "Succès", "Opération effectuée");
	}

	
	@Override
	public MessageResponse update(User user) {

		if (findById(user.getId()) == null) {
			return new MessageResponse(false, "Attention", "Utilisateur n'existe pas");
		}
		boolean exist = contactRepository.existsByIdAndEmail(user.getId(), user.getEmail());

		if (!exist) {
			exist = contactRepository.existsByEmail(user.getEmail());
			if (exist) {
				return new MessageResponse(false, "Attention", "Email existe déjà");
			}
		}

		exist = userRepository.existsByIdAndUsername(user.getId(), user.getUsername());

		if (!exist) {
			exist = userRepository.existsByUsername(user.getUsername());
			if (exist) {
				return new MessageResponse(false, "Attention", "Nom d'utilisateur existe déjà");
			}
		}

		userRepository.save(user);

		return new MessageResponse(true, "Succès", "Opération effectuée");
	}	
	@Override
	public MessageResponse delete(Integer id) {
		if (findById(id) == null) {
			return new MessageResponse(false, "Attention", "Utilisateur n'existe pas");
		}
		userRepository.deleteById(id);
		return new MessageResponse(true, "Succès", "Opération effectuée");
	}

	
	@Override
	@Transactional(readOnly = true)

	public List<User> findAll() {
		// TODO Auto-generated method stub
		return userRepository.findAll();
	}

	
	@Override
	public User findById(Integer id) {
		
		return userRepository.findById(id).orElse(null);
	}


	@Override
	public MessageResponse changePassword(PasswordRequest passwordRequest) {
		User user = userRepository.findById(passwordRequest.getId()).orElse(null);

		if (user == null) {
			return new MessageResponse(false, "Attention", "Utilisateur n'existe pas");
		}

		boolean matched = passwordEncoder.matches(passwordRequest.getOldPassword(), user.getPassword());

		if (!matched) {
			return new MessageResponse(false, "Attention", "Ancien mot de passe incorrecte");
		}

		String encodedPassword = passwordEncoder.encode(passwordRequest.getNewPassword());
		user.setPassword(encodedPassword);

		userRepository.save(user);
		return new MessageResponse(true, "Succès", "Opération effectuée");
		
	}
	@Override
	public LoginResponse authentication(User user) {
		
		UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
				user.getUsername(), user.getPassword());

		Authentication auth = authenticateManger.authenticate(usernamePasswordAuthenticationToken);

		String token = jwtUtil.generateToken(auth);

		LoginResponse loginResponse = new LoginResponse();
		loginResponse.setToken(token);
		loginResponse.setUser(userRepository.findOneByUsername
				(user.getUsername()).orElse(null));
		return loginResponse;
	}


	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		User user =userRepository.findOneByUsername(username).orElse(null);
		return user;
	}
	
	
}


