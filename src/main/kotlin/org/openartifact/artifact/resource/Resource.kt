package org.openartifact.artifact.resource

import java.io.File
import java.io.InputStream
import java.nio.file.Files
import java.nio.file.Path

class Resource(val name : String, val path : String, internal var isCached : Boolean = false) {

    private var extracted : ExtractedResource? = null

    init {
        isCached = true
        cached.add(this)
    }

    val inputStream : InputStream? =
        javaClass.getResourceAsStream("/$path")

    fun extract() : ExtractedResource =
        (extracted ?: ExtractedResource(this))
            .apply { extracted = this }

    fun asText() : String {
        return inputStream?.reader()?.readText()
            ?: throw IllegalStateException("Resource $path not found.")
    }

    inner class ExtractedResource(resource : Resource) {

        private var tempFile : Path

        init {
            val inputStream =
                javaClass.getResourceAsStream("/${resource.path}")
                    ?: throw IllegalStateException("Resource ${resource.path} not found.")

            tempFile = Files.createTempFile("game-resource-", ".tmp")
            tempFile.toFile().deleteOnExit()

            Files.copy(inputStream, tempFile, java.nio.file.StandardCopyOption.REPLACE_EXISTING)
            inputStream.close()
        }

        val path : Path
            get() =
                tempFile


        val file : File
            get() = path.toFile()

    }
}