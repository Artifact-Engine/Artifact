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

    // Extension function to simplify DSL usage
    operator fun IShader.invoke(block : IShader.() -> Unit) {
        block()
        commitShader(this)
    }

    // Extension function to simplify DSL usage
    operator fun IVertexArray.invoke(block : IVertexArray.() -> Unit) {
        block()
        commitVertexArray(this)
    }
}

fun renderFlow(block : RenderFlow.() -> Unit) : RenderFlow {
    val flow = RenderFlow()
    flow.block()
    flow.finish() // Call finish to finalize the rendering flow
    return flow
}
