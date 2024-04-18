package org.openartifact.node

import org.openartifact.scripting.Identifier

/**
 * Base class for any type of object that's in a scene
 */
open class Node {

    var identifier: String? = ""

    /**
     * List of components on this node
     * @see Component
     */
    val components = mutableListOf<Component>()

    @Transient open var renderer: NodeRenderer<*>? = null

    init {
        identifier = javaClass.getAnnotation(Identifier::class.java)?.id
    }

}

enum class NodeType {
    Dynamic,
    Fixed
}