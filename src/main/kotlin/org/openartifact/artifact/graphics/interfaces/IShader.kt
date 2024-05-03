package org.openartifact.artifact.graphics.interfaces

import org.openartifact.artifact.graphics.IGraphicsComponent

interface IShader : IGraphicsComponent {

    fun create() : IShader
    fun bind()
    fun unbind()

}