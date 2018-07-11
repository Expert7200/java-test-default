package com.etnetera.hr.rest;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * Envelope for the validation errors. Represents JSON response.
 * 
 * @author Bogdan
 *
 */
public class Errors<T> {

	private List<T> errors = new ArrayList<>();

	public List<T> getErrors() {
		return errors;
	}

	public void setErrors(List<T> errors) {
		this.errors = errors;
	}

	public void addError(T error) {
		getErrors().add(error);
	}

	public void deleteError(T error) {
		getErrors().remove(error);
	}

	@Override
	public String toString() {
		return "Errors [errors=" + getErrors() + "]";
	}

}
