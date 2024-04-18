package org.openartifact.node

import org.openartifact.rendering.Renderer

/**
 * Base class for the implementation of rendering specific nodes.
 */
open class NodeRenderer<T: Node>: Renderer {

    override fun init() {}

    override fun render() {}

    override fun shutdown() {}

}