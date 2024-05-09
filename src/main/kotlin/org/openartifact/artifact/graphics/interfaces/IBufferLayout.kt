package org.openartifact.artifact.graphics.interfaces

import DataType
import org.openartifact.artifact.graphics.IGraphicsComponent

data class BufferElement(val name : String, val dataType : DataType, var offset : Int = 0)

interface IBufferLayout : IGraphicsComponent {

    var stride : Int
    val bufferElements : MutableList<BufferElement>

    /**
     * Creates a buffer layout based on the provided map of data types to names.
     *
     * @param map A map where keys are DataType instances and values are names for the buffer elements.
     * @return The created buffer layout.
     */
    fun create(map: Map<DataType, String>): IBufferLayout {
        require(map.isNotEmpty()) { "Map cannot be empty." }

        calculateOffsets(map)
        calculateStride()

        return this
    }

    /**
     * Calculates the offsets for each buffer element based on the provided map of data types to names.
     * Offsets are calculated sequentially based on the byte sizes of the data types.
     *
     * @param map A map where keys are DataType instances and values are names for the buffer elements.
     */
    fun calculateOffsets(map : Map<DataType, String>) {
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
    fun calculateStride() {
        stride = bufferElements.lastOrNull()?.let { it.offset + it.dataType.byteSize() } ?: 0
    }
}
