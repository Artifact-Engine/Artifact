package org.openartifact.artifact.graphics.interfaces

import org.openartifact.artifact.graphics.IGraphicsComponent
import kotlin.reflect.KClass

interface IBufferLayout : IGraphicsComponent {

    fun create(map: Map<KClass<*>, Pair<String, Boolean>> = emptyMap()): IBufferLayout
}
