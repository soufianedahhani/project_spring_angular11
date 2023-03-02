package com.dev.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.dev.service.UserService;

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	
	
	@Autowired 
	private JwtAuthorizationFilter jwtAuthorizationFilter;
	
	@Autowired
	private Http401EntryPoint http401EntryPoint;
	
	@Autowired
	private UserService userService;
	
	@Bean 
	public PasswordEncoder passwordEncoder() {
		
		return new BCryptPasswordEncoder();
		
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		
		http.csrf().disable();
		http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
		http.authorizeHttpRequests().antMatchers("/login").permitAll();
		http.authorizeHttpRequests().anyRequest().authenticated();
		http.addFilterBefore(jwtAuthorizationFilter, UsernamePasswordAuthenticationFilter.class);
		http.exceptionHandling().authenticationEntryPoint(http401EntryPoint);	
		http.cors();
				
	}
	     @Override
	    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
	    	
	    	 auth.userDetailsService(userService).passwordEncoder(passwordEncoder());
	    	 
//	    	 auth.inMemoryAuthentication()
//	    	 .passwordEncoder(NoOpPasswordEncoder.getInstance())
//	    	 .withUser("mohamed").password("mohamed")
//	    	 .authorities("ROLE_ADMIN");
//	    	 
//	    	 auth.inMemoryAuthentication()
//	    	 .passwordEncoder(NoOpPasswordEncoder.getInstance())
//	    	 .withUser("mars").password("mars")
//	    	 .authorities("ROLE_USER");
//	    	
	    }
	     @Bean
	     public AuthenticationManager authenticationManager() throws Exception {
	    	 return super.authenticationManager();
	     }
	     
	     @Override
	    public void configure(WebSecurity web) throws Exception {
	    	web.ignoring().antMatchers(
	    			"/swagger-ui.html",
	    			"/webjars/**",
	    			"/swagger-resources/**",
	    			"/v2/api-docs");
	    	
	    }
	     
	}


