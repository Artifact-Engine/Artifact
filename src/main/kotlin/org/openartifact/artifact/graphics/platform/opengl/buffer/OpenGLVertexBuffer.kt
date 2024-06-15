/*
 * Copyright Artifact-Engine (c) 2024.
 *
 * This program is free software: you can redistribute it and/or modify it under the terms of the GNU General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.
 */

package org.openartifact.artifact.graphics.platform.opengl.buffer

import org.lwjgl.opengl.GL46.*
import org.openartifact.artifact.graphics.interfaces.IBufferLayout
import org.openartifact.artifact.graphics.interfaces.IVertexBuffer

class OpenGLVertexBuffer : IVertexBuffer {

    private var id : Int = 0
    override lateinit var layout : IBufferLayout
    override var count : Int = 0

    override fun create(vertices : FloatArray, layout : IBufferLayout) : IVertexBuffer {
        super.create(vertices, layout)

        id = glCreateBuffers()
        bind()

        glBufferData(GL_ARRAY_BUFFER, vertices, GL_STATIC_DRAW)

        return this
    }

    override fun bind() {
        glBindBuffer(GL_ARRAY_BUFFER, id)
    }

    override fun unbind() {
        glBindBuffer(GL_ARRAY_BUFFER, 0)
    }

    override fun clear() {
        glDeleteBuffers(id)
    }

    override fun commit() {}

}