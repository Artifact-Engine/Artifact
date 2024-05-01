package org.openartifact.artifact.core.graphics.mesh

import glm_.vec3.Vec3
import org.lwjgl.opengl.GL30.*
import org.openartifact.artifact.core.graphics.Texture

class Mesh(vertexData : FloatArray, texCoordsData : FloatArray, normalsData : FloatArray, val indexData : IntArray) {

    var texture : Texture? = null
    var color : Vec3 = Vec3(1)

    private val vaId : Int = glGenVertexArrays()
    private val vertexBuffer : Int
    private val texCoordBuffer : Int
    private val normalsBuffer : Int
    private val indexBuffer : Int

    init {
        glBindVertexArray(vaId)

        vertexBuffer = glGenBuffers()
        glBindBuffer(GL_ARRAY_BUFFER, vertexBuffer)
        glBufferData(GL_ARRAY_BUFFER, vertexData, GL_STATIC_DRAW)

        texCoordBuffer = glGenBuffers()
        glBindBuffer(GL_ARRAY_BUFFER, texCoordBuffer)
        glBufferData(GL_ARRAY_BUFFER, texCoordsData, GL_STATIC_DRAW)

        normalsBuffer = glGenBuffers()
        glBindBuffer(GL_ARRAY_BUFFER, normalsBuffer)
        glBufferData(GL_ARRAY_BUFFER, normalsData, GL_STATIC_DRAW)

        indexBuffer = glGenBuffers()
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, indexBuffer)
        glBufferData(GL_ELEMENT_ARRAY_BUFFER, indexData, GL_STATIC_DRAW)
    }

    private fun bind() {
        glBindVertexArray(vaId)
    }

    fun modifyBufferData(buffer : Int, bufferData : FloatArray) {
        glBindBuffer(GL_ARRAY_BUFFER, buffer)
        glBufferData(GL_ARRAY_BUFFER, bufferData, GL_STATIC_DRAW)
    }

    private fun getVertexCount() : Int =
        indexData.size

    fun render() {
        bind()

        glEnableVertexAttribArray(0)
        glEnableVertexAttribArray(1)
        glEnableVertexAttribArray(2)

        glBindBuffer(GL_ARRAY_BUFFER, vertexBuffer)
        glVertexAttribPointer(0, 3, GL_FLOAT, false, 0, 0)

        glBindBuffer(GL_ARRAY_BUFFER, texCoordBuffer)
        glVertexAttribPointer(1, 2, GL_FLOAT, false, 0, 0)

        glBindBuffer(GL_ARRAY_BUFFER, normalsBuffer)
        glDrawElements(GL_TRIANGLES, getVertexCount(), GL_UNSIGNED_INT, 0)

        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, indexBuffer)
        glDrawElements(GL_TRIANGLES, getVertexCount(), GL_UNSIGNED_INT, 0)

        glDisableVertexAttribArray(0)
        glDisableVertexAttribArray(1)
        glDisableVertexAttribArray(2)
        glBindVertexArray(0)

        texture?.render()
    }

    fun cleanup() {
        glDisableVertexAttribArray(0)
        glDisableVertexAttribArray(1)

        glBindBuffer(GL_ARRAY_BUFFER, 0)
        glDeleteBuffers(vertexBuffer)
        glDeleteBuffers(texCoordBuffer)
        glDeleteBuffers(indexBuffer)

        glBindVertexArray(0)
        glDeleteVertexArrays(vaId)
    }
}
