package com.system.blog.provide.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RegisterDto {
	
	private String name;
	
	private String username;
	
	private String email;
	
	private String password;

}
