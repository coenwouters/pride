package com.prezi.gradle.pride

import org.gradle.api.Project
import org.gradle.api.artifacts.Dependency
import org.gradle.api.artifacts.ProjectDependency
import org.gradle.api.internal.GradleInternal
import org.gradle.api.invocation.Gradle
import org.gradle.configuration.BuildConfigurer
import org.gradle.testfixtures.ProjectBuilder
import spock.lang.Specification

/**
 * Created by lptr on 28/04/14.
 */
class PridePluginTest extends Specification {
	def "single project dependencies"() {
		def project = ProjectBuilder.builder().build()
		project.apply plugin: "pride"
		project.configurations.create("compile")
		project.configure(project.extensions.getByType(DynamicDependenciesExtension)) {
			compile "com.example:example-test:1.0"
		}
		evaluateAllProjects(project.gradle)

		expect:
		dependencies(project, "compile") == ["com.example:example-test:1.0"]
	}

	private static List<String> dependencies(Project project, String configuration) {
		project.configurations.getByName(configuration).dependencies.collect { Dependency dep ->
			if (dep instanceof ProjectDependency) {
				return "${dep.dependencyProject.path}@${dep.configuration}"
			} else {
				return "${dep.group}:${dep.name}:${dep.version}"
			}
		}
	}

	private static void evaluateAllProjects(Gradle gradle) {
		def internal = (GradleInternal) gradle
		internal.services.get(BuildConfigurer).configure(internal)
		internal.buildListenerBroadcaster.projectsEvaluated(internal)
	}
}