package com.dev.config;

import java.io.IOException;
import java.util.List;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.OncePerRequestFilter;

import com.dev.util.JwtUtil;


@Configuration
public class JwtAuthorizationFilter extends OncePerRequestFilter{

	@Value("${jwt.prefix}")
	private String prefix;
	
	@Autowired
	private JwtUtil  jwtUtil;
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		String requestHeader = request.getHeader("Authorization");
		System.out.println(requestHeader);
		if (requestHeader == null || !requestHeader.startsWith(prefix)) {
			filterChain.doFilter(request, response);
			return;
		}

		String token = requestHeader.replace(prefix, "").trim();
		if (jwtUtil.validateToken(token)) {

			String username = jwtUtil.getSubject(token);
			
			List<GrantedAuthority> authorities = jwtUtil.getAuthorities(token);
			
			UsernamePasswordAuthenticationToken authenticationToken = new
					UsernamePasswordAuthenticationToken(username, null, authorities);
			
			
			SecurityContextHolder.getContext().setAuthentication(authenticationToken);
			
		}
		
		filterChain.doFilter(request, response);
	}

}
