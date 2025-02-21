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
	 private static final String[] PUBLIC_URLS = {
		        "/swagger-ui/**",
		        "/webjars/**",
		        "/swagger-resources/**",
		        "/v3/api-docs/**",  
		        "/auth/generate-token",
		        "/api/user"
		    };

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity security) throws Exception {

		security.cors(cors -> cors.disable()) // ⚠️ Consider enabling CORS if needed
        .csrf(csrf -> csrf.disable()) // Disable CSRF for stateless APIs
        .authorizeHttpRequests(auth -> auth
            .requestMatchers(PUBLIC_URLS).permitAll()  // ✅ Allow public endpoints
            .anyRequest().authenticated()  // Require authentication for everything else
        )
        .exceptionHandling(ex -> ex.authenticationEntryPoint(entryPoint)) // Handle unauthorized access
        .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) // Stateless session
        .addFilterBefore(filter, UsernamePasswordAuthenticationFilter.class); // Add JWT filter

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
