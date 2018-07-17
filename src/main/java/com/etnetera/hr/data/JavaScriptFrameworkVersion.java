package com.etnetera.hr.data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonBackReference;

/**
 * Simple data entity describing versions of every JavaScript framework.
 * 
 * @author Bogdan
 *
 */
@Entity
@Table(name = "Version")
public class JavaScriptFrameworkVersion {

	private Long id;

	private JavaScriptFramework framework;

	private String version;

	public JavaScriptFrameworkVersion() {
	}

	public JavaScriptFrameworkVersion(@NotNull String version) {
		this.setVersion(version);
	}

	public JavaScriptFrameworkVersion(Long id, @NotNull String version) {
		this.setId(id);
		this.setVersion(version);
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "version_id")
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@NotBlank(message = "Version can't be blank or null")
	@Size(max = 10, message = "Version shouldn't be longer than 10 symbols")
	@Column(nullable = false, length = 10)
	public String getVersion() {
		return version;
	}

	public void setVersion(@NotNull String version) {
		this.version = version;
	}

	@JsonBackReference
	@ManyToOne(optional = false)
	@JoinColumn(name = "framework_id", nullable = false)
	public JavaScriptFramework getFramework() {
		return framework;
	}

	public void setFramework(@NotNull JavaScriptFramework framework) {
		this.framework = framework;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((framework == null) ? 0 : framework.hashCode());
		result = prime * result + ((version == null) ? 0 : version.hashCode());
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
		JavaScriptFrameworkVersion other = (JavaScriptFrameworkVersion) obj;
		if (framework == null) {
			if (other.framework != null)
				return false;
		} else if (!framework.equals(other.framework))
			return false;
		if (version == null) {
			if (other.version != null)
				return false;
		} else if (!version.equals(other.version))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "JavaScriptFrameworkVersion [id=" + id + ", framework=" + framework.getId() + ", version=" + version
				+ "]";
	}

}
