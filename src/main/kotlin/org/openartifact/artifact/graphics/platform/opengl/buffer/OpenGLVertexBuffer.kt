package org.openartifact.artifact.graphics.platform.opengl.buffer

import org.lwjgl.opengl.GL46.*
import org.openartifact.artifact.graphics.interfaces.IBufferLayout
import org.openartifact.artifact.graphics.interfaces.IVertexBuffer

class OpenGLVertexBuffer : IVertexBuffer {

    private var id : Int = 0
    override lateinit var layout : IBufferLayout

    override fun create(vertices : FloatArray, layout : IBufferLayout) : IVertexBuffer {
        super.create(vertices, layout)

        id = glCreateBuffers()
        bind()

        glBufferData(GL_ARRAY_BUFFER, vertices, GL_STATIC_DRAW)

        return this
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

    override fun push() {}

}