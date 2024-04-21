package org.openartifact.artifact.core

import org.openartifact.artifact.core.rendering.RendererType

data class ApplicationSettings(val name : String, val mainClass : String, val currentScene : String, val rendererType : RendererType)