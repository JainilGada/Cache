package xyz.sarva.distributedcache.cache

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import xyz.sarva.distributedcache.cache.eviction.LRUEvictionPolicy
import java.util.concurrent.TimeUnit
import kotlin.test.Ignore

class InMemoryCacheTest {
    private lateinit var cache: InMemoryCache
    private val maxSize = 1000L
    private val ttl = System.currentTimeMillis() + 1 * 60 * 1000 // 1 min for testing

    @BeforeEach
    fun setUp() {
        cache = InMemoryCache(maxSize.toInt(), LRUEvictionPolicy())
    }

    @Test
    fun `should store and retrieve value`() {
        val key = "testKey"
        val value = "testValue".toByteArray()
        
        cache.put(key, value, ttl)
        val retrieved = cache.get(key)
        
        assertNotNull(retrieved)
        assertArrayEquals(value, retrieved)
    }

    @Test
    fun `should return null for non-existent key`() {
        val retrieved = cache.get("nonExistentKey")
        assertNull(retrieved)
    }

    @Ignore
    @Test
    fun `should evict items when cache is full`() {
        // Fill the cache with small items
        for (i in 1..100) {
            cache.put("item$i", "v$i".toByteArray(), ttl)
        }

        // Access some items to change their access order
        cache.get("item5")
        cache.get("item10")

        // Add one more item to trigger eviction
        cache.put("newItem", "newValue".toByteArray(), ttl)

        // Verify LRU item was evicted
        assertNull(cache.get("item1"))
        assertNotNull(cache.get("item5")) // Should still be in cache as it was accessed
    }

    @Ignore
    @Test
    fun `should respect TTL`() {
        val key = "tempKey"
        val shortTtl = 100L // 100ms

        cache.put(key, "tempValue".toByteArray(), shortTtl)
        assertNotNull(cache.get(key))

        // Wait for TTL to expire
        Thread.sleep(shortTtl + 50)

        assertNull(cache.get(key))
    }

    @Test
    fun `should remove item from cache`() {
        val key = "toRemove"
        cache.put(key, "value".toByteArray(), ttl)
        
        cache.remove(key)
        
        assertNull(cache.get(key))
        assertEquals(0, cache.stats().split("Current Size: ")[1].split(" ")[0].toInt())
    }

    @Test
    fun `should return correct statistics`() {
        cache.put("key1", "value1".toByteArray(), ttl)
        cache.put("key2", "value2".toByteArray(), ttl)
        
        val stats = cache.stats()
        
        assertTrue(stats.contains("Total Size: $maxSize"))
        assertTrue(stats.contains("Current Size: 12")) // 6 bytes per value (value1 + value2)
        assertTrue(stats.contains("Entries: 2"))
    }
}
