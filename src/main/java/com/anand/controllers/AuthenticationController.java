package com.anand.controllers;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.anand.dto.JwtRequest;
import com.anand.dto.JwtResponce;
import com.anand.dto.UserDTO;
import com.anand.entities.User;
import com.anand.security.JwtHelper;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/auth")
@Slf4j
public class AuthenticationController {

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private JwtHelper jwtHelper;

	@Autowired
	private UserDetailsService userDetailsService;

	@Autowired
	private ModelMapper mapper;

	// method to generate token
	@PostMapping("/generate-token")
	public ResponseEntity<JwtResponce> login(@RequestBody JwtRequest request) {
		

		// now authenticate username and password
		this.doAuthenticate(request.getEmail(), request.getPassword());

		User user = (User) userDetailsService.loadUserByUsername(request.getEmail());

		// generate token
		String token = jwtHelper.generateToken(user);
		JwtResponce response = JwtResponce.builder().token(token).user(mapper.map(user, UserDTO.class)).build();
		return ResponseEntity.ok(response);

	}

	private void doAuthenticate(String email, String password) {

		try {
			UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(email,
					password);
			authenticationManager.authenticate(authentication);

		} catch (BadCredentialsException ex) {
			throw new BadCredentialsException("Invalid Username and Password !!");
		}

	}

}
