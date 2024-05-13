package org.openartifact.artifact.graphics

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
     * @return The committed graphics component.
     */
    fun <T : IGraphicsComponent> commit(element : T) : T {
        committedElements.add(element)
        return element
    }

    /**
     * Commits a graphics component to the rendering flow and executes a block of code on it.
     *
     * @param element The graphics component to commit.
     * @param block The block of code to execute on the committed graphics component.
     * @return The committed graphics component.
     */
    fun <T : IGraphicsComponent> commit(element : T, block : T.() -> Unit) : T {
        block(element)
        commit(element)
        return element
    }

    /**
     * Directly commits and pushes an element.
     * NOTE: Should be avoided if possible. Can lead to unexpected behavior.
     *
     * @param element The graphics component to commit and push.
     * @return The committed and pushed graphics component.
     */
    fun <T : IGraphicsComponent> directCommit(element : T) : T {
        element.push()
        return element
    }

    /**
     * Directly commits and pushes an element and executes a block of code on it.
     * NOTE: Should be avoided if possible. Can lead to unexpected behavior.
     *
     * @param element The graphics component to commit and push.
     * @param block The block of code to execute on the committed and pushed graphics component.
     * @return The committed and pushed graphics component.
     */
    fun <T : IGraphicsComponent> directCommit(element : T, block : T.() -> Unit) : T {
        directCommit(element)
        block(element)
        return element
    }

    /**
     * Pushes all committed elements to the render pipeline.
     */
    fun push() {
        require(committedElements.size <= MAX_COMMITS) { "Exceeded maximum number of commits. Please remove some committed elements or adjust MAX_COMMITS." }

        committedElements.forEach(IGraphicsComponent::push).also {
            committedElements.clear()
        }
    }

    /**
     * Commits multiple graphics components to the rendering flow.
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
 * @return The created [RenderFlow] instance after executing the block.
 */
fun renderFlow(block : RenderFlow.() -> Unit) : RenderFlow {
    val flow = RenderFlow()
    flow.block()
    return flow
}

// Maximum number of commits allowed
var MAX_COMMITS = 320000