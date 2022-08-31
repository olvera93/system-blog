package com.system.blog.provide.dto;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@Schema(name = "ResponseStatusError", description = "Error response")
public class ResponseStatusErrorDto {

	@Schema(name = "timestamp", description = "Time of error")
	private String timestamp;
	
	@Min(100)
	@Max(599)
	@NotNull
	@Schema(name = "status", description = "Error ocurred in number")
	private int status;
	
	@Schema(name = "error", description = "Error ocurred")
	private String error;
	
	@Schema(name = "trace", description = "Trace of error logs")
	private String trace;
	
	@NotNull
	@Schema(name = "message", description = "Custom error message")
	private String message;
	
	@Schema(name = "path", description = "Endpoint where the error occurred")
	private String path;
}
