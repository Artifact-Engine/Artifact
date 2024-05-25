/*
 * Copyright Artifact-Engine (c) 2024.
 *
 * This program is free software: you can redistribute it and/or modify it under the terms of the GNU General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.
 */

package org.openartifact.artifact.graphics.platform.opengl

import org.lwjgl.opengl.GL46.*
import org.openartifact.artifact.graphics.interfaces.IIndexBuffer
import org.openartifact.artifact.graphics.interfaces.IVertexArray
import org.openartifact.artifact.graphics.interfaces.IVertexBuffer

class OpenGLVertexArray : IVertexArray {

    override val vertexBuffers : MutableList<IVertexBuffer> = mutableListOf()
    override lateinit var indexBuffer : IIndexBuffer

    private var id : Int = 0

    override fun create() : IVertexArray {
        id = glCreateVertexArrays()
        bind()

        return this
    }

    override fun addVertexBuffer(vertexBuffer : IVertexBuffer) {
        glBindVertexArray(id)
        vertexBuffer.bind()

        check(vertexBuffer.layout.bufferElements.isNotEmpty()) { "Layout contains no elements." }

        vertexBuffer.layout.apply {

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
        }

        vertexBuffers += vertexBuffer
    }

    override fun defineIndexBuffer(buffer : IIndexBuffer) {
        super.defineIndexBuffer(buffer)

        buffer.bind()
    }

    override fun bind() {
        glBindVertexArray(id)
    }

    override fun draw() {        glDrawElements(GL_TRIANGLES, indexBuffer.count, GL_UNSIGNED_INT, 0)

        glDrawElements(GL_TRIANGLES, indexBuffer.count, GL_UNSIGNED_INT, 0)
    }

    override fun unbind() {
        glBindVertexArray(0)
    }

    override fun push() {
        bind()
        draw()
    }
}