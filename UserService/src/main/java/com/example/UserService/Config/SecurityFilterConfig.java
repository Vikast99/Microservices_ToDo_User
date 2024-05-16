package com.example.UserService.Config;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.example.UserService.Security.JwtAuthenticationEntryPoint;
import com.example.UserService.Security.JwtAuthenticationFilter;

import lombok.AllArgsConstructor;



@Configuration
@AllArgsConstructor
public class SecurityFilterConfig {
	
	
	  private JwtAuthenticationEntryPoint point;
	    private JwtAuthenticationFilter filter;

	    @Bean
	    public SecurityFilterChain filterChain(HttpSecurity security) throws Exception {
	        return security.csrf(csrf-> csrf.disable())
	                .cors(cors-> cors.disable())
	                .authorizeHttpRequests(auth->auth.requestMatchers("/authenticate").permitAll()
	                		.requestMatchers("/user/**").hasAnyRole("USER","ADMIN")
	                		.requestMatchers("/admin/**").hasRole("ADMIN")
	                        .anyRequest().authenticated())
	                .exceptionHandling(ex ->ex.authenticationEntryPoint(point))
	                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
	                .addFilterBefore(filter, UsernamePasswordAuthenticationFilter.class)
	                .build();

	    }

}
