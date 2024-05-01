package org.openartifact.artifact.game

import org.lwjgl.glfw.GLFW
import org.openartifact.artifact.core.GameContext
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

    fun getKeyDown(key : Int) : Boolean =
        GLFW.glfwGetKey(GameContext.current().engine.window.id, key) == GLFW.GLFW_PRESS

    fun onKey(key: Int, action: () -> Unit) =
        if (GLFW.glfwGetKey(GameContext.current().engine.window.id, key) == GLFW.GLFW_PRESS) action() else {}

    fun keyMap(block: KeyInputMap.() -> Unit): KeyInputMap {
        return KeyInputMap().apply(block)
    }

    fun getMouseButtonDown(mouseButton : Int) : Boolean =
        GLFW.glfwGetMouseButton(GameContext.current().engine.window.id, mouseButton) == GLFW.GLFW_PRESS

    fun onMouseButton(mouseButton: Int, action: () -> Unit) =
        if (GLFW.glfwGetMouseButton(GameContext.current().engine.window.id, mouseButton) == GLFW.GLFW_PRESS) action() else {}

}
