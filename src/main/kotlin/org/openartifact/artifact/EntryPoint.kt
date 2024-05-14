/*
 * Copyright Artifact-Engine (c) 2024.
 *
 * This program is free software: you can redistribute it and/or modify it under the terms of the GNU General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.
 */

package org.openartifact.artifact

import io.github.classgraph.ClassGraph
import org.openartifact.artifact.core.Application
import org.openartifact.artifact.core.ApplicationEntry
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