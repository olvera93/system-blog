package com.system.blog.crosscutting.errormanagement;

public class ResourceNotFoundException extends IllegalArgumentException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7649561586524927953L;
	
	
	public ResourceNotFoundException(String message) {
		super(message);
	}
	

}
