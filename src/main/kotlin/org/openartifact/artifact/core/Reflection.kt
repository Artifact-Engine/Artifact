/*
 * Copyright Artifact-Engine (c) 2024.
 *
 * This program is free software: you can redistribute it and/or modify it under the terms of the GNU General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.
 */

package org.openartifact.artifact.core

import kotlin.reflect.KClass
import kotlin.reflect.full.primaryConstructor

/**
 * Searched for a class in a specific package
 */
@Suppress("UNCHECKED_CAST")
inline fun <reified T : Any> getClass(className : String) : KClass<T> {
    val kClass = Class.forName(className).kotlin

    require(T::class.java.isAssignableFrom(kClass.java)) { "Class $className is not a subclass of ${T::class.java.simpleName}" }

    return kClass as KClass<T>
}

inline fun <reified T : Any> createInstance(kClass : KClass<*>, vararg args : Any) : T? =
    kClass.primaryConstructor?.call(*args) as? T

inline fun <reified T : Any> createInstance(clazz : Class<*>, vararg args : Any) : T? =
    clazz.constructors.first().newInstance(*args) as T?

inline fun <reified T : Any> createInstance(className : String, vararg args : Any) : T? =
    createInstance<T>(getClass<T>(className), *args)

inline fun <reified T : Any> createInstance(className: String, classLoader: ClassLoader, vararg args: Any): T? {
    val kClass = getClass<T>(className, classLoader)
    return kClass.primaryConstructor?.call(*args) as? T
}

/**
 * Extended version of getClass to accept a ClassLoader as the second argument
 */
@Suppress("UNCHECKED_CAST")
inline fun <reified T : Any> getClass(className: String, classLoader: ClassLoader): KClass<T> {
    val kClass = Class.forName(className, true, classLoader).kotlin

    require(T::class.java.isAssignableFrom(kClass.java)) { "Class $className is not a subclass of ${T::class.java.simpleName}" }

    return kClass as KClass<T>
}