package com.system.blog.provide;

import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.system.blog.business.PublicationService;
import com.system.blog.business.model.Publication;
import com.system.blog.crosscutting.errormanagement.ErrorManager;
import com.system.blog.provide.dto.PageResultDto;
import com.system.blog.provide.dto.PublicationDto;
import com.system.blog.provide.dto.PublicationDtoCreate;
import com.system.blog.provide.dto.ResponseStatusErrorDto;
import com.system.blog.utilities.ConstansApp;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/v1/api/publications")
@Tag(name = "Publication")
@CrossOrigin(origins = "*")
@Validated
public class PublicationController {

	@Autowired
	private PublicationService publicationService;

	@Autowired
	private ErrorManager errorManager;

	@Operation(summary = "Create a publication", description = "Saves a new publication")
	@ApiResponses(value = { @ApiResponse(responseCode = "201", description = "Publication created"),
			@ApiResponse(responseCode = "400", description = "Bad request", content = @Content(schema = @Schema(implementation = ResponseStatusErrorDto.class))),
			@ApiResponse(responseCode = "500", description = "Error while creating a publication", content = @Content(schema = @Schema(implementation = ResponseStatusErrorDto.class))) })
	@PreAuthorize("hasRole('ADMIN')")
	@PostMapping(produces = { "application/json" })
	public ResponseEntity<PublicationDtoCreate> create(@RequestBody @Valid PublicationDtoCreate publicationDto) {

		try {
			PublicationDtoCreate result = publicationService.createPublication(publicationDto);
			return ResponseEntity.status(HttpStatus.CREATED).body(result);

		} catch (Exception e) {
			throw new ResponseStatusException(errorManager.getHttpStatusForException(e), e.getMessage(), e);
		}
	}

	@Operation(summary = "Retrieve all the publications", description = "Retrieve all publications")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Publications retrieve"),
			@ApiResponse(responseCode = "404", description = "Not found", content = @Content(schema = @Schema(implementation = ResponseStatusErrorDto.class))),
			@ApiResponse(responseCode = "500", description = "Error while retrieve all publications", content = @Content(schema = @Schema(implementation = ResponseStatusErrorDto.class))) })
	@GetMapping(produces = { "application/json" })
	public ResponseEntity<PageResultDto<PublicationDto>> retrieveAllPublications(
			@Parameter(name = "pageNumber", description = "Page to retrieve (>=0)", example = "0", required = false) @RequestParam(value = "pageNumber", defaultValue = ConstansApp.DEFAULT_PAGE_NUMBER, required = false) int pageNumber,
			@Parameter(name = "pageSize", description = "Number of maximum logs per page (>0)", example = "10", required = false) @RequestParam(value = "pageSize", defaultValue = ConstansApp.DEFAULT_PAGE_SIZE, required = false) int pageSize,
			@Parameter(name = "sortBy", description = "Sort the publucations by ", example = "id", required = false) @RequestParam(value = "sortBy", defaultValue = ConstansApp.DEFAULT_ORDER_BY, required = false) String sortBy,
			@Parameter(name = "sortDir", description = "Sort dir ", example = "asc", required = false) @RequestParam(value = "sortDir", defaultValue = ConstansApp.DEFAULT_ORDER_BY_DIR, required = false) String sortDir) {

		try {
			PageResultDto<PublicationDto> result = publicationService.getAllPublications(pageNumber, pageSize, sortBy,
					sortDir);
			return ResponseEntity.status(HttpStatus.OK).body(result);

		} catch (Exception e) {
			throw new ResponseStatusException(errorManager.getHttpStatusForException(e), e.getMessage(), e);
		}
	}

	@Operation(summary = "Retrieve a publication", description = "Retrieve a publication by id")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Publication retrieve"),
			@ApiResponse(responseCode = "404", description = "Not found", content = @Content(schema = @Schema(implementation = ResponseStatusErrorDto.class))),
			@ApiResponse(responseCode = "500", description = "Error while get a publication", content = @Content(schema = @Schema(implementation = ResponseStatusErrorDto.class))) })
	@GetMapping(path = "/{id}", produces = { "application/json" })
	public ResponseEntity<PublicationDto> retrievePublicationById(
			@Parameter(name = "id", description = "publication id", example = "1", required = true) @PathVariable(name = "id", required = true) long id) {

		try {
			PublicationDto result = publicationService.getPublicationById(id);
			return ResponseEntity.status(HttpStatus.OK).body(result);

		} catch (Exception e) {
			throw new ResponseStatusException(errorManager.getHttpStatusForException(e), e.getMessage(), e);
		}

	}

	@Operation(summary = "Update a publication", description = "Updates a set of publication with the given properties. The allowed properties to update are: tittle, description or content. It's always necessary to send the property id for each element of the request body list")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Operation updated successfully"),
			@ApiResponse(responseCode = "400", description = "Data provided for update publication is invalid", content = @Content(schema = @Schema(implementation = ResponseStatusErrorDto.class))),
			@ApiResponse(responseCode = "404", description = "Publication not found", content = @Content(schema = @Schema(implementation = ResponseStatusErrorDto.class))),
			@ApiResponse(responseCode = "500", description = "Error while updating publication", content = @Content(schema = @Schema(implementation = ResponseStatusErrorDto.class))) })
	@PreAuthorize("hasRole('ADMIN')")
	@PatchMapping(path = "/{id}", produces = { "application/json" })
	public ResponseEntity<PublicationDto> updatePublication(
			@Parameter(name = "id", description = "publication id", example = "1", required = true) @PathVariable(name = "id", required = true) long id,
			@Parameter(name = "updates", description = "Values to be updated", required = true) @RequestBody Map<String, Object> updatesList) {

		try {
			PublicationDto result = publicationService.updatePublication(updatesList, id);
			return ResponseEntity.status(HttpStatus.OK).body(result);

		} catch (Exception e) {
			throw new ResponseStatusException(errorManager.getHttpStatusForException(e), e.getMessage(), e);
		}
	}

	@Operation(summary = "Delete publication", description = "Delete publication if exist")
	@ApiResponses(value = { @ApiResponse(responseCode = "204", description = "Publication deleted successfully"),
			@ApiResponse(responseCode = "404", description = "Publication not found", content = @Content(schema = @Schema(implementation = ResponseStatusErrorDto.class))),
			@ApiResponse(responseCode = "500", description = "Error while delete publication", content = @Content(schema = @Schema(implementation = ResponseStatusErrorDto.class))) })
	@PreAuthorize("hasRole('ADMIN')")
	@DeleteMapping(path = "/{id}")
	public ResponseEntity<Void> deletePublication(@PathVariable(name = "id", required = true) long id) {
		try {
			// delete operation in service
			publicationService.deletePublication(id);
			return ResponseEntity.status(HttpStatus.NO_CONTENT).build();

		} catch (Exception e) { // if a throw appear, we return error response
			throw new ResponseStatusException(errorManager.getHttpStatusForException(e), e.getMessage(), e);
		}
	}

}
