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