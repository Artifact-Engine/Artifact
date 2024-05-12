package org.openartifact.artifact.graphics.platform.opengl.buffer

import org.lwjgl.opengl.GL46.*
import org.openartifact.artifact.graphics.interfaces.IIndexBuffer

class OpenGLIndexBuffer : IIndexBuffer {

    private var id : Int = 0
    override var count : Int = 0

    override fun create(indices : IntArray) : IIndexBuffer {
        count = indices.size

        id = glCreateBuffers()
        bind()

        glBufferData(GL_ELEMENT_ARRAY_BUFFER, indices, GL_STATIC_DRAW)

        return this
    }

    override fun bind() {
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, id)
    }

    override fun unbind() {
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, 0)
    }

    override fun clear() {
        glDeleteBuffers(id)
    }

    override fun push() {}
}