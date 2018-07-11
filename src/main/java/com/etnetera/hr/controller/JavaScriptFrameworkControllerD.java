package com.etnetera.hr.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.etnetera.hr.data.JavaScriptFramework;
import com.etnetera.hr.exception.FrameworkDoesNotExistException;
import com.etnetera.hr.service.ApiService;

/**
 * Simple REST controller for Delete functionality.
 * 
 * @author Bogdan
 *
 */
@RestController
public class JavaScriptFrameworkControllerD extends EtnRestController {

	private final ApiService apiService;

	@Autowired
	public JavaScriptFrameworkControllerD(ApiService apiService) {
		this.apiService = apiService;
	}

	@DeleteMapping("/frameworks/{id}")
	public ResponseEntity<JavaScriptFramework> deleteFrameworkById(@PathVariable("id") Long id) {
		if (apiService.deleteFrameworkById(id)) {
			return new ResponseEntity<JavaScriptFramework>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<JavaScriptFramework>(HttpStatus.NOT_FOUND);
	}

	@DeleteMapping("/frameworks/{idf}/versions/{idv}")
	public ResponseEntity<JavaScriptFramework> deleteVersionById(@PathVariable("idf") Long idf,
			@PathVariable("idv") Long idv) throws FrameworkDoesNotExistException {
		if (apiService.deleteVersionById(idf, idv)) {
			return new ResponseEntity<JavaScriptFramework>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<JavaScriptFramework>(HttpStatus.NOT_FOUND);
	}

}
