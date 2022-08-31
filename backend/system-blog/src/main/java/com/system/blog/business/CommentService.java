package com.system.blog.business;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.system.blog.business.model.Comment;
import com.system.blog.business.model.Publication;
import com.system.blog.crosscutting.errormanagement.BlogAppException;
import com.system.blog.crosscutting.errormanagement.ResourceNotFoundException;
import com.system.blog.persistence.CommentRepository;
import com.system.blog.persistence.PublicationRepository;
import com.system.blog.provide.dto.CommentDto;

@Service
public class CommentService {
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private CommentRepository commentRepository;
	
	@Autowired
	private PublicationRepository publicationRepository;
	
	/**
	 * Create a comment on a publication
	 * @param publicationId
	 * @param commentDto
	 * @return commentDto create
	 */
	public CommentDto createComment(long publicationId, CommentDto commentDto) {
		
		Publication publication = publicationRepository.findById(publicationId)
				.orElseThrow(() -> new ResourceNotFoundException("Publication not found with id: " + publicationId));
		
		
		Comment comment = Comment.builder()
				.name(commentDto.getName())
				.email(commentDto.getEmail())
				.body(commentDto.getBody())
				.build();
		
		comment.setPublication(publication);
		
		
		Comment returnedComment = commentRepository.save(comment);
		
		
		return modelMapper.map(returnedComment, CommentDto.class);
		
	}
	
	/**
	 * Retrieve a comment from a publication
	 * @param publicationId
	 * @return commentDto
	 */
	public List<CommentDto> retrieveCommentByPublicationId(long publicationId) {
		List<Comment> comments = commentRepository.findByPublicationId(publicationId);
		
		return comments.stream().map(comment -> modelMapper.map(comment, CommentDto.class)).collect(Collectors.toList());
	}
	
	/**
	 * Retrieve a comment from a post
	 * @param publicationId
	 * @param commentId
	 * @return commentDto
	 */
	public CommentDto retrieveCommentById(long publicationId, long commentId) {
		
		Publication publication = publicationRepository.findById(publicationId)
				.orElseThrow(() -> new ResourceNotFoundException("Publication not found with id: " + publicationId));
		
		Comment comment = commentRepository.findById(commentId)
				.orElseThrow(() -> new ResourceNotFoundException("Comment not found with id: " + commentId));
		
		if (!comment.getPublication().getId().equals(publication.getId())) {
			throw new BlogAppException("The comment does not belong to the publication");
		}
		
		return modelMapper.map(comment, CommentDto.class);
		
	}
	
	/**
	 * Update a comment from a publication
	 * @param publicationId
	 * @param commentId
	 * @param updates
	 * @return commentDto updated
	 */
	public CommentDto updateComment(long publicationId, long commentId, Map<String, Object> updates) {
		Publication publication = publicationRepository.findById(publicationId)
				.orElseThrow(() -> new ResourceNotFoundException("Publication not found with id: " + publicationId));
		
		Comment comment = commentRepository.findById(commentId)
				.orElseThrow(() -> new ResourceNotFoundException("Comment not found with id: " + commentId));
		
		if (!comment.getPublication().getId().equals(publication.getId())) {
			throw new BlogAppException("The comment does not belong to the publication");
		}
		
		List<String> allowedProperties = new ArrayList<>();
		allowedProperties.add("body");
				
		for(String key: updates.keySet()) {
			if(!allowedProperties.contains(key)) {
				throw new IllegalArgumentException("Property: "+key+" is not a valid property to update");
			}
		}
		
		String propertyToUpdate = "body";
		
		if (updates.containsKey(propertyToUpdate) && updates.get(propertyToUpdate) != null) {
			comment.setBody(updates.get(propertyToUpdate).toString());
		}
		
		Comment returnedComment = commentRepository.save(comment);
		
		return modelMapper.map(returnedComment, CommentDto.class);
		
	}
	
	public void deleteComment(long publicationId, long commentId) {
		Publication publication = publicationRepository.findById(publicationId)
				.orElseThrow(() -> new ResourceNotFoundException("Publication not found with id: " + publicationId));
		
		Comment comment = commentRepository.findById(commentId)
				.orElseThrow(() -> new ResourceNotFoundException("Comment not found with id: " + commentId));
		
		if (!comment.getPublication().getId().equals(publication.getId())) {
			throw new BlogAppException("The comment does not belong to the publication");
		}
		
		commentRepository.delete(comment);
		
	}

}
