package com.system.blog.business;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.system.blog.business.model.Publication;
import com.system.blog.crosscutting.errormanagement.ResourceNotFoundException;
import com.system.blog.persistence.PublicationRepository;
import com.system.blog.provide.dto.PageResultDto;
import com.system.blog.provide.dto.PublicationDto;
import com.system.blog.provide.dto.PublicationDtoCreate;

@Service
public class PublicationService {
	
	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private PublicationRepository publicationRepository;
	
	/**
	 * Create a publication
	 * @param publicationDto
	 * @return publication
	 */
	public PublicationDtoCreate createPublication(PublicationDtoCreate publicationDto) {
		
		Publication publication = Publication.builder()
				.title(publicationDto.getTitle())
				.description(publicationDto.getContent())
				.content(publicationDto.getContent())
				.build();
		
		publication = publicationRepository.save(publication);
		
		return modelMapper.map(publication, PublicationDtoCreate.class);
		
	}
	
	/**
	 * Retrieve all publications
	 * @return retrieve a list of publications
	 */
	public PageResultDto<Publication> getAllPublications(int pageNumber, int pageSize, String sortBy, String sortDir) {
		
		Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name())?Sort.by(sortBy).ascending():Sort.by(sortBy).descending();
		
		PageResultDto<Publication> pageResultDto = new PageResultDto<>();
		Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);
		
		Page<Publication> publications = publicationRepository.findAll(pageable);
		
		List<Publication> listPublications = publications.getContent();
		listPublications.stream().map(publication -> modelMapper.map(publication, PublicationDto.class)).collect(Collectors.toList());
		
		pageResultDto.setCurrentPage(publications.getNumber()); // create page information
		pageResultDto.setResultsInPage(publications.getNumberOfElements());
		pageResultDto.setTotalPages(publications.getTotalPages());
		pageResultDto.setTotalResults(publications.getTotalElements());
		pageResultDto.setLastPage(publications.isLast());
		pageResultDto.setResults(listPublications);
		
		return pageResultDto;
	}
	
	/**
	 * Get a publication
	 * @param id
	 * @return publication
	 */
	public PublicationDto getPublicationById(long id) {
		Publication publication = publicationRepository
				.findById(id).orElseThrow(() -> new ResourceNotFoundException("Publication not found with id: " + id));
		
		return modelMapper.map(publication, PublicationDto.class);
	}
	
	/**
	 * Update a publication
	 * @param updates
	 * @param id
	 * @return publication update
	 */
	public PublicationDto updatePublication(Map<String, Object> updates, long id) {
		
		Publication publication = publicationRepository
				.findById(id).orElseThrow(() -> new ResourceNotFoundException("Publication not found with id: " + id));
		
		List<String> allowedProperties = new ArrayList<>();
		allowedProperties.add("title");
		allowedProperties.add("description");
		allowedProperties.add("content");
		
		
		for(String key: updates.keySet()) {
			if(!allowedProperties.contains(key)) {
				throw new IllegalArgumentException("Property: "+key+" is not a valid property to update");
			}
		}
		
		String propertyToUpdate = "title";
		if (updates.containsKey(propertyToUpdate) && updates.get(propertyToUpdate) != null) {
			publication.setTitle(updates.get(propertyToUpdate).toString());
		}
		
		propertyToUpdate = "description";
		if (updates.containsKey(propertyToUpdate) && updates.get(propertyToUpdate) != null) {
			publication.setDescription(updates.get(propertyToUpdate).toString());
		}
		
		propertyToUpdate = "content";
		
		if (updates.containsKey(propertyToUpdate) && updates.get(propertyToUpdate) != null) {
			publication.setContent(updates.get(propertyToUpdate).toString());
		}
		
		
		Publication returned = publicationRepository.save(publication);
		
		return modelMapper.map(returned, PublicationDto.class);

	}
	
	/**
	 * Delete publication
	 * @param id
	 */
	public void deletePublication(long id) {
		Publication publication = publicationRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Publication not found with id: " + id));
		publicationRepository.delete(publication);
	}
	
	
}
