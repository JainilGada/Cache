# High Performance In-Memory Cache

A pluggable, high-throughput in-memory cache designed for single-node deployment with support for size constraints, TTL-based expiration, and multiple eviction strategies. Designed to evolve into a multi-threaded and distributed cache with support for various communication protocols like HTTP, TCP, and gRPC.

---

## ✅ Features Implemented (Phase 1)

- [ ] Size-bound cache with maximum byte capacity
- [ ] TTL (Time-to-Live) for entries
- [ ] Lazy expiration on access
- [ ] Probabilistic TTL cleaner (25% chance on access)
- [ ] LRU eviction policy
- [ ] Single-threaded core engine



---

## 🚀 Getting Started

### Requirements

- Kotlin 1.9+
- JDK 17+
- Gradle or Maven

### Clone and Run

```bash
git clone https://github.com/yourusername/high-perf-cache.git
cd high-perf-cache
./gradlew run
````

---

## ✏️ TODOs

### ✅ Core Functionality

* [x] Create basic cache data structure
* [x] Implement TTL with lazy + probabilistic cleanup
* [x] LRU eviction policy
* [x] Basic testing with main()

### 🔁 Eviction Policies

* [ ] Implement LFU (Least Frequently Used)
* [ ] Implement Random eviction

### 🧵 Concurrency

* [ ] Multi-threaded access with `ConcurrentHashMap`
* [ ] Optimistic concurrency using CAS
* [ ] Pessimistic concurrency with locks

### 🧪 Testing

* [ ] Add JUnit tests for cache get/put/remove
* [ ] Benchmark TTL cleanup performance
* [ ] Benchmark eviction throughput (under pressure)

### 🌐 Communication Protocols

* [ ] Add simple HTTP/1.1 interface (Ktor/Spring)
* [ ] Implement gRPC interface
* [ ] Add raw TCP protocol handler
* [ ] Evaluate HTTP/2 performance benefits

### 📤 Serialization & CLI

* [ ] Add CLI for interactive testing
* [ ] Optional: Add serialization support (e.g., JSON, ProtoBuf) for values

### 📡 Distributed Cache (Future Phase)

* [ ] Add node discovery mechanism
* [ ] Add consistent hashing for key distribution
* [ ] Add replication support
* [ ] Handle cross-node eviction TTL sync

---

## 📊 Sample Usage

```kotlin
val cache = InMemoryCache(
    maxSize = 1024 * 10, // 10 KB
    evictionPolicy = LRUEvictionPolicy()
)

cache.put("key1", ByteArray(2048), 5000)
val value = cache.get("key1")
```

---

## 📂 Project Structure

```
src/
├── main/
│   ├── kotlin/
│   │   ├── CacheEntry.kt
│   │   ├── EvictionPolicy.kt
│   │   ├── LRUEvictionPolicy.kt
│   │   ├── InMemoryCache.kt
│   │   └── Main.kt
│   └── resources/
└── test/
```

---

## 📄 License

MIT

```
```
