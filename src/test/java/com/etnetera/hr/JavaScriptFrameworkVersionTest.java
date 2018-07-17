package com.etnetera.hr;

import static org.hamcrest.CoreMatchers.hasItems;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import com.etnetera.hr.data.JavaScriptFrameworkVersion;
import com.etnetera.hr.testService.TestWithPreparedDataset2;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Class used for Spring Boot/MVC based tests. Test for
 * JavaScriptFrameworkVersion CRUD functionality.
 * 
 * @author Bogdan
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class JavaScriptFrameworkVersionTest extends TestWithPreparedDataset2 {

	@Autowired
	private MockMvc mockMvc;

	private ObjectMapper mapper = new ObjectMapper();

	private Logger log = LoggerFactory.getLogger(getClass());

	@Test
	public void getVersionByIdTest() throws Exception {
		log.debug("Test endpoint: GET:/frameworks/{id}/versions/{id}");
		log.debug("Test case: Framework with given ID exists. Version with given ID exists");
		mockMvc.perform(get("/frameworks/1/versions/5")).andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8)).andExpect(jsonPath("$.id", is(5)))
				.andExpect(jsonPath("$.version", is("0.9-stable")));

		log.debug("Test case: Framework with given ID exists. Version with given ID does not exist");
		mockMvc.perform(get("/frameworks/1/version/6")).andExpect(status().isNotFound());
	}

	@Test
	public void getVersionsTest() throws Exception {
		log.debug("Test endpoint: GET:/frameworks/{id}/versions");
		log.debug("Test case: Framework with given ID exists");
		mockMvc.perform(get("/frameworks/1/versions")).andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8)).andExpect(jsonPath("$", hasSize(5)))
				.andExpect(jsonPath("$[?(@.id == 1)].version", hasItems("0.3-stable")))
				.andExpect(jsonPath("$[?(@.id == 2)].version", hasItems("0.4-stable")))
				.andExpect(jsonPath("$[?(@.id == 3)].version", hasItems("0.5-stable")))
				.andExpect(jsonPath("$[?(@.id == 4)].version", hasItems("0.8-stable")))
				.andExpect(jsonPath("$[?(@.id == 5)].version", hasItems("0.9-stable")));
	}

	@Test
	public void getVersionsByParamsTest() throws Exception {
		log.debug("Test endpoint: GET:/frameworks/{id}/versions/params");
		log.debug("Test case: Framework with given ID exists. Version with given version name exists");
		mockMvc.perform(get("/frameworks/1/versions/params?version=0.5-stable")).andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8)).andExpect(jsonPath("$", hasSize(1)))
				.andExpect(jsonPath("$[?(@.id == 3)].version", hasItems("0.5-stable")));
	}

	@Test
	public void deleteVersionByIdTest() throws Exception {
		log.debug("Test endpoint: DELETE:/frameworks/{id}/versions/{id}");
		log.debug("Test case: Framework with given ID exists. Version with given ID exists");
		mockMvc.perform(delete("/frameworks/1/versions/1")).andExpect(status().isNoContent());

		log.debug("Test case: Framework with given ID exists. Version with given ID does not exist");
		mockMvc.perform(delete("/frameworks/1/versions/10")).andExpect(status().isNotFound());

		log.debug("Test case: Framework with given ID does not exist");
		mockMvc.perform(delete("/frameworks/10/versions/1")).andExpect(status().isBadRequest());
	}

	@Test
	public void createOrUpdateVersionTest() throws Exception {
		log.debug("Test endpoint: PUT:/frameworks/{id}/versions");
		log.debug("Test case: Framework with given ID exists. Version with given ID does not exist (create)");
		JavaScriptFrameworkVersion version = new JavaScriptFrameworkVersion("v1.7.x");
		mockMvc.perform(put("/frameworks/2/versions").contentType(MediaType.APPLICATION_JSON)
				.content(mapper.writeValueAsBytes(version))).andExpect(status().isCreated())
				.andExpect(jsonPath("$.versions", hasSize(1)))
				.andExpect(jsonPath("$.versions[?(@.id == 6)].version", hasItems("v1.7.x")));

		log.debug("Test case: Framework with given ID exists. Version with given ID exists (update)");
		version.setId(6L);
		version.setVersion("v1.6.x");
		mockMvc.perform(put("/frameworks/2/versions").contentType(MediaType.APPLICATION_JSON)
				.content(mapper.writeValueAsBytes(version))).andExpect(status().isCreated())
				.andExpect(jsonPath("$.versions", hasSize(1)))
				.andExpect(jsonPath("$.versions[?(@.id == 6)].version", hasItems("v1.6.x")));

		log.debug("Test case: Framework with given ID does not exist");
		mockMvc.perform(put("/frameworks/5/versions").contentType(MediaType.APPLICATION_JSON)
				.content(mapper.writeValueAsBytes(version))).andExpect(status().isBadRequest());

		log.debug("Test case: Framework with given ID exists. null body");
		mockMvc.perform(put("/frameworks/1/versions").contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());
	}

	@Test
	public void addFrameworkInvalidTest() throws JsonProcessingException, Exception {
		log.debug("Test endpoint: PUT:/frameworks/{id}/versions");
		log.debug("Test case: Framework with given ID exists. New Version's version name is null");
		JavaScriptFrameworkVersion version = new JavaScriptFrameworkVersion();
		mockMvc.perform(put("/frameworks/1/versions").contentType(MediaType.APPLICATION_JSON)
				.content(mapper.writeValueAsBytes(version))).andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.errors", hasSize(1))).andExpect(jsonPath("$.errors[0].field", is("version")))
				.andExpect(jsonPath("$.errors[0].message", is("NotBlank")));

		log.debug("Test case: Framework with given ID exists. New Version's version name is empty");
		version.setVersion("");
		mockMvc.perform(put("/frameworks/1/versions").contentType(MediaType.APPLICATION_JSON)
				.content(mapper.writeValueAsBytes(version))).andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.errors", hasSize(1))).andExpect(jsonPath("$.errors[0].field", is("version")))
				.andExpect(jsonPath("$.errors[0].message", is("NotBlank")));

		log.debug("Test case: Framework with given ID exists. New Version's version name is too long");
		version.setVersion("verylongversionname");
		mockMvc.perform(put("/frameworks/1/versions").contentType(MediaType.APPLICATION_JSON)
				.content(mapper.writeValueAsBytes(version))).andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.errors", hasSize(1))).andExpect(jsonPath("$.errors[0].field", is("version")))
				.andExpect(jsonPath("$.errors[0].message", is("Size")));
	}

}
