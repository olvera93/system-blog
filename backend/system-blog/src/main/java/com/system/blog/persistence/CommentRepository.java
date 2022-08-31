package com.system.blog.persistence;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.system.blog.business.model.Comment;

public interface CommentRepository extends JpaRepository<Comment, Long> {
	
	List<Comment> findByPublicationId(long publicationId);

}
