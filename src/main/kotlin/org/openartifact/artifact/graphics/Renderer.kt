/*
 * Copyright Artifact-Engine (c) 2024.
 *
 * This program is free software: you can redistribute it and/or modify it under the terms of the GNU General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.
 */

package org.openartifact.artifact.graphics

import org.openartifact.artifact.core.createInstance
import kotlin.reflect.KClass


interface Renderer {

    /**
     * Registers components that implement the [IGraphicsComponent] interface.
     * This method is expected to return a map where the keys are the classes of the components
     * and the values are the classes of the implementations. This allows for dynamic selection
     * and instantiation of components at runtime.
     *
     * @return A map of component classes to their implementation classes.
     */
    fun registerComponents() : Map<KClass<out IGraphicsComponent>, KClass<out IGraphicsComponent>>

    /**
     * Creates and executes a rendering flow using the provided block of code.
     *
     * @param block The block of code to execute within the rendering flow context.
     * @return The created [RenderFlow] instance after executing the block.
     */
    fun renderFlow(block : RenderFlow.() -> Unit) : RenderFlow {
        val flow = RenderFlow()
        flow.block()
        return flow
    }

    /**
     * Renders a frame
     * @param handler The block of code to execute within the frame.
     */
    fun frame(handler : Renderer.() -> Unit) {
        handler()
    }

}

/**
 * Selects and creates an instance of a specific [IGraphicsComponent] implementation based on its type.
 *
 * The function searches for the component in the registered components map by matching the class of the type parameter [T].
 * If the component is found, it is instantiated and returned.
 *
 * @param T The type of the [IGraphicsComponent] to be created. Must be a subtype of [IGraphicsComponent].
 * @param args Additional arguments required to instantiate the [IGraphicsComponent].
 * @return An instance of the specified [IGraphicsComponent] type.
 * @throws IllegalArgumentException If no component of the specified type is registered.
 *
 * @see IGraphicsComponent
 * @see Renderer.registerComponents
 * @see createInstance
 */
inline fun <reified T : IGraphicsComponent> Renderer.choose(vararg args : Any) : T {
    return createInstance<IGraphicsComponent>(registerComponents()
        .entries
        .firstOrNull { it.key == T::class }!!
        .value, *args) as T
}
