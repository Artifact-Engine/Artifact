package org.openartifact.artifact.core.event

import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.runBlocking
import kotlin.reflect.KClass

/**
 * listeners a map of event types to a list of event listeners
 */
private val listeners : MutableMap<KClass<out Any>, MutableList<EventListener>> = HashMap()

/**
 * Registers an event listener for a specific event type
 *
 * @param eventClass the class of the event to listen for
 * @param listener the event listener to register
 */
fun register(eventClass : KClass<*>, listener : EventListener) {
    val eventListeners = listeners.getOrPut(eventClass) { ArrayList() }
    eventListeners.add(listener)
}

/**
 * Notifies all registered event listeners of a new event in parallel
 *
 * @param event the event to notify listeners of
 */
fun notify(event : Event) {
    runBlocking {
        listeners[event::class]?.map { listener ->
            async {
                listener.handle(event)
            }
        }?.awaitAll()
    }
}

/**
 * Registers an event listener for a specific event
 *
 * @param T the type of event to listen for
 * @param callback the function to call when an event of type [T] is received
 */
inline fun <reified T : Any> handler(crossinline callback : suspend (T) -> Unit, priority : Int = 0) {
    val listener = object : EventListener {
        override suspend fun handle(event : Event) {
            if (event is T)
                callback(event)
        }
    }
    register(T::class, listener)
}