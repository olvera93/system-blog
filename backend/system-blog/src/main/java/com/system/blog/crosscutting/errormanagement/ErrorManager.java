package com.system.blog.crosscutting.errormanagement;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class ErrorManager {

	private long totalErrors = 0;

	public long getTotalErrors() {
		return totalErrors;
	}

	public HttpStatus getHttpStatusForException(Exception e) {

		logError(e);

		
		if (e instanceof ResourceNotFoundException) {
			return HttpStatus.NOT_FOUND;
		}
		
		if(e instanceof IllegalArgumentException || e instanceof BlogAppException) {
			return HttpStatus.BAD_REQUEST;
		}
	

		return HttpStatus.INTERNAL_SERVER_ERROR;

	}

	public void logError(Exception e) {
		totalErrors++;
		log.error(e.getMessage(), e);
	}

	public void logError(Exception e, String message) {
		totalErrors++;
		log.error(message, e);
	}
}
