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
import org.openartifact.artifact.graphics.IGraphicsComponent
import org.openartifact.artifact.graphics.Renderer
import org.openartifact.artifact.graphics.interfaces.*
import org.openartifact.artifact.graphics.platform.opengl.buffer.OpenGLIndexBuffer
import org.openartifact.artifact.graphics.platform.opengl.buffer.OpenGLVertexBuffer
import org.openartifact.artifact.graphics.platform.opengl.buffer.layout.OpenGLBufferLayout
import kotlin.reflect.KClass

class OpenGLRenderer : Renderer {

    override fun registerComponents() : Map<KClass<out IGraphicsComponent>, KClass<out IGraphicsComponent>> =
        mapOf(
            IVertexBuffer::class to OpenGLVertexBuffer::class,
            IIndexBuffer::class to OpenGLIndexBuffer::class,
            IVertexArray::class to OpenGLVertexArray::class,
            IShader::class to OpenGLShader::class,
            IBufferLayout::class to OpenGLBufferLayout::class,
            ITexture::class to OpenGLTexture::class
        )

    /**
     * Clears the all required open GL buffers.
     */
    override fun frame(handler : Renderer.() -> Unit) {
        glClearColor(0.1f, 0.1f, 0.1f, 1f)
        glClear(GL_COLOR_BUFFER_BIT or GL_DEPTH_BUFFER_BIT)

        super.frame(handler)
    }

}