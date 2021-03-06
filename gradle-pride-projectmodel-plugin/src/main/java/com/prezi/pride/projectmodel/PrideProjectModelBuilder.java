package com.prezi.pride.projectmodel;

import com.google.common.collect.ImmutableSet;
import org.gradle.api.Project;
import org.gradle.tooling.provider.model.ToolingModelBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.Set;

public class PrideProjectModelBuilder implements ToolingModelBuilder {
	private static final Logger logger = LoggerFactory.getLogger(PrideProjectModelBuilder.class);

	@Override
	public boolean canBuild(String modelName) {
		return modelName.equals(PrideProjectModel.class.getName());
	}

	@Override
	public Object buildAll(String s, Project project) {
		return convertProject(project.getRootProject());
	}

	private PrideProjectModel convertProject(Project project) {
		ImmutableSet.Builder<PrideProjectModel> childModels = ImmutableSet.builder();
		for (Project childProject : project.getChildProjects().values()) {
			childModels.add(convertProject(childProject));
		}
		if (project.getGroup() == null || String.valueOf(project.getGroup()).isEmpty()) {
			throw new IllegalStateException("Group is not specified for project in " + project.getProjectDir());
		}

		Map<String, Set<DynamicDependency>> dynamicDependencies = DynamicDependenciesExtractor.getDynamicDependencies(project);
		logger.debug("Dynamic dependencies for {}: {}", project, dynamicDependencies);

		return new DefaultPrideProjectModel(
				project.getPath(),
				String.valueOf(project.getGroup()),
				project.getName(),
				String.valueOf(project.getVersion()),
				dynamicDependencies,
				childModels.build(),
				project.getProjectDir().getAbsolutePath()
		);
	}
}
