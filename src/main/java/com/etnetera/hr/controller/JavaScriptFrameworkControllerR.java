package com.etnetera.hr.controller;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.etnetera.hr.data.JavaScriptFramework;
import com.etnetera.hr.data.JavaScriptFrameworkVersion;
import com.etnetera.hr.exception.FrameworkDoesNotExistException;
import com.etnetera.hr.service.ApiService;

/**
 * Simple REST controller for Read functionality.
 * 
 * @author Bogdan
 *
 */
@RestController
public class JavaScriptFrameworkControllerR extends EtnRestController {

	private final ApiService apiService;

	@Autowired
	public JavaScriptFrameworkControllerR(ApiService apiService) {
		this.apiService = apiService;
	}

	@GetMapping("/frameworks")
	public ResponseEntity<List<JavaScriptFramework>> getFrameworks() {
		return new ResponseEntity<List<JavaScriptFramework>>(apiService.findFrameworks(null, null, null),
				HttpStatus.OK);
	}

	@GetMapping("/frameworks/params")
	public ResponseEntity<List<JavaScriptFramework>> getFrameworksByParams(
			@RequestParam(value = "name", required = false) String name,
			@RequestParam(value = "hypeLevel", required = false) Integer hypeLevel,
			@RequestParam(value = "deprecationDate", required = false) LocalDate deprecationDate) {
		List<JavaScriptFramework> result = apiService.findFrameworks(name, hypeLevel, deprecationDate);
		return new ResponseEntity<List<JavaScriptFramework>>(result, HttpStatus.OK);
	}

	@GetMapping("/frameworks/{id}")
	public ResponseEntity<JavaScriptFramework> getFrameworkById(@PathVariable("id") Long id) {
		Optional<JavaScriptFramework> result = apiService.findFrameworkById(id);
		if (!result.isPresent()) {
			return new ResponseEntity<JavaScriptFramework>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<JavaScriptFramework>(result.get(), HttpStatus.OK);
	}

	@GetMapping("/frameworks/{id}/versions")
	public ResponseEntity<List<JavaScriptFrameworkVersion>> getVersions(@PathVariable("id") Long id) {
		Optional<JavaScriptFramework> result = apiService.findFrameworkById(id);
		if (result.isPresent()) {
			return new ResponseEntity<List<JavaScriptFrameworkVersion>>(result.get().getVersions(), HttpStatus.OK);
		}
		return new ResponseEntity<List<JavaScriptFrameworkVersion>>(HttpStatus.NOT_FOUND);
	}

	@GetMapping("/frameworks/{id}/versions/params")
	public ResponseEntity<List<JavaScriptFrameworkVersion>> getVersionsByParams(@PathVariable("id") Long id,
			@RequestParam(value = "version", required = false) String version) throws FrameworkDoesNotExistException {
		return new ResponseEntity<List<JavaScriptFrameworkVersion>>(apiService.findVersion(id, version), HttpStatus.OK);
	}

	@GetMapping("/frameworks/{idf}/versions/{idv}")
	public ResponseEntity<JavaScriptFrameworkVersion> getVersionById(@PathVariable("idf") Long idf,
			@PathVariable("idv") Long idv) throws FrameworkDoesNotExistException {
		Optional<JavaScriptFrameworkVersion> result = apiService.findVersionById(idf, idv);
		if (result.isPresent()) {
			return new ResponseEntity<JavaScriptFrameworkVersion>(result.get(), HttpStatus.OK);
		}
		return new ResponseEntity<JavaScriptFrameworkVersion>(HttpStatus.NOT_FOUND);
	}

}
