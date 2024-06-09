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

import org.openartifact.artifact.core.Application
import org.openartifact.artifact.core.Artifact
import org.openartifact.artifact.core.createInstance
import org.openartifact.artifact.resource.resource
import org.slf4j.LoggerFactory
import java.util.*

private val logger = LoggerFactory.getLogger("Artifact EntryPoint")

internal var timeInit = System.currentTimeMillis()

val applicationProperties = Properties()

fun loadProperties() {
    applicationProperties.load(resource("application.properties").inputStream)
}

/**
 * Main function. Launches the engine and the application.
 */
fun main() {
    loadProperties()
    val application : Application =
        createInstance<Application>(applicationProperties
            .getProperty("mainClass")) ?: throw IllegalStateException("mainClass property not set or misleading.")

    application.properties = applicationProperties

    logger.info("Loading Artifact engine with Application " +
            "'${application.properties.getProperty("name")}' " +
            "${application.properties.getProperty("version")} from " +
            "${application.properties.getProperty("mainClass")}.")
    Artifact.launch(application)
}