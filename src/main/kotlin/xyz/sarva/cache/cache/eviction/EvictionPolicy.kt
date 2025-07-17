package xyz.sarva.cache.cache.eviction

interface EvictionPolicy<KEY> {
    fun onAccess(key: KEY);
    fun evict(): KEY?;

    fun onPut(key: KEY);
    fun remove(key: KEY);
}