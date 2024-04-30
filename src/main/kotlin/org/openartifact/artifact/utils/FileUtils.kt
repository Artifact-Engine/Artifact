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

object FileConstants {

    fun engine() : File =
        File(System.getProperty("user.home"), ".artifactengine")

    fun engineData() : File =
        File(engine(), "engineData")

    fun games() : File =
        File(engine(), "games")

    fun game() : File =
        File(games(), Context.current().applicationProfile().projectId)

    fun gameData() : File =
        File(game(), "gameData")

    fun scenes() : File =
        File(gameData(), "scenes")

    fun shaders() : File =
        File(engineData(), "shaders")

    fun shaderFile(fileName : String) =
        File(shaders(), fileName)
}