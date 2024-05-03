package org.openartifact.artifact

import io.github.classgraph.ClassGraph
import org.openartifact.artifact.core.Application
import org.openartifact.artifact.core.Artifact
import org.openartifact.artifact.core.createInstance
import org.slf4j.LoggerFactory

internal val ENTRY_LOG = LoggerFactory.getLogger("Artifact EntryPoint")

internal var timeInit = System.currentTimeMillis()

fun main() {
    val application : Application = searchApplication()

    ENTRY_LOG.info("Loading Artifact engine.")
    Artifact.create(application)
}

fun searchApplication() : Application {
    ENTRY_LOG.info("Searching for applications using ClassGraph...")

    ClassGraph()
        .enableClassInfo()
        .enableAnnotationInfo()
        .scan().use { scanResult ->
            for (classInfo in scanResult.getClassesWithAnnotation(Entry::class.java)) {
                ENTRY_LOG.info("Found candidate: ${classInfo.simpleName}")
                return createInstance<Application>(classInfo.loadClass())!!
            }
        }

    throw IllegalStateException("Failed to find application. Maybe it wasn't annotated with @Entry?")
}