package org.openartifact.artifact.input

import org.lwjgl.glfw.GLFW.*
import org.openartifact.artifact.core.Artifact

fun getKeyDown(key : Int) : Boolean =
    glfwGetKey(Artifact.instance.window.handle, key) == GLFW_PRESS

fun onKey(key: Int, action: () -> Unit) =
    if (glfwGetKey(Artifact.instance.window.handle, key) == GLFW_PRESS) action() else {}

fun createKeyInputMap(block: KeyInputMap.() -> Unit): KeyInputMap =
    KeyInputMap().apply(block)


fun getMouseButtonDown(mouseButton : Int) : Boolean =
    glfwGetMouseButton(Artifact.instance.window.handle, mouseButton) == GLFW_PRESS

fun onMouseButton(mouseButton: Int, action: () -> Unit) =
    if (glfwGetMouseButton(Artifact.instance.window.handle, mouseButton) == GLFW_PRESS) action() else {}