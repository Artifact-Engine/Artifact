package org.openartifact.artifact.utils

import java.io.File
import java.net.URL
import java.net.URLClassLoader
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

/**
 * Loads a jar from a filepath
 */
fun loadJar(jarFile : File) : ClassLoader {
    val jarUrl = jarFile.toURI().toURL()
    return URLClassLoader(arrayOf(jarUrl), ClassLoader.getSystemClassLoader())
}

/**
 * Instantiates a class from a loaded JAR file
 */
inline fun <reified T : Any> createInstanceFromJar(jarFile : File, className: String, vararg args: Any?): T? {
    val classLoader = loadJar(jarFile)
    val kClass = getClass<T>(className, classLoader)
    return createInstance(kClass, *args)
}

/**
 * Extended version of getClass to accept a ClassLoader as the second argument
 */
inline fun <reified T : Any> getClass(className: String, classLoader: ClassLoader): KClass<T> {
    val kClass = Class.forName(className, true, classLoader).kotlin

    require(T::class.java.isAssignableFrom(kClass.java)) { "Class $className is not a subclass of ${T::class.java.simpleName}" }

    return kClass as KClass<T>
}