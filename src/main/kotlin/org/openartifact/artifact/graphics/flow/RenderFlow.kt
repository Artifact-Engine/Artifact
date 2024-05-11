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
    fun commit(element : IGraphicsComponent) =
        committedElements.add(element)


    /**
     * Pushes all commits to the render pipeline
     */
    fun push() {
        require(committedElements.size <= MAX_COMMITS) { "Exceeded maximum number of commits. Please remove some committed elements or adjust MAX_COMMITS." }

        committedElements.forEach(IGraphicsComponent::commit).also {
            committedElements.clear()
        }
    }

    /**
     * Commits all [elements] to the [committedElements]
     *
     * @param elements The graphics components to commit.
     */
    fun commit(vararg elements : IGraphicsComponent) =
        elements.forEach (::commit)
}


/**
 * Creates and executes a rendering flow using the provided block of code.
 *
 * @param block The block of code to execute within the rendering flow context.
 * @return The created org.openartifact.artifact.graphics.flow.RenderFlow instance after executing the block.
 */
fun renderFlow(block : RenderFlow.() -> Unit) : RenderFlow {
    val flow = RenderFlow()
    flow.block()
    return flow
}

var MAX_COMMITS = 320000