
package org.openartifact.artifact.core.input

import org.lwjgl.glfw.GLFW
import org.openartifact.artifact.core.Context

class KeyInputMap {
    private val singleKeyMappings = mutableMapOf<Int, () -> Unit>()
    private val multiKeyMappings = mutableMapOf<MultiKey, () -> Unit>()

    infix fun Int.to(action: () -> Unit) {
        singleKeyMappings[this] = action
    }

    infix fun MultiKey.to(action: () -> Unit) {
        multiKeyMappings[this] = action
    }

    fun process() {
        singleKeyMappings.forEach { (key, action) ->
            if (GLFW.glfwGetKey(Context.current().engine.window.window, key) == GLFW.GLFW_PRESS) {
                action()
            }
        }

        multiKeyMappings.forEach { (multiKey, action) ->
            if (multiKey.keys.all { GLFW.glfwGetKey(Context.current().engine.window.window, it) == GLFW.GLFW_PRESS }) {
                action()
            }
        }
    }
}


data class MultiKey(val keys: Set<Int>) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false
        other as MultiKey
        return keys == other.keys
    }

    override fun hashCode(): Int {
        return keys.hashCode()
    }

}

infix fun Int.combineWith(key: Int): MultiKey =
    MultiKey(setOf(this, key))

infix fun MultiKey.combineWith(key: MultiKey): MultiKey =
    MultiKey(this.keys + key.keys.filterNot { it in this.keys })

infix fun MultiKey.combineWith(key : Int) : MultiKey =
    MultiKey(this.keys + setOf(key))