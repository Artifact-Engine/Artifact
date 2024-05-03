package org.openartifact.artifact.graphics.interfaces

import org.openartifact.artifact.graphics.IGraphicsComponent

interface IIndexBuffer : IGraphicsComponent {

    var count : Int

    fun create(indices : IntArray) : IIndexBuffer

    fun bind()
    fun unbind()

    fun clear()

}