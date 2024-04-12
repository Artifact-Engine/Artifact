package org.openartifact.configuration

import org.slf4j.LoggerFactory
import java.io.File

private val logger = LoggerFactory.getLogger("ArtFile manager")

fun initializeFileStructure() {
    logger.info("Initializing FileStructure")
    getEngineDirectory()?.mkdir()
    getProjectsDirectory().mkdir()
}

// TODO: Mac support
/**
 * Returns the engine directory where settings etc. are stored in
 * @see getProjectsDirectory
 */
private fun getEngineDirectory(): File? =
    when (System.getProperty("os.name")) {
        "Linux", "Windows" ->
            File(System.getProperty("user.home") + File.separator + "ArtifactEngine")
        else ->
            null
    }

/**
 * Returns the directory all projects are stored in
 */
fun getProjectsDirectory(): File =
    File(getEngineDirectory(), "Projects")