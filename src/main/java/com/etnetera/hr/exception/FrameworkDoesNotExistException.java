package com.etnetera.hr.exception;

/**
 * Exception thrown when request called for not existing framework
 * 
 * @author Bogdan
 *
 */
public class FrameworkDoesNotExistException extends RuntimeException {

	private Long id;

	public FrameworkDoesNotExistException(Long id) {
		super();
		this.id = id;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

}
