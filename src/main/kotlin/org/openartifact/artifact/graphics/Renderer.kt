package org.openartifact.artifact.graphics

import org.openartifact.artifact.core.createInstance
import kotlin.reflect.KClass


interface Renderer {

    /**
     * Accessor for the Renderer API.
     *
     * @return The Renderer API instance that provides the interface to the underlying rendering system.
     */
    val api : RendererAPI

    /**
     * Registers components that implement the [IRendererComponent] interface.
     * This method is expected to return a map where the keys are the classes of the components
     * and the values are the classes of the implementations. This allows for dynamic selection
     * and instantiation of components at runtime.
     *
     * @return A map of component classes to their implementation classes.
     */
    fun registerComponents() : Map<KClass<out IRendererComponent>, KClass<out IRendererComponent>>

}


/**
 * Selects and creates an instance of a specific [IRendererComponent] implementation based on its type.
 *
 * This function uses reified type parameters to ensure type safety and runtime type information.
 * It searches for the component in the registered components map by matching the class of the type parameter `T`.
 * If the component is found, it is instantiated and returned.
 *
 * @param T The type of the [IRendererComponent] to be created. Must be a subtype of [IRendererComponent].
 * @return An instance of the specified [IRendererComponent] type.
 * @throws IllegalArgumentException If no component of the specified type is registered.
 *
 * @see IRendererComponent
 * @see Renderer.registerComponents
 * @see createInstance
 */
inline fun <reified T : IRendererComponent> Renderer.choose() : T {
    return createInstance<IRendererComponent>(registerComponents()
        .entries
        .firstOrNull { it.key == T::class }!!
        .value) as T
}
