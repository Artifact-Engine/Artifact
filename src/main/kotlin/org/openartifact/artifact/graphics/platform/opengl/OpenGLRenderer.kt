package org.openartifact.artifact.graphics.platform.opengl

import org.openartifact.artifact.graphics.IRendererComponent
import org.openartifact.artifact.graphics.Renderer
import org.openartifact.artifact.graphics.RendererAPI
import org.openartifact.artifact.graphics.interfaces.IContext
import org.openartifact.artifact.graphics.interfaces.IVertexBuffer
import kotlin.reflect.KClass

class OpenGLRenderer : Renderer {

    override val api = RendererAPI.OpenGL

    override fun registerComponents() : Map<KClass<out IRendererComponent>, KClass<out IRendererComponent>> =
        mapOf(
            IContext::class to OpenGLContext::class,
            IVertexBuffer::class to OpenGLVertexBuffer::class
        )

}