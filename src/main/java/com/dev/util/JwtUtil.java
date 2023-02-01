package com.dev.util;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm
;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;

@Component
public class JwtUtil {
	
	@Value("${jwt.secret-key}")
	private String secretKey;
	
	 @Value("${jwt.prefix}")
	 private String prefix;
	
	
	 
	@Value("${jwt.exp-time}")
	private long expirationTime;
	public String generateToken(Authentication auth) {
		
		String username = auth.getName();
		List<String> roles = new ArrayList<>();

		auth.getAuthorities()
		.forEach(authority -> roles.add(authority.getAuthority()));

		String token = Jwts.builder().setSubject(username)
				.claim("roles", roles)
				.setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() + expirationTime))
				.signWith(SignatureAlgorithm.HS512, secretKey).compact();
		return prefix + " " + token;
		
		
	}
	public Boolean validateToken(String token) {
		
		try {
			Jwts.parser().setSigningKey(secretKey)
			.parseClaimsJws(token).getBody();
			
			return true;
			
		} catch (ExpiredJwtException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedJwtException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MalformedJwtException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SignatureException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return false;	
	}
	public String getSubject(String token) {
		
		String subject= Jwts.parser().setSigningKey(secretKey)
				.parseClaimsJws(token).getBody().getSubject();
		
		return subject;
	}
	public List<GrantedAuthority> getAuthorities(String token) {
		List<String> roles = 
				Jwts.parser()
				.setSigningKey(secretKey)
				.parseClaimsJws(token)
				.getBody().get("roles", List.class);
		
		List<GrantedAuthority> authorities = new ArrayList<>();
		
		roles.forEach(role -> authorities.add(new SimpleGrantedAuthority(role)));
		
		return authorities;
     }
}
