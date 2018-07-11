package com.etnetera.hr.rest;

/**
 * 
 * FrameworkDoesNotExistError error. Represents JSON response.
 * 
 * @author Bogdan
 *
 */
public class FrameworkDoesNotExistError {

	private Long id;
	private final String MESSAGE = "Framework doesn't exist";

	public FrameworkDoesNotExistError() {
	}

	public FrameworkDoesNotExistError(Long id) {
		this.id = id;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return "FrameworkDoesNotExistsError [" + id + " : " + MESSAGE + "]";
	}

}
