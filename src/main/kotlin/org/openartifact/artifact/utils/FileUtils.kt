package org.openartifact.artifact.utils

import org.openartifact.artifact.core.Context
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

fun createDirectory(file : File) {
    if (! file.exists()) file.mkdir()
}

fun getEngineDir() : File =
    File(System.getProperty("user.home"), ".artifactengine")

fun getGamesDir() : File =
    File(getEngineDir(), "games")

fun getGameDir() : File =
    File(getGamesDir(), Context.current().applicationProfile().projectId)

fun getGameDataDir() : File =
    File(getGameDir(), "gameData")

fun getScenesDir() : File =
    File(getGameDataDir(), "scenes")

fun getShadersDir() : File =
    File(getEngineDir(), "shaders")

fun getShaderFile(fileName : String) =
    File(getShadersDir(), fileName)