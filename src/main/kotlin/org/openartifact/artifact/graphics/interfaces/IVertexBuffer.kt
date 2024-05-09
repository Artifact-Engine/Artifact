package org.openartifact.artifact.graphics.interfaces

import org.openartifact.artifact.graphics.IGraphicsComponent

interface IVertexBuffer : IGraphicsComponent {

    var layout : IBufferLayout

    fun create(vertices : FloatArray, layout : IBufferLayout) : IVertexBuffer {
        this.layout = layout
        return this
    }

    fun bind()
    fun unbind()

    fun clear()

}

