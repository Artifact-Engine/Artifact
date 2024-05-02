package org.openartifact.artifact

import org.openartifact.artifact.core.Application
import org.openartifact.artifact.core.Artifact
import org.openartifact.artifact.core.createInstance

fun main() {
    val application = createInstance<Application>("Sandbox")

    Artifact.create(application!!).init()
}