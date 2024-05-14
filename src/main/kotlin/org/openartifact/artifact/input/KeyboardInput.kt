/*
 * Copyright Artifact-Engine (c) 2024.
 *
 * This program is free software: you can redistribute it and/or modify it under the terms of the GNU General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.
 */

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