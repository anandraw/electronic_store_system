package com.anand.service.impl;

import java.util.List;
import java.util.Random;
import java.util.UUID;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.anand.config.AppConstat;
import com.anand.dto.PageabaleResponse;
import com.anand.dto.UserDTO;
import com.anand.entities.Role;
import com.anand.entities.User;
import com.anand.exceptions.NoUsersFoundException;
import com.anand.exceptions.ResourceNotFoundException;
import com.anand.helper.Helper;
import com.anand.repository.RoleRepo;
import com.anand.repository.UserRepo;
import com.anand.service.UserService;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepo userRepo;

	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private RoleRepo roleRepository;

	Random random = new Random();

	@Override
	public UserDTO createUSer(UserDTO userDTO) {

		String userId = UUID.randomUUID().toString();
		userDTO.setId(userId);

		User user = modelMapper.map(userDTO, User.class);
		// user password encoder
		user.setPassword(passwordEncoder.encode(user.getPassword()));

		Role role = new Role();
		role.setRoleId(UUID.randomUUID().toString());
		role.setName("ROLE_" + AppConstat.ROLE_NORMAL);
		Role roleNormal = roleRepository.findByName("ROLE_" + AppConstat.ROLE_NORMAL).orElse(role);
		user.setRoles(List.of(roleNormal));

		User saveUser = userRepo.save(user);

		UserDTO userDto = modelMapper.map(saveUser, UserDTO.class);
		return userDto;
	}

	@Override
	public UserDTO updateUser(UserDTO userDTO, String userId) {
		User user = userRepo.findById(userId)
				.orElseThrow(() -> new NoUsersFoundException("User not found with this ID: " + userId));
		user.setName(userDTO.getName());
		user.setAbout(userDTO.getAbout());
		user.setPassword(userDTO.getPassword());
		User saveUser = userRepo.save(user);
		UserDTO updated = modelMapper.map(saveUser, UserDTO.class);
		return updated;
	}

	@Override
	public void deleteUser(String userId) {
		User user = userRepo.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User not found with this id:" + userId));
		userRepo.delete(user);

	}

	@Override
	public UserDTO getUserById(String userId) {
		User user = userRepo.findById(userId)
				.orElseThrow(() -> new NoUsersFoundException("User not found with this ID: " + userId));
		return modelMapper.map(user, UserDTO.class);
	}

	public UserDTO getUserByEmail(String userEmail) {
		User user = userRepo.findByUserEmail(userEmail)
				.orElseThrow(() -> new NoUsersFoundException("User not found with this emailID: " + userEmail));
		return modelMapper.map(user, UserDTO.class);
	}

	@Override
	public PageabaleResponse<UserDTO> getAllUser(int pageNumber, int pageSize, String sortBy, String sortDir) {

		Sort sort = (sortDir.equalsIgnoreCase("desc")) ? (Sort.by(sortBy).descending()) : (Sort.by(sortBy).ascending());
		Pageable pageable = PageRequest.of(pageNumber, pageSize);
		Page<User> page = userRepo.findAll(pageable);
		PageabaleResponse pageabaleResponse = Helper.getPageableResponse(page, UserDTO.class);
		return pageabaleResponse;
	}

}
