/*
 * Copyright Artifact-Engine (c) 2024.
 *
 * This program is free software: you can redistribute it and/or modify it under the terms of the GNU General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.
 */

package org.openartifact.artifact.core.application

import org.lwjgl.glfw.GLFW
import org.openartifact.artifact.core.Artifact
import org.openartifact.artifact.graphics.Camera
import org.openartifact.artifact.graphics.RenderAPI
import org.openartifact.artifact.graphics.Renderer
import org.openartifact.artifact.graphics.cameras.PerspectiveCamera
import org.openartifact.artifact.graphics.platform.opengl.OpenGLRenderer
import org.openartifact.artifact.graphics.window.WindowConfig
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.util.Properties

open class Application(val api : RenderAPI, val windowConfig: WindowConfig) {

    lateinit var config : ApplicationConfig
    open val logger : Logger = LoggerFactory.getLogger(javaClass)

    lateinit var renderer : Renderer
    lateinit var camera : PerspectiveCamera

    fun createRenderer() : Renderer {
        return when (api) {
            RenderAPI.OpenGL -> OpenGLRenderer()
            RenderAPI.Vulkan -> TODO()
        }
    }

    fun requestShutdown() {
        GLFW.glfwSetWindowShouldClose(Artifact.instance.window.handle, true)
        renderer.shutdown()
    }

    open fun init() {}
    open fun update(deltaTime : Double) {}
    open fun shutdown() {}

}