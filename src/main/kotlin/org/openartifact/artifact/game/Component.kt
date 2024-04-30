package org.openartifact.artifact.game

import org.lwjgl.glfw.GLFW
import org.openartifact.artifact.core.Context
import org.openartifact.artifact.core.input.KeyInputMap

open class Component {

    lateinit var parent: Node

    inline fun <reified T : Component> getComponent(): T? =
        parent.components.find { it is T } as? T

    @Suppress("unused")
    val type: String = javaClass.simpleName

    open fun awake() {}
    open fun render(deltaTime: Double) {}

    /**
     * Physics update
     */
    open fun update(physicsDeltaTime: Double) {}
    open fun rest() {}

    fun onKey(key: Int, action: () -> Unit) =
        if (GLFW.glfwGetKey(Context.current().engine.window.window, key) == GLFW.GLFW_PRESS) action() else {}

    fun keyMap(block: KeyInputMap.() -> Unit): KeyInputMap {
        return KeyInputMap().apply(block)
    }

}
