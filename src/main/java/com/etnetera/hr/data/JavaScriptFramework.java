package com.etnetera.hr.data;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.format.annotation.DateTimeFormat;

import com.etnetera.hr.utils.UniqueList;
import com.fasterxml.jackson.annotation.JsonManagedReference;

/**
 * Simple data entity describing basic properties of every JavaScript framework.
 * 
 * @author Bogdan
 *
 */
@Entity
@Table(name = "Framework")
public class JavaScriptFramework {

	private Long id;

	private String name;

	private LocalDate deprecationDate;

	private Integer hypeLevel = 0;

	private List<JavaScriptFrameworkVersion> versions = new UniqueList<>();

	public JavaScriptFramework() {
	}

	public JavaScriptFramework(String name) {
		this.name = name;
	}

	public JavaScriptFramework(@NotNull String name, Integer hypeLevel) {
		this.setName(name);
		this.setHypeLevel(hypeLevel);
	}

	public JavaScriptFramework(@NotNull String name, Integer hypeLevel, LocalDate deprecationDate) {
		this.setName(name);
		this.setHypeLevel(hypeLevel);
		this.setDeprecationDate(deprecationDate);
	}

	public JavaScriptFramework(@NotNull String name, Integer hypeLevel, LocalDate deprecationDate,
			List<JavaScriptFrameworkVersion> versions) {
		this.setName(name);
		this.setHypeLevel(hypeLevel);
		this.setDeprecationDate(deprecationDate);
		this.setVersions(versions);
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "framework_id", nullable = false, unique = true)
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@NotBlank(message = "Name can't be blank or null")
	@Size(max = 50, message = "Name shouldn't be longer than 50 symbols")
	@Column(nullable = false, length = 50)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@DateTimeFormat(pattern = "yyyy-MM-dd")
	public LocalDate getDeprecationDate() {
		return deprecationDate;
	}

	public void setDeprecationDate(LocalDate deprecationDate) {
		this.deprecationDate = deprecationDate;
	}

	@Min(value = 0, message = "HypeLevel should not be less than 0")
	@Max(value = 100, message = "HypeLevel should not be greater than 100")
	@Column(nullable = false)
	public Integer getHypeLevel() {
		return hypeLevel;
	}

	public void setHypeLevel(Integer hypeLevel) {
		this.hypeLevel = (hypeLevel == null) ? 100 : hypeLevel;
	}

	@Valid
	@NotNull
	@JsonManagedReference
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "framework", targetEntity = JavaScriptFrameworkVersion.class, orphanRemoval = true, fetch = FetchType.EAGER)
	public List<JavaScriptFrameworkVersion> getVersions() {
		return this.versions;
	}

	public void setVersions(List<JavaScriptFrameworkVersion> versionSet) {
		this.versions = versionSet;
	}

	public void addVersion(JavaScriptFrameworkVersion version) {
		if (getVersions().contains(version))
			return;
		for (JavaScriptFrameworkVersion v : getVersions()) {
			if (v.getId() != null ? v.getId().equals(version.getId()) : false) {
				deleteVersion(v);
				break;
			}
		}
		getVersions().add(version);
		if (version.getFramework() != this)
			version.setFramework(this);
	}

	public void deleteVersion(JavaScriptFrameworkVersion version) {
		if (!getVersions().contains(version))
			return;
		getVersions().remove(version);
		version.setFramework(null);
	}

	public JavaScriptFrameworkVersion findVersionWithId(Long id) {
		for (JavaScriptFrameworkVersion v : getVersions()) {
			if (v.getId().equals(id)) {
				return v;
			}
		}
		return null;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((deprecationDate == null) ? 0 : deprecationDate.hashCode());
		result = prime * result + ((hypeLevel == null) ? 0 : hypeLevel.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		JavaScriptFramework other = (JavaScriptFramework) obj;
		if (deprecationDate == null) {
			if (other.deprecationDate != null)
				return false;
		} else if (!deprecationDate.equals(other.deprecationDate))
			return false;
		if (hypeLevel == null) {
			if (other.hypeLevel != null)
				return false;
		} else if (!hypeLevel.equals(other.hypeLevel))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "JavaScriptFramework [id=" + id + ", name=" + name + "]";
	}

}
