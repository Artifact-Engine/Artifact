package org.openartifact.artifact

import org.openartifact.artifact.core.Application
import org.openartifact.artifact.core.Artifact
import org.openartifact.artifact.core.createInstance

fun main() {
    val application : Application = createInstance<Application>("Sandbox")!!

    Artifact.create(application)
}