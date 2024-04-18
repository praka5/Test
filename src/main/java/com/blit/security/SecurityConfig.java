package com.blit.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;


@Configuration
@EnableWebSecurity 
public class SecurityConfig {
	
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		return http
				.csrf((c)->{c.disable();})
				.cors((c)->Customizer.withDefaults())
				.authorizeHttpRequests(
						(c)->{c.requestMatchers(HttpMethod.GET,
								"api/v1/car/*").hasAnyRole("ADMIN","USER")
						.requestMatchers(HttpMethod.POST, 
								"api/v1/car/*").hasRole("ADMIN")
						.requestMatchers(HttpMethod.DELETE, 
								"api/v1/car/*").hasRole("ADMIN")
						.requestMatchers(HttpMethod.PUT, 
								"api/v1/car/*").hasRole("ADMIN")
						.anyRequest().authenticated();
						})
				.httpBasic(Customizer.withDefaults())
				.build();
	}
	
	@Bean
	public UserDetailsService userDetailsService() {
		UserDetails admin = User.builder()
				.username("admin")
				.password(bCryptPasswordEncoder()
						.encode("password"))
				.roles("ADMIN")
				.build();
		UserDetails user = User.builder()
				.username("user")
				.password(bCryptPasswordEncoder().encode
						("password"))
				.roles("USER")
				.build();
		return new InMemoryUserDetailsManager(admin, user);
	}
	
	@Bean
	public BCryptPasswordEncoder bCryptPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	
}
