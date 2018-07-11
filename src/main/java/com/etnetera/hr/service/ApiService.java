package com.etnetera.hr.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import javax.validation.constraints.NotNull;

import org.springframework.transaction.annotation.Transactional;

import com.etnetera.hr.data.JavaScriptFramework;
import com.etnetera.hr.data.JavaScriptFrameworkVersion;
import com.etnetera.hr.exception.FrameworkDoesNotExistException;
import com.etnetera.hr.repository.JavaScriptFrameworkRepository;
import com.google.common.collect.Sets;

/**
 * 
 * Service for common CRUD functionality
 * 
 * @author Bogdan
 *
 */
public class ApiService {

	private JavaScriptFrameworkRepository repository;

	public ApiService(JavaScriptFrameworkRepository repository) {
		this.repository = repository;
	}

	@Transactional
	public JavaScriptFramework createFramework(@NotNull JavaScriptFramework jsframework) {
		return repository.save(jsframework);
	}

	@Transactional
	public JavaScriptFramework addVersionToFrameworkId(Long id, JavaScriptFrameworkVersion jsversion)
			throws FrameworkDoesNotExistException {
		return addVersionSetToFrameworkById(id, Sets.newHashSet(jsversion));
	}

	@Transactional
	public JavaScriptFramework addVersionSetToFrameworkById(Long id, Set<JavaScriptFrameworkVersion> versionSet)
			throws FrameworkDoesNotExistException {
		Optional<JavaScriptFramework> jsframeworkOpt = repository.findById(id);
		if (jsframeworkOpt.isPresent()) {
			JavaScriptFramework jsframework = jsframeworkOpt.get();
			for (JavaScriptFrameworkVersion jsv : versionSet) {
				jsframework.addVersion(jsv);
			}
			return repository.save(jsframework);
		}
		throw new FrameworkDoesNotExistException(id);
	}

	@Transactional
	public boolean deleteFrameworkById(Long id) {
		if (repository.existsById(id)) {
			repository.deleteById(id);
			return true;
		}
		return false;
	}

	@Transactional
	public boolean deleteVersionById(Long idf, Long idv) throws FrameworkDoesNotExistException {
		Optional<JavaScriptFramework> jsframework = repository.findById(idf);
		if (jsframework.isPresent()) {
			List<JavaScriptFrameworkVersion> versions = jsframework.get().getVersions();
			Optional<JavaScriptFrameworkVersion> result = versions.stream().filter(v -> v.getId().equals(idv))
					.findFirst();
			if (result.isPresent()) {
				jsframework.get().deleteVersion(result.get());
				result.get().setFramework(null);
				repository.save(jsframework.get());
				return true;
			}
			return false;
		}
		throw new FrameworkDoesNotExistException(idf);
	}

	@Transactional
	public List<JavaScriptFramework> findFrameworks(String name, Integer hypeLevel, LocalDate deprecationDate) {
		Iterable<JavaScriptFramework> jsframeworkIterable = repository.findAllByOrderByIdAsc();
		List<JavaScriptFramework> result = StreamSupport.stream(jsframeworkIterable.spliterator(), false)
				.filter(f -> name == null || f.getName().equals(name))
				.filter(f -> hypeLevel == null || f.getHypeLevel() == null || f.getHypeLevel().equals(hypeLevel))
				.filter(f -> deprecationDate == null || f.getDeprecationDate() == null
						|| f.getDeprecationDate().equals(deprecationDate))
				.collect(Collectors.toList());
		return result;
	}

	@Transactional
	public Optional<JavaScriptFramework> findFrameworkById(Long id) {
		Optional<JavaScriptFramework> result = repository.findById(id);
		return result;
	}

	@Transactional
	public Optional<JavaScriptFrameworkVersion> findVersionById(Long idf, Long idv)
			throws FrameworkDoesNotExistException {
		Optional<JavaScriptFramework> jsframework = repository.findById(idf);
		if (jsframework.isPresent()) {
			List<JavaScriptFrameworkVersion> versions = jsframework.get().getVersions();
			Optional<JavaScriptFrameworkVersion> result = versions.stream().filter(v -> v.getId().equals(idv))
					.findFirst();
			return result;
		}
		throw new FrameworkDoesNotExistException(idf);
	}

	@Transactional
	public List<JavaScriptFrameworkVersion> findVersion(Long id, String version) throws FrameworkDoesNotExistException {
		Optional<JavaScriptFramework> jsframework = repository.findById(id);
		if (jsframework.isPresent()) {
			List<JavaScriptFrameworkVersion> result = StreamSupport
					.stream(jsframework.get().getVersions().spliterator(), false)
					.filter(v -> version == null || v.getVersion().equals(version)).collect(Collectors.toList());
			return result;
		}
		throw new FrameworkDoesNotExistException(id);
	}

}
