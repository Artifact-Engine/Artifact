package org.openartifact.artifact.core.graphics.node

import org.openartifact.artifact.game.Node


abstract class NodeRenderer<T : Node> {

    abstract fun initRender()
    abstract fun render()
    abstract fun cleanupRender()

}