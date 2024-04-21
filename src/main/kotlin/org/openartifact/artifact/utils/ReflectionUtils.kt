package org.openartifact.artifact.utils

import kotlin.reflect.KClass
import kotlin.reflect.full.primaryConstructor


/**
 * Searched for a class in a specific package
 */
inline fun <reified T : Any> getClass(className : String) : KClass<T> {
    val kClass = Class.forName(className).kotlin

    require(T::class.java.isAssignableFrom(kClass.java)) { "Class $className is not a subclass of ${T::class.java.simpleName}" }

    return kClass as KClass<T>
}

inline fun <reified T : Any> createInstance(kClass : KClass<*>, vararg args : Any?) : T? =
    kClass.primaryConstructor?.call(*args) as? T

inline fun <reified T : Any> createInstance(className : String, vararg args : Any?) : T? =
    createInstance<T>(getClass<T>(className), *args)
