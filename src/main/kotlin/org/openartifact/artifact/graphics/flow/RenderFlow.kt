package org.openartifact.artifact.graphics.flow

import org.openartifact.artifact.graphics.IGraphicsComponent

/**
 * Represents a rendering flow that manages the rendering of [IGraphicsComponent]s.
 *
 * @property committedElements A list of [IGraphicsComponent]s that have been committed for rendering.
 */
class RenderFlow {

    private val committedElements = mutableListOf<IGraphicsComponent>()

    /**
     * Commits a graphics component to the rendering flow.
     *
     * @param element The graphics component to commit.
     */
    fun commit(element : IGraphicsComponent) {
        element.commit()
        committedElements.add(element)
    }

    /**
     * Finishes the rendering flow, performing any necessary cleanup or finalization.
     * Shouldn't be called by the user; Will be called by the [renderFlow] function.
     */
    internal fun finish() {

    }
}

/**
 * Creates and executes a rendering flow using the provided block of code.
 *
 * @param block The block of code to execute within the rendering flow context.
 * @return The created RenderFlow instance after executing the block.
 */
fun renderFlow(block : RenderFlow.() -> Unit) : RenderFlow {
    val flow = RenderFlow()
    flow.block()
    flow.finish()
    return flow
}
