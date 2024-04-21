package org.openartifact.artifact.game

interface NodeRenderer<T : Node> {

    fun initRender()
    fun render()
    fun cleanupRender()

}