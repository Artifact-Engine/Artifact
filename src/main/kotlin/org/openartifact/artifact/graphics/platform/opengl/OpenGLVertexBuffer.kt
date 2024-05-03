package org.openartifact.artifact.graphics.platform.opengl

import org.lwjgl.opengl.GL46.*
import org.openartifact.artifact.graphics.interfaces.IVertexBuffer

class OpenGLVertexBuffer : IVertexBuffer {

    private var id : Int = 0

    override fun create(vertices : FloatArray) : IVertexBuffer {
        id = glGenBuffers()
        bind()

        glBufferData(GL_ARRAY_BUFFER, vertices, GL_STATIC_DRAW)

        return OpenGLVertexBuffer()
    }

    override fun bind() {
        glBindBuffer(GL_ARRAY_BUFFER, id)
    }

    override fun unbind() {
        glBindBuffer(GL_ARRAY_BUFFER, 0)
    }

    override fun clear() {
        glDeleteBuffers(id)
    }

}