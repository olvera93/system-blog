package com.system.blog.crosscutting.errormanagement;

public class UsernameNotFoundException extends IllegalArgumentException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5533406368035601899L;
	
	public UsernameNotFoundException(String message) {
		super(message);
	}

}
