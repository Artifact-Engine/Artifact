package org.openartifact.artifact.core

import org.openartifact.artifact.core.graphics.RenderAPI

class ApplicationProfile(
    val projectId : String,
    val displayTitle : String,
    val startingSceneId : String,
    val renderAPI : RenderAPI = RenderAPI.OpenGL
)