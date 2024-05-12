package org.openartifact.artifact.graphics.interfaces

import org.openartifact.artifact.graphics.IGraphicsComponent

interface IVertexArray : IGraphicsComponent {

    val vertexBuffers: MutableList<IVertexBuffer>
    var indexBuffer : IIndexBuffer

    fun create() : IVertexArray

    fun addVertexBuffer(vertexBuffer: IVertexBuffer)
    fun defineIndexBuffer(buffer: IIndexBuffer) {
        this.indexBuffer = buffer
    }

    fun bind()
    fun draw()
    fun unbind()

}

