package org.openartifact.artifact.graphics.interfaces

import org.openartifact.artifact.graphics.IGraphicsComponent
import org.openartifact.artifact.graphics.platform.opengl.buffer.layout.DataType

interface IBufferLayout : IGraphicsComponent {

    fun create(map: Map<DataType, String> = emptyMap()): IBufferLayout
}
