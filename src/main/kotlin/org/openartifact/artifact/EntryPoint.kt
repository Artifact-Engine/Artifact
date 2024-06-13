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

import com.typesafe.config.ConfigFactory
import io.github.config4k.extract
import org.openartifact.artifact.core.application.Application
import org.openartifact.artifact.core.Artifact
import org.openartifact.artifact.core.application.ApplicationConfig
import org.openartifact.artifact.core.createInstance
import org.openartifact.artifact.resource.resource
import org.slf4j.LoggerFactory

private val logger = LoggerFactory.getLogger("Artifact EntryPoint")

internal var timeInit = System.currentTimeMillis()

/**
 * Main function. Launches the engine and the application.
 */
fun main() {
    val applicationConfig = ConfigFactory
        .parseString(resource("application.json").asText()).extract<ApplicationConfig>()

    val application: Application =
        createInstance<Application>(
            applicationConfig.mainClass
        ) ?: throw IllegalStateException("mainClass property not set or misleading.")

    application.config = applicationConfig

    logger.info(
        "Loading Artifact engine with Application " +
                "'${applicationConfig.name}' " +
                "${applicationConfig.version} from " +
                "${applicationConfig.mainClass}."
    )
    Artifact.launch(application)
}