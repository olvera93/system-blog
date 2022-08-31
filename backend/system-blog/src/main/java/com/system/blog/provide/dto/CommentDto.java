package com.system.blog.provide.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommentDto {
	
	
	private long id;
	
	@NotBlank(message = "field requeried name")
	private String name;
	
	@NotBlank(message = "field requeried email")
	@Email
	private String email;
	
	@NotBlank(message = "field requeried body")
	private String body;
	
}
