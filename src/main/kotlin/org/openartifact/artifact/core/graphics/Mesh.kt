package org.openartifact.artifact.core.graphics

import org.lwjgl.opengl.GL15
import org.lwjgl.opengl.GL30.*

class Mesh(val vertexBufferData: FloatArray, val uvBufferData: FloatArray, val indexBufferData: IntArray) {

    private var vaId: Int = 0
    var vertexBuffer: Int = 0
    var uvBuffer: Int = 0
    var indexBuffer: Int = 0

    init {
        vaId = glGenVertexArrays()
        glBindVertexArray(vaId)

        vertexBuffer = glGenBuffers()
        glBindBuffer(GL_ARRAY_BUFFER, vertexBuffer)
        glBufferData(GL_ARRAY_BUFFER, vertexBufferData, GL_STATIC_DRAW)

        uvBuffer = glGenBuffers()
        glBindBuffer(GL_ARRAY_BUFFER, uvBuffer)
        GL15.glBufferData(GL_ARRAY_BUFFER, uvBufferData, GL_STATIC_DRAW)

        indexBuffer = glGenBuffers()
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, indexBuffer)
        GL15.glBufferData(GL_ELEMENT_ARRAY_BUFFER, indexBufferData, GL_STATIC_DRAW)
    }

    private fun bind() {
        glBindVertexArray(vaId)
    }

    fun modifyBufferData(buffer: Int, bufferData: FloatArray) {
        glBindBuffer(GL_ARRAY_BUFFER, buffer)
        glBufferData(GL_ARRAY_BUFFER, bufferData, GL_STATIC_DRAW)
    }

    fun render() {
        bind()

        glEnableVertexAttribArray(0)
        glEnableVertexAttribArray(1)
        glBindBuffer(GL_ARRAY_BUFFER, vertexBuffer)
        glVertexAttribPointer(0, 3, GL_FLOAT, false, 0, 0)

        glBindBuffer(GL_ARRAY_BUFFER, uvBuffer)
        glVertexAttribPointer(1, 2, GL_FLOAT, false, 0, 0)

        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, indexBuffer)
        glDrawElements(GL_TRIANGLES, indexBufferData.size, GL_UNSIGNED_INT, 0)

        glDisableVertexAttribArray(0)
        glDisableVertexAttribArray(1)
    }

    fun cleanup() {
        glDisableVertexAttribArray(0)
        glDisableVertexAttribArray(1)

        glBindBuffer(GL_ARRAY_BUFFER, 0)
        glDeleteBuffers(vertexBuffer)
        glDeleteBuffers(uvBuffer)
        glDeleteBuffers(indexBuffer)

        glBindVertexArray(0)
        glDeleteVertexArrays(vaId)
    }
}
