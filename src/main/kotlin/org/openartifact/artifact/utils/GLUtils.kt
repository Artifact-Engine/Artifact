package org.openartifact.artifact.utils

import org.lwjgl.opengl.GL30.*
import org.lwjgl.system.MemoryStack

fun createBuffer(target: Int, data: FloatArray, usage: Int): Int {
    MemoryStack.stackPush().use { stack ->
        val buffer = stack.callocFloat(data.size)
        buffer.put(data).flip()
        val vboId = glGenBuffers()
        glBindBuffer(target, vboId)
        glBufferData(target, buffer, usage)
        return vboId
    }
}

fun createBuffer(target: Int, data: IntArray, usage: Int): Int {
    MemoryStack.stackPush().use { stack ->
        val buffer = stack.callocInt(data.size)
        buffer.put(data).flip()
        val vboId = glGenBuffers()
        glBindBuffer(target, vboId)
        glBufferData(target, buffer, usage)
        return vboId
    }
}

fun applyDepthTest() {
    glEnable(GL_DEPTH_TEST)
    glDepthFunc(GL_LESS)
}