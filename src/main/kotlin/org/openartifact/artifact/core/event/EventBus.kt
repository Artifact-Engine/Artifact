package org.openartifact.artifact.core.event

import kotlin.reflect.KClass

private val listeners = mutableMapOf<KClass<*>, MutableList<(Event) -> Unit>>()

@Suppress("unchecked_cast")
fun <T : Event> subscribe(eventType : KClass<T>, listener : (T) -> Unit) {
    listeners.getOrPut(eventType) { mutableListOf() }.add(listener as (Event) -> Unit)
}

fun deploy(event : Event) {
    val type = event::class
    listeners[type]?.forEach { it(event) }
}