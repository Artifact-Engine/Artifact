package org.openartifact.artifact.resource

import java.io.File
import java.nio.file.Files
import java.nio.file.Path

class ExtractedResource(private val resource : Resource) {

    private lateinit var tempFile : Path

    fun extract() : ExtractedResource {
        val inputStream = this::class.java.classLoader.getResourceAsStream(resource.path)
            ?: throw IllegalStateException("Resource ${resource.path} not found.")

        tempFile = Files.createTempFile("game-resource-", ".tmp")
        tempFile.toFile().deleteOnExit()

        Files.copy(inputStream, tempFile, java.nio.file.StandardCopyOption.REPLACE_EXISTING)
        inputStream.close()

        return this
    }

    val path : Path
        get() {
            if (!::tempFile.isInitialized) {
                throw IllegalStateException("Resource has not been extracted yet.")
            }
            return tempFile
        }

    val file : File
        get() =
            path.toFile()

    companion object {

        fun from(resource : Resource) : ExtractedResource =
            ExtractedResource(resource)
    }
}