package xyz.sarva.cache.cache

interface Cache {
    fun put(key: String, value: ByteArray, ttlMillis: Long)
    fun get(key: String): ByteArray?

    fun remove(key: String)
    fun stats(): String
}