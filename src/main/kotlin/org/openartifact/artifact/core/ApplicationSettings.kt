package org.openartifact.artifact.core

import org.openartifact.artifact.core.graphics.RendererType

data class ApplicationSettings(
    val name : String,
    val currentScene : String,
    val rendererType : RendererType
)