package com.system.blog.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import com.system.blog.business.model.Publication;

public interface PublicationRepository extends JpaRepository<Publication, Long> {

}
