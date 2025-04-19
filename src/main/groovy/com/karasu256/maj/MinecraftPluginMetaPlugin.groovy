package com.karasu256.maj

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.yaml.snakeyaml.Yaml
import org.gradle.api.tasks.bundling.Jar

class MinecraftPluginMetaPlugin implements Plugin<Project> {
    @Override
    void apply(Project project) {
        project.afterEvaluate {
            def pluginYml = project.file("src/main/resources/plugin.yml")
            if (!pluginYml.exists()) {
                throw new IllegalStateException("plugin.yml not found")
            }

            def yaml = new Yaml().load(pluginYml.newInputStream())
            def version = yaml["version"] ?: "0.0.0"
            def apiVersion = yaml["api-version"] ?: "unknown"

            def branch = "nogit"
            def hash = "nogit"

            try {
                def stdout = new ByteArrayOutputStream()
                project.exec {
                    commandLine "git", "rev-parse", "--abbrev-ref", "HEAD"
                    standardOutput = stdout
                }
                branch = stdout.toString().trim()

                stdout = new ByteArrayOutputStream()
                project.exec {
                    commandLine "git", "rev-parse", "--short", "HEAD"
                    standardOutput = stdout
                }
                hash = stdout.toString().trim()
            } catch (ignored) {}

            def parts = [version, apiVersion]
            if (branch != "nogit" && hash != "nogit") {
                parts += [branch, hash]
            }

            project.tasks.withType(Jar).configureEach {
                archiveBaseName.set(project.name)
                archiveVersion.set(parts.join("-"))
                archiveClassifier.set('')
            }
        }
    }
}
