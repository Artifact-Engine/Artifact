/*
 * Copyright Artifact-Engine (c) 2024.
 *
 * This program is free software: you can redistribute it and/or modify it under the terms of the GNU General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.
 */

package org.openartifact.artifact.graphics.interfaces

import org.apache.commons.collections4.MultiValuedMap
import org.openartifact.artifact.extensions.forEach
import org.openartifact.artifact.extensions.isNotEmpty
import org.openartifact.artifact.graphics.DataType
import org.openartifact.artifact.graphics.IGraphicsComponent

data class BufferElement(val name : String, val dataType : DataType, var offset : Int = 0)

interface IBufferLayout : IGraphicsComponent {

    var stride : Int
    val bufferElements : MutableList<BufferElement>

    /**
     * Creates a buffer layout based on the provided map of data types to names.
     *
     * @param map A map where keys are org.openartifact.artifact.graphics.DataType instances and values are names for the buffer elements.
     * @return The created buffer layout.
     */
    fun create(map : MultiValuedMap<DataType, String>) : IBufferLayout {
        require(map.isNotEmpty()) { "Can't create buffer layout with empty map." }

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
    fun calculateOffsets(map : MultiValuedMap<DataType, String>) {
        var offset = 0
        map.forEach { dataType, name ->
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
