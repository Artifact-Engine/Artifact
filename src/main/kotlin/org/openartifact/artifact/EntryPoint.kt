package org.openartifact.artifact

import io.github.classgraph.ClassGraph
import org.openartifact.artifact.core.Application
import org.openartifact.artifact.core.Artifact
import org.openartifact.artifact.core.createInstance
import org.slf4j.LoggerFactory

private val logger = LoggerFactory.getLogger("Artifact EntryPoint")

internal var timeInit = System.currentTimeMillis()

/**
 * Main function. Launches the engine and the application.
 */
fun main() {
    val application : Application = searchApplication()

    logger.info("Loading Artifact engine.")
    Artifact.launch(application)
}


/**
 * A method that searches for an [Application] on the classpath.
 * It detects classes that are annotated by [ApplicationEntry] and inherit [Application].
 */
private fun searchApplication() : Application {
    logger.info("Searching for applications using ClassGraph...")

    ClassGraph()
        .enableClassInfo()
        .enableAnnotationInfo()
        .scan().use { scanResult ->
            for (classInfo in scanResult.getClassesWithAnnotation(ApplicationEntry::class.java)) {
                logger.info("Found application candidate: ${classInfo.simpleName}")
                return createInstance<Application>(classInfo.loadClass())!!
            }
        }

    throw IllegalStateException("Failed to find application.")
}