package org.openartifact.artifact.graphics.platform.opengl.buffer.layout

import org.lwjgl.opengl.GL46.*
import org.openartifact.artifact.graphics.interfaces.IBufferLayout

/**
 * Represents the data types used in OpenGL buffer layouts.
 * Each data type is associated with a size, a corresponding OpenGL type, and a byte size.
 */
enum class DataType(val size : Int, val glType : Int) {

    /**
     * A single float value.
     */
    FloatX(1, GL_FLOAT),

    /**
     * An integer value.
     */
    Integer(1, GL_INT),

    /**
     * A boolean value.
     */
    Boolean(1, GL_BOOL),

    /**
     * A vector of two floats.
     */
    Vec2(2, GL_FLOAT),

    /**
     * A vector of three floats.
     */
    Vec3(3, GL_FLOAT),

    /**
     * A vector of four floats.
     */
    Vec4(4, GL_FLOAT),

    /**
     * A 2x2 matrix of floats.
     */
    Mat2(2 * 2, GL_FLOAT),

    /**
     * A 3x3 matrix of floats.
     */
    Mat3(3 * 3, GL_FLOAT),

    /**
     * A 4x4 matrix of floats.
     */
    Mat4(4 * 4, GL_FLOAT);

    /**
     * Calculates the byte size of a DataType instance dynamically.
     *
     * @return The byte size of the DataType instance.
     */
    fun byteSize() : Int =
        size * when (glType) {
            GL_INT -> Int.SIZE_BYTES
            GL_FLOAT -> Float.SIZE_BYTES
            GL_DOUBLE -> Double.SIZE_BYTES
            GL_BOOL -> Byte.SIZE_BYTES
            else -> throw IllegalArgumentException("Unsupported GL type: $glType")
        }
}

private data class BufferElement(val name : String, val dataType : DataType, var offset : Int = 0)

/**
 * Represents a buffer layout for OpenGL rendering.
 * This class manages the creation of buffer layouts based on a map of data types to names.
 */
class OpenGLBufferLayout : IBufferLayout {

    private var stride : Int = 0
    private val bufferElements = mutableListOf<BufferElement>()

    /**
     * Creates a buffer layout based on the provided map of data types to names.
     *
     * @param map A map where keys are DataType instances and values are names for the buffer elements.
     * @return The created buffer layout.
     */
    override fun create(map : Map<DataType, String>) : IBufferLayout {
        require(map.isNotEmpty()) { "Map cannot be empty." }

        calculateOffsets(map)
        calculateStride()

        bufferElements.forEachIndexed { index, bufferElement ->
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

    /**
     * Calculates the offsets for each buffer element based on the provided map of data types to names.
     * Offsets are calculated sequentially based on the byte sizes of the data types.
     *
     * @param map A map where keys are DataType instances and values are names for the buffer elements.
     */
    private fun calculateOffsets(map : Map<DataType, String>) {
        var offset = 0
        map.forEach { (dataType, name) ->
            val bufferElement = BufferElement(name, dataType, offset)
            bufferElements.add(bufferElement)
            offset += dataType.byteSize()
        }
    }

    /**
     * Calculates the stride of the buffer layout.
     * Stride represents the byte offset between consecutive elements within the buffer.
     * It is calculated based on the offset of the last buffer element and its byte size.
     * If the buffer layout is empty, stride is set to 0. (Which shouldn't normally happen)
     */
    private fun calculateStride() {
        stride = bufferElements.lastOrNull()?.let { it.offset + it.dataType.byteSize() } ?: 0
    }

}
