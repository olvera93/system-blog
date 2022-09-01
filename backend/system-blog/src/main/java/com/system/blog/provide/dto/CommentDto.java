package com.system.blog.provide.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

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
	@Size(min = 10, message = "The body must have 10 characters")
	private String body;
	
}
