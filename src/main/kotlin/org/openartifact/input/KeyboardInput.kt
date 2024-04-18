package org.openartifact.input

import org.lwjgl.glfw.GLFW.*
import org.openartifact.core.Engine

/**
 * Returns true if the specified key is currently pressed
 * @see glfwGetKey
 */
fun isKeyPressed(key: Int): Boolean =
    glfwGetKey(Engine.gameWindow.window, key) == GLFW_PRESS