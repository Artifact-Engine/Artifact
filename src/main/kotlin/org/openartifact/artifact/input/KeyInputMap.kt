/*
 * Copyright Artifact-Engine (c) 2024.
 *
 * This program is free software: you can redistribute it and/or modify it under the terms of the GNU General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.
 */

package org.openartifact.artifact.input

class KeyInputMap {

    private val singleKeyMappings = mutableMapOf<Int, () -> Unit>()
    private val multiKeyMappings = mutableMapOf<MultiKey, () -> Unit>()

    infix fun Int.to(action : () -> Unit) {
        singleKeyMappings[this] = action
    }

    infix fun MultiKey.to(action : () -> Unit) {
        multiKeyMappings[this] = action
    }

    fun process() {
        singleKeyMappings.forEach { (key, action) ->
            if (getKeyDown(key)) action()
        }

        multiKeyMappings.forEach { (multiKey, action) ->
            if (multiKey.keys.all { getKeyDown(it) })
                action()
        }
    }
}


data class MultiKey(val keys : Set<Int>) {
    override fun equals(other : Any?) : Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false
        other as MultiKey
        return keys == other.keys
    }

    override fun hashCode() : Int {
        return keys.hashCode()
    }

}

infix fun Int.with(key : Int) : MultiKey =
    MultiKey(setOf(this, key))

infix fun MultiKey.with(key : Int) : MultiKey =
    MultiKey(this.keys + setOf(key))