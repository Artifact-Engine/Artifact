/*
 * Copyright Artifact-Engine (c) 2024.
 *
 * This program is free software: you can redistribute it and/or modify it under the terms of the GNU General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.
 */

package org.openartifact.artifact.extensions

import org.apache.commons.collections4.MultiValuedMap
import org.apache.commons.collections4.multimap.ArrayListValuedHashMap

fun <K, V> multiValuedMapOf(vararg pairs : Pair<K, V>) : MultiValuedMap<K, V> =
    ArrayListValuedHashMap<K, V>()
        .apply { pairs.forEach { put(it.first, it.second) } }

fun <K, V> MultiValuedMap<K, V>.isNotEmpty() : Boolean =
    !isEmpty


inline fun <K, V> MultiValuedMap<K, V>.forEach(action : (K, V) -> Unit) {
    for (entry in entries()) {
        action(entry.key, entry.value)
    }
}

inline fun <K, V> MultiValuedMap<K, V>.forEach(action : (Pair<K, V>) -> Unit) {
    for (entry in entries()) {
        action(Pair(entry.key, entry.value))
    }
}

