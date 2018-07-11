package com.etnetera.hr;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.etnetera.hr.repository.JavaScriptFrameworkRepository;
import com.etnetera.hr.service.ApiService;

/**
 * Spring Boot application configuration class.
 * 
 * @author Bogdan
 *
 */
@Configuration
public class AppConfig {
	
	@Bean
	public ApiService apiService(@Autowired JavaScriptFrameworkRepository repository) {
		return new ApiService(repository);
	}

}
