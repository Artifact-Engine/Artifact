package org.openartifact.artifact.graphics.interfaces

import org.openartifact.artifact.graphics.IRendererComponent

interface IIndexBuffer : IRendererComponent {

    fun create(indices : IntArray, size : Int) : IIndexBuffer

    fun bind()
    fun unbind()

    fun clear()

}