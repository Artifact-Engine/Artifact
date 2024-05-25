package org.openartifact.artifact.resource

import java.io.File
import java.nio.file.Files
import java.nio.file.Path

class Resource(val name : String, val path : String, internal var isCached : Boolean = false) {

    private var extractedResource : ExtractedResource? = null

    init {
        isCached = true
        cached.add(this)
    }

    fun extract() : ExtractedResource {
        if (extractedResource == null) {
            extractedResource = ExtractedResource(this)
            extractedResource!!.extract()
        }
        return extractedResource!!
    }

    fun asText() : String {
        return javaClass.getResourceAsStream("/$path")?.reader()?.readText()
            ?: throw IllegalStateException("org.openartifact.artifact.resource.Resource $path not found.")
    }

    inner class ExtractedResource(private val resource : Resource) {
        private lateinit var tempFile : Path

        fun extract() : ExtractedResource {
            val inputStream =
                javaClass.getResourceAsStream("/${resource.path}")
                    ?: throw IllegalStateException("org.openartifact.artifact.resource.Resource ${resource.path} not found.")

            tempFile = Files.createTempFile("game-resource-", ".tmp")
            tempFile.toFile().deleteOnExit()

            Files.copy(inputStream, tempFile, java.nio.file.StandardCopyOption.REPLACE_EXISTING)
            inputStream.close()

            return this
        }

        val path : Path
            get() {
                if (!::tempFile.isInitialized) {
                    throw IllegalStateException("org.openartifact.artifact.resource.Resource has not been extracted yet.")
                }
                return tempFile
            }

        val file : File
            get() = path.toFile()

    }
}