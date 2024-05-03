package org.openartifact.artifact.graphics.platform.vulkan

import org.openartifact.artifact.graphics.IGraphicsComponent
import org.openartifact.artifact.graphics.Renderer
import org.openartifact.artifact.graphics.RendererAPI
import kotlin.reflect.KClass

class VulkanRenderer : Renderer {

    override val api = RendererAPI.Vulkan

    override fun registerComponents() : Map<KClass<out IGraphicsComponent>, KClass<out IGraphicsComponent>> {
        TODO("Not yet implemented; Will not be implemented anytime soon.")
    }

}