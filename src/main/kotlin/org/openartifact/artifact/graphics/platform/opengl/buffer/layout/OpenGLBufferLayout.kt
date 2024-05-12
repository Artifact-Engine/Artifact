package org.openartifact.artifact.graphics.platform.opengl.buffer.layout

import org.openartifact.artifact.graphics.DataType
import org.openartifact.artifact.graphics.interfaces.BufferElement
import org.openartifact.artifact.graphics.interfaces.IBufferLayout

/**
 * Represents a buffer layout for OpenGL rendering.
 * This class manages the creation of buffer layouts based on a map of data types to names.
 */
class OpenGLBufferLayout : IBufferLayout {

    override var stride : Int = 0
    override val bufferElements : MutableList<BufferElement> = mutableListOf()

    override fun create(map : Map<DataType, String>) : IBufferLayout {
        super.create(map)
        return this
    }

    override fun push() {}

}
