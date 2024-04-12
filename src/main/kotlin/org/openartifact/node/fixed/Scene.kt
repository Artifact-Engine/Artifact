package org.openartifact.node.fixed

import org.openartifact.node.Node

/**
 * Holds multiple nodes and manages them.
 * Only one scene can be active at a time!
 */
data class Scene(val identifier: String, val nodes: MutableList<Node>)