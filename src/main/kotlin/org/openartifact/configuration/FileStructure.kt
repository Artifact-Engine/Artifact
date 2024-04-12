package org.openartifact.configuration

import java.io.File

fun initializeFileStructure() {
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