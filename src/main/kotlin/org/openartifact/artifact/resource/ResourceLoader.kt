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

internal val cached = mutableListOf<Resource>()

fun resource(name : String, path : String = name) : Resource =
    Resource(name, path)

fun getResource(name : String) : Resource {
    cached.forEach {
        if (it.name == name && it.isCached) return it
    }

    return Resource(name, name)
}