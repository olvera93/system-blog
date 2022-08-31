package com.system.blog.provide.dto;



import java.util.List;
import java.util.Set;

import javax.validation.constraints.NotBlank;

import com.system.blog.business.model.Comment;

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
	private String title;
	
	@NotBlank(message = "field requeried description")
	private String description;
	
	@NotBlank(message = "field requeried content")
	private String content; 
	
	private List<Comment> comments;
	

}
