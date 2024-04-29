package org.openartifact.artifact.game.nodes

import org.openartifact.artifact.core.graphics.component.renderers.CubeRenderer
import org.openartifact.artifact.game.Component
import org.openartifact.artifact.game.Node

class CubeNode : Node() {

    override fun requiredComponents(): MutableList<Component> {
        return mutableListOf(
            CubeRenderer()
        )
    }


}