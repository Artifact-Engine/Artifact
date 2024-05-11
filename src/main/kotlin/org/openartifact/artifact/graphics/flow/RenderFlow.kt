package org.openartifact.artifact.graphics.flow

import org.openartifact.artifact.graphics.interfaces.IShader
import org.openartifact.artifact.graphics.interfaces.IVertexArray

@DslMarker
annotation class RenderFlowBuilder

class RenderFlow {

    private val committedElements = mutableListOf<Any>()

    private fun commit(element : Any) {
        committedElements.add(element)
    }

    fun commitShader(shader : IShader) {
        shader.bind()
        commit(shader)
    }

    fun commitVertexArray(vertexArray : IVertexArray) {
        vertexArray.bind()
        vertexArray.draw()
        commit(vertexArray)
    }

    fun finish() {

    }
}

fun renderFlow(block : RenderFlow.() -> Unit) : RenderFlow {
    val flow = RenderFlow()
    flow.block()
    flow.finish()
    return flow
}
