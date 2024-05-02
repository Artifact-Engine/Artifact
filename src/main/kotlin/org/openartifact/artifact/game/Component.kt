package org.openartifact.artifact.game

import org.lwjgl.glfw.GLFW
import org.openartifact.artifact.core.Application
import org.openartifact.artifact.input.KeyInputMap

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
        GLFW.glfwGetKey(Application.current().engine.window.handle, key) == GLFW.GLFW_PRESS

    fun onKey(key: Int, action: () -> Unit) =
        if (GLFW.glfwGetKey(Application.current().engine.window.handle, key) == GLFW.GLFW_PRESS) action() else {}

    fun keyMap(block: KeyInputMap.() -> Unit): KeyInputMap {
        return KeyInputMap().apply(block)
    }

    fun getMouseButtonDown(mouseButton : Int) : Boolean =
        GLFW.glfwGetMouseButton(Application.current().engine.window.handle, mouseButton) == GLFW.GLFW_PRESS

    fun onMouseButton(mouseButton: Int, action: () -> Unit) =
        if (GLFW.glfwGetMouseButton(Application.current().engine.window.handle, mouseButton) == GLFW.GLFW_PRESS) action() else {}

}
