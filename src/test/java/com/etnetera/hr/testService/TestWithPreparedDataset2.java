package com.etnetera.hr.testService;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import javax.persistence.EntityManager;
import javax.validation.constraints.NotNull;

import org.junit.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.etnetera.hr.data.JavaScriptFramework;
import com.etnetera.hr.data.JavaScriptFrameworkVersion;
import com.etnetera.hr.repository.JavaScriptFrameworkRepository;

/**
 * Class, provides dataset with 2 different frameworks.
 * 
 * @author Bogdan
 *
 */
public class TestWithPreparedDataset2 {

	@Autowired
	public JavaScriptFrameworkRepository repository;

	@Autowired
	private EntityManager entityManager;

	protected LocalDate localDate = LocalDate.now();

	protected String localDateString = localDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));

	@Before
	@Transactional
	public void prepareData() throws Exception {
		resetSequence("framework", "framework_id");
		resetSequence("version", "version_id");

		repository.save(createFramework("ReactJS", 90, localDate, "0.3-stable", "0.4-stable", "0.5-stable",
				"0.8-stable", "0.9-stable"));
		repository.save(createFramework("Angular.js", 90, localDate));
	}

	/**
	 * Resets auto increment value for given column in given table.
	 * 
	 * @param tableName name of the table in String format
	 * @param columnName name of the column in String format
	 */
	private void resetSequence(String tableName, String columnName) {
		entityManager.createNativeQuery("ALTER TABLE " + tableName + " ALTER COLUMN " + columnName + " RESTART WITH 1")
				.executeUpdate();
	}

	/**
	 * Creates instance of JavaScriptFramework class with given parameters 
	 * 
	 * @param name name of framework in String format
	 * @param hypeLevel hype level of framework in Integer format
	 * @param deprecationDate deprecation date of framework in LocalDate format
	 * @param versionArgs arguments in String format for versions of framework
	 * @return returns created instance of JavaScriptFramework
	 */
	public JavaScriptFramework createFramework(@NotNull String name, Integer hypeLevel, LocalDate deprecationDate,
			String... versionArgs) {
		JavaScriptFramework jsframework = new JavaScriptFramework(name, hypeLevel, deprecationDate);
		for (String s : versionArgs) {
			JavaScriptFrameworkVersion v = new JavaScriptFrameworkVersion(s);
			jsframework.addVersion(v);
		}
		return jsframework;
	}

}