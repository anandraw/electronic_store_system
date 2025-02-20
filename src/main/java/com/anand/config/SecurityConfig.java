package com.anand.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.anand.security.JWTAuthenticationFilter;
import com.anand.security.JwtAuthEntryPoint;

@Configuration
public class SecurityConfig {

	@Autowired
	private JwtAuthEntryPoint entryPoint;

	@Autowired
	private JWTAuthenticationFilter filter;

	 // Public URLs (no authentication required)
    final String[] PUBLIC_URLS = { 
        "/swagger-ui/**", 
        "/webjars/**", 
        "/swagger-resources/**", 
        "/auth/generate-token" // Endpoint to generate JWT token
    };

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity security) throws Exception {

		security.cors(httpSecurityCorsConfigure -> httpSecurityCorsConfigure.disable());

		security.csrf(httpSecurityCsrfConfigure -> httpSecurityCsrfConfigure.disable());
		
		  // Configure authorization rules
        security.authorizeHttpRequests(request -> request
            .requestMatchers(PUBLIC_URLS).permitAll() // Public endpoints
            .anyRequest().authenticated() // All other endpoints require authentication
        );

		// entry point
		security.exceptionHandling(ex -> ex.authenticationEntryPoint(entryPoint));

		// session creation policy
		security.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
		// main -->
		security.addFilterBefore(filter, UsernamePasswordAuthenticationFilter.class);
		return security.build();

	}

	// for password encoder
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration builder) throws Exception {
		return builder.getAuthenticationManager();
	}

}
