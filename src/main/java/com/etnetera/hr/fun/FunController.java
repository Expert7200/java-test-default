package com.etnetera.hr.fun;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 
 * @author Bogdan
 *
 */
@RestController
public class FunController {

	// Teapot
	@Validated
	@GetMapping("/whoareyou")
	public ResponseEntity<Photo> whoareyou() {
		return new ResponseEntity<Photo>(new Photo(), HttpStatus.I_AM_A_TEAPOT);
	}
}
