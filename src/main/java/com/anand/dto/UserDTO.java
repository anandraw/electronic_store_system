package com.anand.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
	
	
	private String id;
	private String name;
	private String email;
	private String password;
	private String gender;
	private String about;

}
