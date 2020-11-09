package com.sihvi.glsm.memory.attribute

class Stash<T>: MemoryAttribute {
    var stash: MutableList<T> = mutableListOf()

    fun addToStash(items: Collection<T>) {
        stash.addAll(items)
    }

    fun clearStash() {
        stash.clear()
    }

    override fun toString(): String = "Stash (has ${stash.size} items)"
}