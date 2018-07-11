package com.etnetera.hr.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.etnetera.hr.data.JavaScriptFramework;
import com.etnetera.hr.data.JavaScriptFrameworkVersion;
import com.etnetera.hr.exception.FrameworkDoesNotExistException;
import com.etnetera.hr.service.ApiService;

/**
 * Simple REST controller for Create/Update functionality.
 * 
 * @author Bogdan
 *
 */
@RestController
public class JavaScriptFrameworkControllerCU extends EtnRestController {

	private final ApiService apiService;

	@Autowired
	public JavaScriptFrameworkControllerCU(ApiService apiService) {
		this.apiService = apiService;
	}

	@PutMapping("/frameworks")
	public ResponseEntity<JavaScriptFramework> createOrUpdateFramework(
			@Valid @RequestBody(required = false) JavaScriptFramework resource) {
		if (resource == null) {
			return new ResponseEntity<JavaScriptFramework>(HttpStatus.OK);
		}
		return new ResponseEntity<JavaScriptFramework>(apiService.createFramework(resource), HttpStatus.CREATED);
	}

	@PutMapping("/frameworks/{id}/versions")
	public ResponseEntity<JavaScriptFramework> createOrUpdateVersion(@PathVariable("id") Long id,
			@Valid @RequestBody(required = false) JavaScriptFrameworkVersion resource)
			throws FrameworkDoesNotExistException {
		if (resource == null) {
			return new ResponseEntity<JavaScriptFramework>(HttpStatus.OK);
		}
		JavaScriptFramework result = apiService.addVersionToFrameworkId(id, resource);
		return new ResponseEntity<JavaScriptFramework>(result, HttpStatus.CREATED);
	}

}
