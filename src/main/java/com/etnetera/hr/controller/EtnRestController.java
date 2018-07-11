package com.etnetera.hr.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.etnetera.hr.exception.FrameworkDoesNotExistException;
import com.etnetera.hr.rest.Errors;
import com.etnetera.hr.rest.FrameworkDoesNotExistError;
import com.etnetera.hr.rest.ValidationError;

/**
 * Main REST controller.
 * 
 * @author Bogdan
 *
 */
public abstract class EtnRestController {

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<Errors<ValidationError>> handleValidationException(MethodArgumentNotValidException ex) {
		BindingResult result = ex.getBindingResult();
		Errors<ValidationError> errors = new Errors<>();
		List<ValidationError> errorList = result.getFieldErrors().stream().map(e -> {
			return new ValidationError(e.getField(), e.getCode());
		}).collect(Collectors.toList());
		errors.setErrors(errorList);
		return ResponseEntity.badRequest().body(errors);
	}

	@ExceptionHandler(FrameworkDoesNotExistException.class)
	public ResponseEntity<Errors<FrameworkDoesNotExistError>> handleFrameworkDoesNotExistsException(
			FrameworkDoesNotExistException ex) {
		Errors<FrameworkDoesNotExistError> errors = new Errors<>();
		FrameworkDoesNotExistError frameworkDoesNotExistError = new FrameworkDoesNotExistError(ex.getId());
		List<FrameworkDoesNotExistError> errorList = new ArrayList<>();
		errorList.add(frameworkDoesNotExistError);
		errors.setErrors(errorList);
		return ResponseEntity.badRequest().body(errors);
	}

}