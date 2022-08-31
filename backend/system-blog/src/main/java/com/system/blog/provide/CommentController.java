package com.system.blog.provide;

import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.system.blog.business.CommentService;
import com.system.blog.crosscutting.errormanagement.ErrorManager;
import com.system.blog.provide.dto.CommentDto;
import com.system.blog.provide.dto.ResponseStatusErrorDto;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/v1/api")
@Tag(name = "Comment")
@CrossOrigin(origins = "*")
@Validated
public class CommentController {

	@Autowired
	private CommentService commentService;

	@Autowired
	private ErrorManager errorManager;

	@Operation(summary = "Create a comment", description = "Saves a new comment")
	@ApiResponses(value = { @ApiResponse(responseCode = "201", description = "Comment created"),
			@ApiResponse(responseCode = "400", description = "Bad request", content = @Content(schema = @Schema(implementation = ResponseStatusErrorDto.class))),
			@ApiResponse(responseCode = "500", description = "Error while creating a comment", content = @Content(schema = @Schema(implementation = ResponseStatusErrorDto.class))) })
	@PostMapping(path = "/publications/{publicationId}/comments", produces = { "application/json" })
	public ResponseEntity<CommentDto> create(
			@Parameter(name = "publicationId", description = "id of the publication", example = "1", required = true) @PathVariable(name = "publicationId", value = "publicationId") long publicationId,
			@RequestBody @Valid CommentDto commentDto) {

		try {
			CommentDto result = commentService.createComment(publicationId, commentDto);
			return ResponseEntity.status(HttpStatus.CREATED).body(result);

		} catch (Exception e) {
			throw new ResponseStatusException(errorManager.getHttpStatusForException(e), e.getMessage(), e);
		}
	}

	@Operation(summary = "Update a comment", description = "Updates a set of comment with the given properties. The allowed properties to update are: body. It's always necessary to send the property id for each element of the request body list")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Operation updated successfully"),
			@ApiResponse(responseCode = "400", description = "Data provided for update comment is invalid", content = @Content(schema = @Schema(implementation = ResponseStatusErrorDto.class))),
			@ApiResponse(responseCode = "404", description = "Comment not found", content = @Content(schema = @Schema(implementation = ResponseStatusErrorDto.class))),
			@ApiResponse(responseCode = "500", description = "Error while updating Comment", content = @Content(schema = @Schema(implementation = ResponseStatusErrorDto.class))) })
	@PatchMapping(path = "/publications/{publicationId}/comments/{commentId}", produces = { "application/json" })
	public ResponseEntity<CommentDto> updatePublication(
			@Parameter(name = "publicationId", description = "publication id", example = "1", required = true) @PathVariable(name = "publicationId", required = true) long publicationId,
			@Parameter(name = "commentId", description = "id of the comment", example = "1", required = true) @PathVariable(name = "commentId", value = "commentId") long commentId,
			@RequestBody Map<String, Object> updates) {

		try {
			CommentDto result = commentService.updateComment(publicationId, commentId, updates);
			return ResponseEntity.status(HttpStatus.OK).body(result);

		} catch (Exception e) {
			throw new ResponseStatusException(errorManager.getHttpStatusForException(e), e.getMessage(), e);
		}
	}

	@Operation(summary = "Retrieve comments", description = "Retrieve comments of the publication by publicationId")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Comments retrieve"),
			@ApiResponse(responseCode = "404", description = "Not found", content = @Content(schema = @Schema(implementation = ResponseStatusErrorDto.class))),
			@ApiResponse(responseCode = "500", description = "Error while get all comments", content = @Content(schema = @Schema(implementation = ResponseStatusErrorDto.class))) })
	@GetMapping(path = "/publications/{publicationId}/comments", produces = { "application/json" })
	public ResponseEntity<List<CommentDto>> retrieveCommentsByPublicationId(
			@Parameter(name = "publicationId", description = "id of the publication", example = "1", required = true) @PathVariable(name = "publicationId", value = "publicationId") long publicationId) {

		try {
			List<CommentDto> result = commentService.retrieveCommentByPublicationId(publicationId);
			return ResponseEntity.status(HttpStatus.OK).body(result);

		} catch (Exception e) {
			throw new ResponseStatusException(errorManager.getHttpStatusForException(e), e.getMessage(), e);
		}
	}

	@Operation(summary = "Retrieve a comment from a post", description = "Retrieve a comment from a post")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Comment retrieve"),
			@ApiResponse(responseCode = "404", description = "Not found", content = @Content(schema = @Schema(implementation = ResponseStatusErrorDto.class))),
			@ApiResponse(responseCode = "500", description = "Error while get a comment from a post", content = @Content(schema = @Schema(implementation = ResponseStatusErrorDto.class))) })
	@GetMapping(path = "/publications/{publicationId}/comments/{commentId}", produces = { "application/json" })
	public ResponseEntity<CommentDto> retrieveCommentById(
			@Parameter(name = "publicationId", description = "id of the publication", example = "1", required = true) @PathVariable(name = "publicationId", value = "publicationId") long publicationId,
			@Parameter(name = "commentId", description = "id of the comment", example = "1", required = true) @PathVariable(name = "commentId", value = "commentId") long commentId) {

		try {
			CommentDto result = commentService.retrieveCommentById(publicationId, commentId);
			return ResponseEntity.status(HttpStatus.OK).body(result);

		} catch (Exception e) {
			throw new ResponseStatusException(errorManager.getHttpStatusForException(e), e.getMessage(), e);
		}

	}

	@Operation(summary = "Delete comment", description = "Delete commet if exist")
	@ApiResponses(value = { @ApiResponse(responseCode = "204", description = "Comment deleted successfully"),
			@ApiResponse(responseCode = "404", description = "Comment not found", content = @Content(schema = @Schema(implementation = ResponseStatusErrorDto.class))),
			@ApiResponse(responseCode = "500", description = "Error while delete commet", content = @Content(schema = @Schema(implementation = ResponseStatusErrorDto.class))) })
	@DeleteMapping(path = "/publications/{publicationId}/comments/{commentId}")
	public ResponseEntity<Void> deleteComment(
			@Parameter(name = "publicationId", description = "id of the publication", example = "1", required = true) @PathVariable(name = "publicationId", value = "publicationId") long publicationId,
			@Parameter(name = "commentId", description = "id of the comment", example = "1", required = true) @PathVariable(name = "commentId", value = "commentId") long commentId) {
		try {
			// delete operation in service
			commentService.deleteComment(publicationId, commentId);
			return ResponseEntity.status(HttpStatus.NO_CONTENT).build();

		} catch (Exception e) { // if a throw appear, we return error response
			throw new ResponseStatusException(errorManager.getHttpStatusForException(e), e.getMessage(), e);
		}
	}

}
