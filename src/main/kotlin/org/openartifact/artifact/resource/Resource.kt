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