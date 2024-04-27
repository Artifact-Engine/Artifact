package org.openartifact.artifact.utils

import org.openartifact.artifact.core.Engine
import org.openartifact.artifact.core.GameContext
import java.io.File

fun getChildFile(file : File, childFileName : String) : File =
    File(file, childFileName)

fun requireDirectory(file : File, directoryName : String) : File {
    val requiredDirectory = getChildFile(file, directoryName)
    require(requiredDirectory.exists() && requiredDirectory.isDirectory) {
        "The ${file.name} directory needs to include a $directoryName directory!"
    }

    return requiredDirectory
}

fun requireDirectory(file : File) : File {
    require(file.exists() && file.isDirectory) {
        "The ${file.name} directory needs to include a ${file.name} directory!"
    }

    return file
}

fun requireFile(file : File, fileName : String) : File {
    val requiredFile = getChildFile(file, fileName)
    require(requiredFile.exists() && requiredFile.isFile) {
        "The ${file.name} directory needs to include a $fileName file!"
    }
    return requiredFile
}

fun getEngineDir() : File =
    File(System.getProperty("user.home"), ".artifactengine")

fun getProjectsDir() : File =
    File(getEngineDir(), "projects")

fun getProjectDir() : File =
    File(getProjectsDir(), GameContext.current().application().profile.projectId)