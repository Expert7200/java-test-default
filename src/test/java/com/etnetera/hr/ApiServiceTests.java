//package com.etnetera.hr;
//
//import static org.junit.Assert.fail;
//import static org.mockito.Mockito.times;
//
//import java.time.LocalDate;
//import java.util.Optional;
//
//import org.junit.Before;
//import org.junit.Test;
//import org.mockito.Mockito;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
//import com.etnetera.hr.data.JavaScriptFramework;
//import com.etnetera.hr.data.JavaScriptFrameworkVersion;
//import com.etnetera.hr.exception.FrameworkDoesNotExistException;
//import com.etnetera.hr.repository.JavaScriptFrameworkRepository;
//import com.etnetera.hr.service.ApiService;
//
//public class ApiServiceTests {
//
//	private Logger log = LoggerFactory.getLogger(getClass());
//
//	private ApiService apiService;
//	private JavaScriptFrameworkRepository mockedRepository;
//
//	@Before
//	public void setUp() {
//		mockedRepository = Mockito.mock(JavaScriptFrameworkRepository.class);
//		apiService = new ApiService(mockedRepository);
//	}
//
//	@Test
//	public void createFramework() {
//		// TODO?
//	}
//
//	@Test
//	public void addVersionToFrameworkId() {
//		log.info("Test case: framework exists");
//		Long idf = 1L;
//		String version = "0.3-stable";
//		JavaScriptFrameworkVersion javaScriptVersion = vOf(1, version);
//		JavaScriptFramework framework = fOf(idf, "ReactJS", 90, LocalDate.now());
//		Mockito.when(mockedRepository.findById(idf)).thenReturn(Optional.<JavaScriptFramework>of(framework));
//		Mockito.verify(mockedRepository, times(1)).save(framework);
//		Mockito.verify(framework, times(1)).addVersion(javaScriptVersion);
//
//		log.info("Test case: framework doesn't exist");
//		idf = 1L;
//		version = "0.3-stable";
//		javaScriptVersion = vOf(1, version);
//		framework = fOf(idf, "ReactJS", 90, LocalDate.now());
//		Mockito.when(mockedRepository.findById(idf)).thenReturn(Optional.empty());
//		try {
//			apiService.addVersionToFrameworkId(idf, javaScriptVersion);
//			fail("Excpected exception FrameworkDoesNotExistException");
//		} catch (FrameworkDoesNotExistException e) {
//		}
//		Mockito.verify(mockedRepository, times(0)).save(framework);
//		Mockito.verify(framework, times(0)).addVersion(javaScriptVersion);
//
//	}
//
//	@Test
//	public void addVersionSetToFrameworkById() {
//		/* TODO */ }
//
//	@Test
//	public void deleteFrameworkById() {
//		/* TODO */ }
//
//	@Test
//	public void deleteVersionByVersion() {
//		log.info("Test case: framework exists, version found");
//		Long idf = 1L;
//		String version = "0.3-stable";
//		JavaScriptFrameworkVersion[] versions = { vOf(1, "0.3-stable"), vOf(2, "0.4-stable"), vOf(3, "0.5-stable"),
//				vOf(4, "0.8-stable"), vOf(5, "0.9-stable") };
//		JavaScriptFramework framework = fOf(idf, "ReactJS", 90, LocalDate.now(), versions);
//		Mockito.when(mockedRepository.findById(idf)).thenReturn(Optional.<JavaScriptFramework>of(framework));
//		apiService.deleteVersionByVersion(idf, version);
//		Mockito.verify(mockedRepository, times(1)).save(framework);
//
//		log.info("Test case: framework exists, version not found");
//		idf = 2L;
//		version = "alfabeta";
//		versions = new JavaScriptFrameworkVersion[] { vOf(1, "0.3-stable"), vOf(2, "0.4-stable"), vOf(3, "0.5-stable"),
//				vOf(4, "0.8-stable"), vOf(5, "0.9-stable") };
//		framework = fOf(idf, "Angular.js", 90, LocalDate.now(), versions);
//		Mockito.when(mockedRepository.findById(idf)).thenReturn(Optional.<JavaScriptFramework>of(framework));
//		apiService.deleteVersionByVersion(idf, version);
//		Mockito.verify(mockedRepository, times(0)).save(framework);
//
//		log.info("Test case: framework does not exist");
//		idf = 3L;
//		version = "0.3-stable";
//		versions = new JavaScriptFrameworkVersion[] { vOf(1, "0.3-stable"), vOf(2, "0.4-stable"), vOf(3, "0.5-stable"),
//				vOf(4, "0.8-stable"), vOf(5, "0.9-stable") };
//		// framework = fOf(idf, "Angular.js", 90, LocalDate.now(), versions);
//		Mockito.when(mockedRepository.findById(idf)).thenReturn(Optional.empty());
//		try {
//			apiService.deleteVersionByVersion(idf, version);
//			fail("Excpected exception FrameworkDoesNotExistException");
//		} catch (FrameworkDoesNotExistException e) {
//		}
//		Mockito.verify(mockedRepository, times(0)).save(framework);
//
//	}
//
//	@Test
//	public void findFrameworks() {
//		/* TODO */ }
//
//	@Test
//	public void findFrameworkById() {
//		/* TODO */ }
//
//	@Test
//	public void findVersionById() {
//		/* TODO */ }
//
//	@Test
//	public void findVersion() {
//		/* TODO */ }
//
//	@Test
//	public void deleteVersionById() {
//		log.info("Test case: framework exists, version exists");
//		Long idf = 1L;
//		Long idv = 5L;
//		JavaScriptFrameworkVersion[] versions = { vOf(1, "0.3-stable"), vOf(2, "0.4-stable"), vOf(3, "0.5-stable"),
//				vOf(4, "0.8-stable"), vOf(5, "0.9-stable") };
//		JavaScriptFramework framework = fOf(idf, "ReactJS", 90, LocalDate.now(), versions);
//		Mockito.when(mockedRepository.findById(idf)).thenReturn(Optional.<JavaScriptFramework>of(framework));
//
//		apiService.deleteVersionById(idf, idv);
//		Mockito.verify(mockedRepository, times(1)).save(framework);
//
//		log.info("Test case: framework exists, version does not exist");
//		idf = 2L;
//		idv = 6L;
//		versions = new JavaScriptFrameworkVersion[] { vOf(1, "0.3-stable"), vOf(2, "0.4-stable"), vOf(3, "0.5-stable"),
//				vOf(4, "0.8-stable"), vOf(5, "0.9-stable") };
//		framework = fOf(idf, "Angular.js", 90, LocalDate.now(), versions);
//		Mockito.when(mockedRepository.findById(idf)).thenReturn(Optional.<JavaScriptFramework>of(framework));
//
//		apiService.deleteVersionById(idf, idv);
//		Mockito.verify(mockedRepository, times(0)).save(framework);
//
//		log.info("Test case: framework does not exist");
//		idf = 3L;
//		idv = 1L;
//		versions = new JavaScriptFrameworkVersion[] { vOf(1, "0.3-stable"), vOf(2, "0.4-stable"), vOf(3, "0.5-stable"),
//				vOf(4, "0.8-stable"), vOf(5, "0.9-stable") };
//		// framework = fOf(idf, "Angular.js", 90, LocalDate.now(), versions);
//		Mockito.when(mockedRepository.findById(idf)).thenReturn(Optional.empty());
//
//		try {
//			apiService.deleteVersionById(idf, idv);
//			fail("Excpected exception FrameworkDoesNotExistException");
//		} catch (FrameworkDoesNotExistException e) {
//		}
//		Mockito.verify(mockedRepository, times(0)).save(framework);
//
//	}
//
//	/*
//	 * Create and return version of JavaScript framework
//	 */
//	private JavaScriptFrameworkVersion vOf(long id, String ver) {
//		JavaScriptFrameworkVersion version = new JavaScriptFrameworkVersion();
//		version.setId(id);
//		version.setVersion(ver);
//		return version;
//	}
//
//	/*
//	 * Create and return JavaScript framework
//	 */
//	private JavaScriptFramework fOf(long id, String name, Integer hypeLevel, LocalDate deprecationDate,
//			JavaScriptFrameworkVersion... versions) {
//		JavaScriptFramework jsframework = new JavaScriptFramework(name, hypeLevel, deprecationDate);
//		jsframework.setId(id);
//		for (JavaScriptFrameworkVersion version : versions) {
//			jsframework.addVersion(version);
//			version.setFramework(jsframework);
//		}
//		return jsframework;
//	}
//
//}
