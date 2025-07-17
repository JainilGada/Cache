package xyz.sarva.cache.cache

data class CacheEntry (
    val key: String,
    val value: ByteArray,
    val expiresAt: Long,
    val creationTime: Long = System.currentTimeMillis(),
    var lastAccessTime: Long = creationTime, //  needed for LRU
    var accessCount: Int = 0, //  needed for LFU
)