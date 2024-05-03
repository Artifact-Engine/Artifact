package org.openartifact.artifact.graphics.interfaces

import org.openartifact.artifact.graphics.IGraphicsComponent

interface IVertexBuffer : IGraphicsComponent {

    fun create(vertices : FloatArray) : IVertexBuffer

    fun bind()
    fun unbind()

    fun clear()

}

