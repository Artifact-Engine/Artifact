package org.openartifact.artifact.game.nodes

import org.openartifact.artifact.core.GameContext
import org.openartifact.artifact.core.graphics.node.renderers.CubeRenderer
import org.openartifact.artifact.game.Node

class CubeNode : Node(CubeRenderer()) {

    override fun awake() {
        super.awake()

        println("Hello World!")
    }

}