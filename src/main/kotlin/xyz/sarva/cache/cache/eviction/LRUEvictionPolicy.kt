package xyz.sarva.cache.cache.eviction

class LRUEvictionPolicy<KEY>: EvictionPolicy<KEY> {

    private val accessOrder = LinkedHashSet<KEY>()
    override fun onAccess(key: KEY) {
        accessOrder.remove(key)
        accessOrder.add(key)
    }

    override fun evict(): KEY? {
        val itr = accessOrder.iterator()
        if(itr.hasNext()) {
            val oldest = itr.next()
            accessOrder.remove(oldest)
            return oldest
        } else {
            return null
        }

    }

    override fun remove(key: KEY) {
        accessOrder.remove(key)
    }

    override fun onPut(key: KEY) {
        accessOrder.remove(key)
        accessOrder.add(key)
    }
}