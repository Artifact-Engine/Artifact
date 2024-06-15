package org.openartifact.artifact.graphics


/**
 * Commits a graphics component to the rendering flow.
 *
 * @param element The graphics component to commit.
 * @return The committed graphics component.
 */
fun <T : IGraphicsComponent> commit(element: T): T {
    element.commit()
    return element
}

/**
 * Commits a graphics component to the rendering flow and executes a block of code on it.
 *
 * @param element The graphics component to commit.
 * @param block The block of code to execute on the committed graphics component.
 * @return The committed graphics component.
 */
fun <T : IGraphicsComponent> commit(element: T, block: T.() -> Unit): T {
    commit(element)
    block(element)
    return element
}

