package com.linkr.message;

/**
 * Exception Handler class
 * 
 * @author jocelleluna
 *
 */
public class LinkrAppException extends Exception {

	private static final long serialVersionUID = 1L;
	
	private String errorMessage;
	 
	public LinkrAppException() {
		super();
	}
	
	public LinkrAppException(String errorMessage) {
		super(errorMessage);
		this.errorMessage = errorMessage;
	}
	
	public String getErrorMessage() {
		return errorMessage;
	}

}
