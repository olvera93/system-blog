package com.system.blog.crosscutting.errormanagement;

public class BlogAppException extends IllegalArgumentException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7107288643053977629L;
	
	public BlogAppException (String message) {
		super(message);
	}

}
