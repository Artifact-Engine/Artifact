/*
 * Copyright Artifact-Engine (c) 2024.
 *
 * This program is free software: you can redistribute it and/or modify it under the terms of the GNU General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.
 */

package org.openartifact.artifact.core.event

import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlin.reflect.KClass

private val listeners = mutableMapOf<KClass<*>, MutableList<(Event) -> Unit>>()

@Suppress("unchecked_cast")
fun <T : Event> subscribe(eventType : KClass<T>, listener : (T) -> Unit) {
    listeners.getOrPut(eventType) { mutableListOf() }.add(listener as (Event) -> Unit)
}

@OptIn(DelicateCoroutinesApi::class)
fun deploy(event : Event) {
    val type = event::class
    listeners[type]?.forEach { listener ->
        GlobalScope.launch {
            listener(event)
        }
    }
}