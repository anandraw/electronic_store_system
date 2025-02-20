package com.anand.service;

import java.util.List;

import com.anand.dto.PageabaleResponse;
import com.anand.dto.UserDTO;

public interface UserService {

	UserDTO createUSer(UserDTO userDTO);

	
	PageabaleResponse<UserDTO> getAllUser(int pageNumber, int pageSize, String sortBy, String sortDir);

	UserDTO updateUser(UserDTO userDTO, String userId);

	void deleteUser(String userId);

	UserDTO getUserById(String userId);

	UserDTO getUserByEmail(String userEmail);

}
