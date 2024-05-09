package org.openartifact.artifact.graphics.platform.opengl.buffer.layout

import org.lwjgl.opengl.GL46.*
import org.openartifact.artifact.graphics.interfaces.IBufferLayout

enum class DataType(val size: Int, val glType: Int, val byteSize: Int) {
    FloatX(1, GL_FLOAT, Float.SIZE_BYTES),
    Integer(1, GL_INT, Int.SIZE_BYTES),
    Boolean(1, GL_BOOL, Byte.SIZE_BYTES),
    Vec2(2, GL_FLOAT, 2 * FloatX.byteSize),
    Vec3(3, GL_FLOAT, 3 * FloatX.byteSize),
    Vec4(4, GL_FLOAT, 4 * FloatX.byteSize),
    Mat2(4, GL_FLOAT, 4 * FloatX.byteSize),
    Mat3(9, GL_FLOAT, 9 * FloatX.byteSize),
    Mat4(16, GL_FLOAT, 16 * FloatX.byteSize)
}

private data class BufferElement(val name: String, val dataType: DataType, var offset: Int = 0)

class OpenGLBufferLayout : IBufferLayout {

    private var stride: Int = 0
    private val bufferElements = mutableListOf<BufferElement>()

    override fun create(map: Map<DataType, String>): IBufferLayout {
        require(map.isNotEmpty()) { "Map cannot be empty." }

        var offset = 0
        map.forEach { (dataType, name) ->
            val bufferElement = BufferElement(name, dataType, offset)
            bufferElements.add(bufferElement)
            offset += dataType.byteSize // Update offset for the next element
        }

        stride = offset // Stride is the total byte size of a vertex

        bufferElements.forEachIndexed { index, bufferElement ->
            println("name: ${bufferElement.name} | offset: ${bufferElement.offset} | stride: $stride")
            glEnableVertexAttribArray(index)
            glVertexAttribPointer(
                index,
                bufferElement.dataType.size,
                bufferElement.dataType.glType,
                false,
                stride,
                bufferElement.offset.toLong()
            )
        }

        return this
    }
}
