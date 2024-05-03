package org.openartifact.artifact.graphics.interfaces

import org.openartifact.artifact.graphics.IRendererComponent

interface IVertexBuffer : IRendererComponent {

    fun create(vertices : FloatArray) : IVertexBuffer

    fun bind()
    fun unbind()

    fun clear()

}

