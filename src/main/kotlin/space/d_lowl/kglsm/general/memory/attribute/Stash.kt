package space.d_lowl.kglsm.general.memory.attribute

/**
 * Stash Memory Attribute
 *
 * @param[T] a type of items to stash
 * @property[stash] a list of stashed items
 */
class Stash<T>: MemoryAttribute {
    var stash: MutableList<T> = mutableListOf()

    /**
     * Add a collection of items to stash
     *
     * @param[items] a collection of items to stash
     */
    fun addAllToStash(items: Collection<T>) {
        stash.addAll(items)
    }

    /**
     * Add a single item to stash
     *
     * @param[item] an item to stash
     */
    fun addToStash(item: T) {
        stash.add(item)
    }

    /**
     * Clear stash
     */
    fun clearStash() {
        stash.clear()
    }

    override fun toString(): String = "Stash (has ${stash.size} items)"
}