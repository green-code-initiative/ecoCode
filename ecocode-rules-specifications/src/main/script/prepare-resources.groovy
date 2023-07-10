#!/usr/bin/env groovy
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.StandardCopyOption

Path projectBasedir = Path.of(project.basedir.absolutePath)

Path sourceDir = Path.of(Objects.requireNonNull(properties.sourceDir, "property must be set: sourceDir"))
Path targetDir = Path.of(Objects.requireNonNull(properties.targetDir, "property must be set: targetDir"))

def copyFile(Path basedirPath, Path source, Path target) {
	log.debug("Copy: {} -> {}", basedirPath.relativize(source), basedirPath.relativize(target))
	Files.createDirectories(target.parent)
	Files.copy(source, target, StandardCopyOption.REPLACE_EXISTING)
}

sourceDir.traverse(type: groovy.io.FileType.FILES, nameFilter: ~/.*\.html$/) { Path htmlPath ->
	def language = htmlPath.parent.fileName.toString()
	def languageDir = targetDir.resolve(language)

	def ruleDir = htmlPath.parent.parent
	def ruleKey = ruleDir.fileName.toString()

	def metadataFileSourcePath = ruleDir.resolve(ruleKey + ".json")

	def htmlTargetPath = languageDir.resolve(htmlPath.fileName)
	def metadataFileTargetPath = languageDir.resolve(metadataFileSourcePath.fileName)

	if(Files.exists(metadataFileSourcePath)) {
		copyFile(projectBasedir, htmlPath, htmlTargetPath)
		copyFile(projectBasedir, metadataFileSourcePath, metadataFileTargetPath)
	}
}
