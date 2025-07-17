package xyz.sarva.cache

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class DistributedCacheApplication

fun main(args: Array<String>) {
    runApplication<DistributedCacheApplication>(*args)
}
