package org.openartifact.artifact.graphics.platform.vulkan

import org.openartifact.artifact.graphics.IGraphicsComponent
import org.openartifact.artifact.graphics.Renderer
import kotlin.reflect.KClass

@Suppress("unused")
class VulkanRenderer : Renderer {

    override fun registerComponents() : Map<KClass<out IGraphicsComponent>, KClass<out IGraphicsComponent>> {
        TODO("Not yet implemented; Will not be implemented anytime soon.")
    }

}