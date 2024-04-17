package org.openartifact.node

/**
 * Base class for any type of object that's in a scene
 */
open class Node {

    /**
     * List of components on this node
     * @see Component
     */
    val components = mutableListOf<Component>()

}

enum class NodeType {
    Dynamic,
    Fixed
}