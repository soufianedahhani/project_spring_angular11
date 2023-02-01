package com.dev.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dev.entities.User;
import com.dev.requests.PasswordRequest;
import com.dev.responses.MessageResponse;
import com.dev.service.UserService;



@RestController
@RequestMapping("/users")
@CrossOrigin("*")
//@PreAuthorize("hasAnyRole('ADMIN','USER')")
//@Api(description = "classe controller pour l'utilisateur")
public class UserController {
	
	@Autowired
	private UserService userService;
		
	//@RequestMapping(value="/list",method=RequestMethod.GET)
	
	//@ApiOperation(value = "c'est une methode aui affiche la liste d'utilisateur")
	@GetMapping
	@PreAuthorize("hasRole('ADMIN')")
	public List<User> findAll() {
		return userService.findAll();
		
	}
	@PostMapping
	@PreAuthorize("hasRole('ADMIN','USER')")
	//@ApiOperation(value = "c'est une methode pour l'enregistrement des données")
	public MessageResponse save(@RequestBody User user) {
		
		return userService.save(user);
	}
	@PutMapping
	@PreAuthorize("hasRole('ADMIN')")
	public MessageResponse update(@RequestBody User user) {
		return userService.update(user);
	}
	@DeleteMapping("/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	public MessageResponse delete(@PathVariable("id")Integer id) {
		
		return userService.delete(id);
	}
	@GetMapping("/{id}")
	@PreAuthorize("hasRole('ADMIN','USER')")
	public User findById(@PathVariable Integer id) {
		return userService.findById(id);
	}
	@PatchMapping
	public MessageResponse changePassword(@RequestBody PasswordRequest passwordRequest) {
		return userService.changePassword(passwordRequest);
	}
}
