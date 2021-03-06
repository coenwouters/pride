package com.prezi.pride.projectmodel;

import java.io.Serializable;
import java.util.Map;
import java.util.Set;

public class DefaultPrideProjectModel implements Serializable, PrideProjectModel {
	private final String path;
	private final String group;
	private final String name;
	private final String version;
	private final Map<String, Set<DynamicDependency>> dynamicDependencies;
	private final Set<PrideProjectModel> children;
	private final String projectDir;

	public DefaultPrideProjectModel(String path, String group, String name, String version, Map<String, Set<DynamicDependency>> dynamicDependencies, Set<PrideProjectModel> children, String projectDir) {
		this.path = path;
		this.group = group;
		this.name = name;
		this.version = version;
		this.dynamicDependencies = dynamicDependencies;
		this.children = children;
		this.projectDir = projectDir;
	}

	@Override
	public String toString() {
		return "PrideProject{path='" + path + "\'}";
	}

	@Override
	public String getPath() {
		return path;
	}

	@Override
	public String getGroup() {
		return group;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public String getVersion() {
		return version;
	}

	@Override
	public Map<String, Set<DynamicDependency>> getDynamicDependencies() {
		return dynamicDependencies;
	}

	@Override
	public Set<PrideProjectModel> getChildren() {
		return children;
	}

	@Override
	public String getProjectDir() {
		return projectDir;
	}

	@Override
	@SuppressWarnings("RedundantIfStatement")
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		DefaultPrideProjectModel that = (DefaultPrideProjectModel) o;

		if (!children.equals(that.children)) return false;
		if (group != null ? !group.equals(that.group) : that.group != null) return false;
		if (!name.equals(that.name)) return false;
		if (!path.equals(that.path)) return false;

		return true;
	}

	@Override
	public int hashCode() {
		int result = path.hashCode();
		result = 31 * result + (group != null ? group.hashCode() : 0);
		result = 31 * result + name.hashCode();
		result = 31 * result + children.hashCode();
		return result;
	}
}
