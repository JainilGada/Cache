package xyz.sarva.distributedcache.cache

import xyz.sarva.distributedcache.cache.eviction.EvictionPolicy

class InMemoryCache(
    private val maxSize: Int,
    private val evictionPolicy: EvictionPolicy<String>
): Cache {

   // private val logger = mu.KotlinLogging.logger {}
    private var currentSize = 0 // concurrency needs to be handled on each access

    private val map = mutableMapOf<String, CacheEntry>()
    override fun put(key: String, value: ByteArray, ttlMillis: Long) {
        val size = value.size;   // We are ignoring size of key and CacheEntry which we will be creating for now as Kotlin don't have built-in size()
        if (size > maxSize) {
            println { "Cache size exceeded, allocated $size bytes" }
            return
        }
        evictIfNeeded( size)

        val entry = CacheEntry(key, value, ttlMillis)
        map[key] = entry
        currentSize += size
        evictionPolicy.onPut(key)
    }

    private fun evictIfNeeded(size: Int) {
         while (currentSize + size > maxSize) {
             val evictKey = evictionPolicy.evict()
             if(evictKey != null) {
                 remove(evictKey)
             }
         }
    }

    override fun get(key: String): ByteArray? {
        val value = map[key] ?: return null
        if(value.expiresAt < System.currentTimeMillis()) {
            remove(key)
            return null
        }
        value.lastAccessTime = System.currentTimeMillis()
        value.accessCount++
        evictionPolicy.onAccess(key)
        return value.value
    }

    override fun remove(key: String) {
        val value = map[key] ?: return
        currentSize -= value.value.size
        evictionPolicy.remove(key)
        map.remove(key)
        return
    }

    override fun stats(): String {
        return "Total Size: $maxSize bytes, Current Size: $currentSize bytes, Entries: ${map.size}"
    }

}