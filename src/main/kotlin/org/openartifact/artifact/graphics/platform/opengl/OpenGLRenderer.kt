package org.openartifact.artifact.graphics.platform.opengl

import org.lwjgl.opengl.GL46.*
import org.openartifact.artifact.graphics.IGraphicsComponent
import org.openartifact.artifact.graphics.Renderer
import org.openartifact.artifact.graphics.interfaces.IBufferLayout
import org.openartifact.artifact.graphics.interfaces.IIndexBuffer
import org.openartifact.artifact.graphics.interfaces.IVertexBuffer
import org.openartifact.artifact.graphics.interfaces.IShader
import org.openartifact.artifact.graphics.platform.opengl.buffer.OpenGLIndexBuffer
import org.openartifact.artifact.graphics.platform.opengl.buffer.OpenGLVertexBuffer
import org.openartifact.artifact.graphics.platform.opengl.buffer.layout.OpenGLBufferLayout
import kotlin.reflect.KClass

class OpenGLRenderer : Renderer {

    override fun registerComponents() : Map<KClass<out IGraphicsComponent>, KClass<out IGraphicsComponent>> =
        mapOf(
            IVertexBuffer::class to OpenGLVertexBuffer::class,
            IIndexBuffer::class to OpenGLIndexBuffer::class,
            IShader::class to OpenGLShader::class,
            IBufferLayout::class to OpenGLBufferLayout::class
        )

    /**
     * Clears the all required open GL buffers.
     */
    fun clearScreenBuffers() {
        glClearColor(0.1f, 0.1f, 0.1f, 1f)
        glClear(GL_COLOR_BUFFER_BIT or GL_DEPTH_BUFFER_BIT)
    }

}