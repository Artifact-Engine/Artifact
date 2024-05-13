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