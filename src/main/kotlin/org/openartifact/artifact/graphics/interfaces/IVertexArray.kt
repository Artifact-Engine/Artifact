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

import org.openartifact.artifact.graphics.IGraphicsComponent

interface IVertexArray : IGraphicsComponent {

    var vertexBuffer : IVertexBuffer
    var indexBuffer : IIndexBuffer

    fun create(vertexBuffer : IVertexBuffer, indexBuffer : IIndexBuffer) : IVertexArray {
        this.vertexBuffer = vertexBuffer
        this.indexBuffer = indexBuffer
        return this
    }

    fun bind()
    fun draw()
    fun unbind()

}

