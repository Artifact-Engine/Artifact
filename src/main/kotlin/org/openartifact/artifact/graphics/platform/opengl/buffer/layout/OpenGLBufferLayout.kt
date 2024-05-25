/*
 * Copyright Artifact-Engine (c) 2024.
 *
 * This program is free software: you can redistribute it and/or modify it under the terms of the GNU General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.
 */

package org.openartifact.artifact.graphics.platform.opengl.buffer.layout

import org.apache.commons.collections4.MultiValuedMap
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

    override fun create(map : MultiValuedMap<DataType, String>) : IBufferLayout {
        super.create(map)
        println(map)
        return this
    }

    override fun push() {}

}
