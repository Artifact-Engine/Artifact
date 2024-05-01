package org.openartifact.artifact.core.graphics

import org.lwjgl.opengl.*
import org.lwjgl.opengl.GL30.*
import org.lwjgl.system.MemoryStack

class Mesh(positions : FloatArray, colors : FloatArray, indices : IntArray) {


    var numVertices : Int = 0
    var vaoId : Int = 0
    private val vboIdList = mutableListOf<Int>()

    init {
        MemoryStack.stackPush().use { stack ->
            numVertices = indices.size

            vaoId = glGenVertexArrays()
            glBindVertexArray(vaoId)

            createVBO(stack, positions, 0, 3, GL_FLOAT, GL_ARRAY_BUFFER)
            createVBO(stack, colors, 1, 3, GL_FLOAT, GL_ARRAY_BUFFER)
            createIndexVBO(stack, indices)

            glBindBuffer(GL_ARRAY_BUFFER, 0)
            glBindVertexArray(0)
        }
    }

    private fun createVBO(
        stack : MemoryStack,
        data : FloatArray,
        attribute : Int,
        size : Int,
        type : Int,
        bufferType : Int,
    ) {
        val vboId = glGenBuffers()
        vboIdList.add(vboId)
        val buffer = stack.callocFloat(data.size)
        buffer.put(0, data)
        glBindBuffer(bufferType, vboId)
        glBufferData(bufferType, buffer, GL_STATIC_DRAW)
        glEnableVertexAttribArray(attribute)
        glVertexAttribPointer(attribute, size, type, false, 0, 0)
    }

    private fun createIndexVBO(stack : MemoryStack, indices : IntArray) {
        val vboId = glGenBuffers()
        vboIdList.add(vboId)
        val indicesBuffer = stack.callocInt(indices.size)
        indicesBuffer.put(0, indices)
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, vboId)
        glBufferData(GL_ELEMENT_ARRAY_BUFFER, indicesBuffer, GL_STATIC_DRAW)
    }

    fun render() {
        glBindVertexArray(vaoId)
        glDrawElements(GL_TRIANGLES, numVertices, GL_UNSIGNED_INT, 0)
        glBindVertexArray(0)
    }

    fun cleanup() {
        vboIdList.forEach(GL30::glDeleteBuffers)
        glDeleteVertexArrays(vaoId)
    }
}
