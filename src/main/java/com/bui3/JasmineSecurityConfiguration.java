package com.bui3;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

@Configuration
@EnableWebSecurity
public class JasmineSecurityConfiguration extends WebSecurityConfigurerAdapter {

	@Autowired
	private UserRepository userRepository;
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
			.authorizeRequests()
				.antMatchers("/admin/**").hasRole("ADMIN")
				.anyRequest().authenticated()
			.and()
			.formLogin()
				.loginPage("/login")
				.permitAll()
			.and()
				.logout().permitAll();
	}
	
	@Override
	protected void configure(
			AuthenticationManagerBuilder auth) throws Exception {
		auth
			.userDetailsService(userDetailsService());
	}
	
	@Bean
	public UserDetailsService userDetailsService() {
		return new UserDetailsService() {
			@Override
			public User loadUserByUsername(String username) 
					throws UsernameNotFoundException {
				User userDetails = userRepository.findOne(username);
				if (null != userDetails)
					return userDetails;
				
				throw new UsernameNotFoundException(
						"User '" + username + "' not found.");
			}
		};
	}
}
