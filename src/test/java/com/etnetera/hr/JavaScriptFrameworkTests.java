package com.etnetera.hr;

import static org.hamcrest.CoreMatchers.equalTo;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import com.etnetera.hr.data.JavaScriptFramework;
import com.etnetera.hr.data.JavaScriptFrameworkVersion;
import com.etnetera.hr.testService.TestWithPreparedDataset;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Class used for Spring Boot/MVC based tests. Test for JavaScriptFramework CRUD
 * functionality.
 * 
 * @author Bogdan
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class JavaScriptFrameworkTests extends TestWithPreparedDataset {

	@Autowired
	private MockMvc mockMvc;

	private ObjectMapper mapper = new ObjectMapper();

	@Test
	public void getFrameworkByIdTest() throws Exception {
		mockMvc.perform(get("/frameworks/1")).andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8)).andExpect(jsonPath("$.id", is(1)))
				.andExpect(jsonPath("$.name", is("ReactJS"))).andExpect(jsonPath("$.hypeLevel", is(90)))
				.andExpect(jsonPath("$.deprecationDate", equalTo(localDateString)))
				.andExpect(jsonPath("$.versions", hasSize(5)))
				.andExpect(jsonPath("$.versions[?(@.id == 1)].version", hasItems("0.3-stable")))
				.andExpect(jsonPath("$.versions[?(@.id == 2)].version", hasItems("0.4-stable")))
				.andExpect(jsonPath("$.versions[?(@.id == 3)].version", hasItems("0.5-stable")))
				.andExpect(jsonPath("$.versions[?(@.id == 4)].version", hasItems("0.8-stable")))
				.andExpect(jsonPath("$.versions[?(@.id == 5)].version", hasItems("0.9-stable")));

		mockMvc.perform(get("/frameworks/99")).andExpect(status().isNotFound());
	}

	@Test
	public void getFrameworksTest() throws Exception {
		mockMvc.perform(get("/frameworks")).andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8)).andExpect(jsonPath("$", hasSize(5)))
				.andExpect(jsonPath("$[0].id", is(1))).andExpect(jsonPath("$[0].name", is("ReactJS")))
				.andExpect(jsonPath("$[0].hypeLevel", is(90)))
				.andExpect(jsonPath("$[0].deprecationDate", equalTo(localDateString)))
				.andExpect(jsonPath("$[0].versions", hasSize(5)))
				.andExpect(jsonPath("$[0].versions[?(@.id == 1)].version", hasItems("0.3-stable")))
				.andExpect(jsonPath("$[0].versions[?(@.id == 2)].version", hasItems("0.4-stable")))
				.andExpect(jsonPath("$[0].versions[?(@.id == 3)].version", hasItems("0.5-stable")))
				.andExpect(jsonPath("$[0].versions[?(@.id == 4)].version", hasItems("0.8-stable")))
				.andExpect(jsonPath("$[0].versions[?(@.id == 5)].version", hasItems("0.9-stable")))
				.andExpect(jsonPath("$[1].id", is(2))).andExpect(jsonPath("$[1].name", is("Vue.js")))
				.andExpect(jsonPath("$[1].hypeLevel", is(60)))
				.andExpect(jsonPath("$[1].deprecationDate", equalTo(localDateString)))
				.andExpect(jsonPath("$[1].versions", hasSize(3)))
				.andExpect(jsonPath("$[1].versions[?(@.id == 6)].version", hasItems("v3-dev")))
				.andExpect(jsonPath("$[1].versions[?(@.id == 7)].version", hasItems("v2-dev")))
				.andExpect(jsonPath("$[1].versions[?(@.id == 8)].version", hasItems("v1-dev")))
				.andExpect(jsonPath("$[2].id", is(3))).andExpect(jsonPath("$[2].name", is("Angular.js")))
				.andExpect(jsonPath("$[2].hypeLevel", is(70)))
				.andExpect(jsonPath("$[2].deprecationDate", equalTo(localDateString)))
				.andExpect(jsonPath("$[2].versions", hasSize(2)))
				.andExpect(jsonPath("$[2].versions[?(@.id == 9)].version", hasItems("v1.7.x")))
				.andExpect(jsonPath("$[2].versions[?(@.id == 10)].version", hasItems("v1.6.x")))
				.andExpect(jsonPath("$[3].id", is(4))).andExpect(jsonPath("$[3].name", is("Three.js")))
				.andExpect(jsonPath("$[3].hypeLevel", is(50)))
				.andExpect(jsonPath("$[3].deprecationDate", equalTo(localDateString)))
				.andExpect(jsonPath("$[3].versions", hasSize(1)))
				.andExpect(jsonPath("$[3].versions[?(@.id == 11)].version", hasItems("dev")))
				.andExpect(jsonPath("$[4].id", is(5))).andExpect(jsonPath("$[4].name", is("Bootstrap")))
				.andExpect(jsonPath("$[4].hypeLevel", is(90)))
				.andExpect(jsonPath("$[4].deprecationDate", equalTo(localDateString)))
				.andExpect(jsonPath("$[4].versions", hasSize(0)));
	}

	@Test
	public void getFrameworksByParamsTest() throws Exception {
		mockMvc.perform(get("/frameworks/params?name=Angular.js")).andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8)).andExpect(jsonPath("$[0].id", is(3)))
				.andExpect(jsonPath("$[0].name", is("Angular.js")))
				.andExpect(jsonPath("$[0].deprecationDate", equalTo(localDateString)))
				.andExpect(jsonPath("$[0].hypeLevel", is(70))).andExpect(jsonPath("$[0].versions", hasSize(2)))
				.andExpect(jsonPath("$[0].versions[?(@.id == 9)].version", hasItems("v1.7.x")))
				.andExpect(jsonPath("$[0].versions[?(@.id == 10)].version", hasItems("v1.6.x")));

		mockMvc.perform(get("/frameworks/params?name=NotExistingFramework")).andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8)).andExpect(jsonPath("$", hasSize(0)));
	}

	@Test
	public void deleteFrameworkByIdTest() throws Exception {
		mockMvc.perform(delete("/frameworks/1")).andExpect(status().isNoContent());

		mockMvc.perform(delete("/frameworks/99")).andExpect(status().isNotFound());
	}

	@Test
	public void createOrUpdateFrameworkTest() throws Exception {
		JavaScriptFramework frameworkWithVersions = createFramework("testFramework", 66, null, "ver 1", "ver 2");
		mockMvc.perform(put("/frameworks").contentType(MediaType.APPLICATION_JSON)
				.content(mapper.writeValueAsBytes(frameworkWithVersions))).andExpect(status().isCreated())
				.andExpect(jsonPath("$.id", is(6))).andExpect(jsonPath("$.name", is("testFramework")))
				.andExpect(jsonPath("$.deprecationDate", equalTo(null))).andExpect(jsonPath("$.hypeLevel", is(66)))
				.andExpect(jsonPath("$.versions", hasSize(2)))
				.andExpect(jsonPath("$.versions[?(@.id == 12)].version", hasItems("ver 1")))
				.andExpect(jsonPath("$.versions[?(@.id == 13)].version", hasItems("ver 2")));

		frameworkWithVersions.setId(6L);
		frameworkWithVersions.setName("changedFramework");
		frameworkWithVersions.setHypeLevel(1);
		frameworkWithVersions.deleteVersion(frameworkWithVersions.getVersions().iterator().next());
		JavaScriptFrameworkVersion changedVersion = frameworkWithVersions.getVersions().iterator().next();
		changedVersion.setVersion("ver 11");
		changedVersion.setId(13L);
		mockMvc.perform(put("/frameworks").contentType(MediaType.APPLICATION_JSON)
				.content(mapper.writeValueAsBytes(frameworkWithVersions))).andExpect(status().isCreated())
				.andExpect(jsonPath("$.id", is(6))).andExpect(jsonPath("$.name", is("changedFramework")))
				.andExpect(jsonPath("$.deprecationDate", equalTo(null))).andExpect(jsonPath("$.hypeLevel", is(1)))
				.andExpect(jsonPath("$.versions", hasSize(1)))
				.andExpect(jsonPath("$.versions[?(@.id == 13)].version", hasItems("ver 11")));

		JavaScriptFramework frameworkWithoutVersions = createFramework("testFramework2", 44, null);
		mockMvc.perform(put("/frameworks").contentType(MediaType.APPLICATION_JSON)
				.content(mapper.writeValueAsBytes(frameworkWithoutVersions))).andExpect(status().isCreated())
				.andExpect(jsonPath("$.id", is(7))).andExpect(jsonPath("$.name", is("testFramework2")))
				.andExpect(jsonPath("$.deprecationDate", equalTo(null))).andExpect(jsonPath("$.hypeLevel", is(44)))
				.andExpect(jsonPath("$.versions", hasSize(0)));

		frameworkWithoutVersions.setId(7L);
		frameworkWithoutVersions.setName("changedFramework2");
		frameworkWithoutVersions.setHypeLevel(2);
		frameworkWithoutVersions.addVersion(new JavaScriptFrameworkVersion("ver 0"));
		frameworkWithoutVersions.addVersion(new JavaScriptFrameworkVersion("ver 1"));
		frameworkWithoutVersions.addVersion(new JavaScriptFrameworkVersion("ver 2"));
		mockMvc.perform(put("/frameworks").contentType(MediaType.APPLICATION_JSON)
				.content(mapper.writeValueAsBytes(frameworkWithoutVersions))).andExpect(status().isCreated())
				.andExpect(jsonPath("$.id", is(7))).andExpect(jsonPath("$.name", is("changedFramework2")))
				.andExpect(jsonPath("$.deprecationDate", equalTo(null))).andExpect(jsonPath("$.hypeLevel", is(2)))
				.andExpect(jsonPath("$.versions", hasSize(3)))
				.andExpect(jsonPath("$.versions[?(@.id == 14)].version", hasItems("ver 0")))
				.andExpect(jsonPath("$.versions[?(@.id == 15)].version", hasItems("ver 1")))
				.andExpect(jsonPath("$.versions[?(@.id == 16)].version", hasItems("ver 2")));

		mockMvc.perform(put("/frameworks").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
	}

	@Test
	public void addFrameworkInvalidTest() throws JsonProcessingException, Exception {
		JavaScriptFramework framework = new JavaScriptFramework();
		mockMvc.perform(
				put("/frameworks").contentType(MediaType.APPLICATION_JSON).content(mapper.writeValueAsBytes(framework)))
				.andExpect(status().isBadRequest()).andExpect(jsonPath("$.errors", hasSize(1)))
				.andExpect(jsonPath("$.errors[0].field", is("name")))
				.andExpect(jsonPath("$.errors[0].message", is("NotBlank")));

		framework.setName("verylongnameofthejavascriptframeworkjavaisthebestintheworld");
		mockMvc.perform(
				put("/frameworks").contentType(MediaType.APPLICATION_JSON).content(mapper.writeValueAsBytes(framework)))
				.andExpect(status().isBadRequest()).andExpect(jsonPath("$.errors", hasSize(1)))
				.andExpect(jsonPath("$.errors[0].field", is("name")))
				.andExpect(jsonPath("$.errors[0].message", is("Size")));

		framework.setName("ReactJS");
		framework.setHypeLevel(-10);
		mockMvc.perform(
				put("/frameworks").contentType(MediaType.APPLICATION_JSON).content(mapper.writeValueAsBytes(framework)))
				.andExpect(status().isBadRequest()).andExpect(jsonPath("$.errors", hasSize(1)))
				.andExpect(jsonPath("$.errors[0].field", is("hypeLevel")))
				.andExpect(jsonPath("$.errors[0].message", is("Min")));

		framework.setHypeLevel(101);
		mockMvc.perform(
				put("/frameworks").contentType(MediaType.APPLICATION_JSON).content(mapper.writeValueAsBytes(framework)))
				.andExpect(status().isBadRequest()).andExpect(jsonPath("$.errors", hasSize(1)))
				.andExpect(jsonPath("$.errors[0].field", is("hypeLevel")))
				.andExpect(jsonPath("$.errors[0].message", is("Max")));
	}

}
