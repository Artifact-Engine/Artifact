package org.openartifact.artifact.graphics.platform.opengl

import org.openartifact.artifact.graphics.IGraphicsComponent
import org.openartifact.artifact.graphics.Renderer
import org.openartifact.artifact.graphics.RendererAPI
import org.openartifact.artifact.graphics.interfaces.IIndexBuffer
import org.openartifact.artifact.graphics.interfaces.IVertexBuffer
import org.openartifact.artifact.graphics.interfaces.IShader
import org.openartifact.artifact.graphics.platform.opengl.buffer.OpenGLIndexBuffer
import org.openartifact.artifact.graphics.platform.opengl.buffer.OpenGLVertexBuffer
import kotlin.reflect.KClass

class OpenGLRenderer : Renderer {

    override val api = RendererAPI.OpenGL

    override fun registerComponents() : Map<KClass<out IGraphicsComponent>, KClass<out IGraphicsComponent>> =
        mapOf(
            IVertexBuffer::class to OpenGLVertexBuffer::class,
            IIndexBuffer::class to OpenGLIndexBuffer::class,
            IShader::class to OpenGLShader::class
        )

}