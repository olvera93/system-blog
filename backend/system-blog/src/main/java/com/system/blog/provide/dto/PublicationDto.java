package com.system.blog.provide.dto;

import java.util.ArrayList;
import java.util.List;

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
public class PublicationDto {
	
	private Long id;
	
	@NotBlank(message = "field requeried title")
	@Size(min = 2, message = "The title must have 2 characters")
	private String title;
	
	@NotBlank(message = "field requeried description")
	@Size(min = 10, message = "The description must have 10 characters")
	private String description;
	
	@NotBlank(message = "field requeried content")
	private String content; 
	
	private List<CommentDto> comments;
	

}
