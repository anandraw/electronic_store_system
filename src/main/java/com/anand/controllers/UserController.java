package com.anand.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.anand.dto.PageabaleResponse;
import com.anand.dto.UserDTO;
import com.anand.service.UserService;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
@RequestMapping("/api/user")
public class UserController {

	@Autowired
	private UserService userService;

	@PostMapping
	public ResponseEntity<UserDTO> createUser(@RequestBody UserDTO userDTO) {
		UserDTO saveUser = userService.createUSer(userDTO);
		return new ResponseEntity<>(saveUser, HttpStatus.CREATED);
	}

	@GetMapping
	public ResponseEntity<PageabaleResponse<UserDTO>> getAllUser(
			@RequestParam(value = "pageNumber", defaultValue = "0", required = false) int pageNumber,
			@RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize,
			@RequestParam(value = "sortBy", defaultValue = "name", required = false) String sortBy,
			@RequestParam(value = "sortDir", defaultValue = "ASC", required = false) String sortDir

	) {
		PageabaleResponse<UserDTO> dto = userService.getAllUser(pageNumber, pageSize, sortBy, sortDir);
		return new ResponseEntity<>(dto, HttpStatus.OK);

	}

	@GetMapping("/{userId}")
	public ResponseEntity<UserDTO> getUserById(@PathVariable String userId) {
		UserDTO userById = userService.getUserById(userId);
		return new ResponseEntity<>(userById, HttpStatus.OK);
	}

	@GetMapping("/email/{email}")
	public ResponseEntity<UserDTO> getUserByEmail(@PathVariable String email) {
		UserDTO userByEmail = userService.getUserByEmail(email);
		return new ResponseEntity<>(userByEmail, HttpStatus.OK);
	}

	@DeleteMapping("/{userId}")
	public ResponseEntity<String> deleteUser(@PathVariable String userId) {
		userService.deleteUser(userId);
		return new ResponseEntity<>("User deleted successfully", HttpStatus.OK);
	}

	@PutMapping("/{userId}")
	public ResponseEntity<UserDTO> updateUser(@RequestBody UserDTO userDTO, @PathVariable String userId) {
		UserDTO updateUser = userService.updateUser(userDTO, userId);
		return new ResponseEntity<>(updateUser, HttpStatus.OK);
	}

}
