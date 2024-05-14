/*
 * Copyright Artifact-Engine (c) 2024.
 *
 * This program is free software: you can redistribute it and/or modify it under the terms of the GNU General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.
 */

package org.openartifact.artifact.resource

class Resource(val name : String, val path : String, internal var isCached : Boolean = false) {

    fun cache() {
        isCached = true
        cached.add(this)
    }

    fun asText() : String {
        return javaClass.getResourceAsStream("/$path")?.reader()?.readText() ?: throw IllegalStateException("Resource $path not found. ")
    }

}