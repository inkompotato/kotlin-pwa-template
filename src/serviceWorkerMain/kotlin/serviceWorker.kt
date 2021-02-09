import kotlinx.coroutines.*
import org.w3c.fetch.Response
import org.w3c.workers.*

/**
 * source:
 * https://github.com/grantas33/Kotlin-PWA-starter-kit/blob/master/serviceWorker/src/main/kotlin/Main.kt
 */

external val self: ServiceWorkerGlobalScope
val scope = MainScope()

fun main() {
    installServiceWorker()
}

const val MAIN_CACHE = "mainCache"

fun installServiceWorker() {
    val offlineContent = arrayOf(
        "/",
        "/output.js"
    )

    self.addEventListener("install", { event ->
        event as InstallEvent
        println("install event occured")
        scope.async {
            val cache = self.caches.open(MAIN_CACHE).await()
            cache.addAll(offlineContent).await()
            println("offline cache loaded")
        }.let {
            event.waitUntil(it.asPromise())
        }
    })

    self.addEventListener("fetch", { event ->
        event as FetchEvent
        if(event.request.url.contains("http").not()) return@addEventListener

        scope.async {
            val cache = self.caches.open(MAIN_CACHE).await()
            try {
                val response = self.fetch(event.request).await()
                cache.put(event.request, response.clone()).await()
                return@async response
            } catch (e: Throwable) {
                return@async self.caches.match(event.request).await().unsafeCast<Response>()
            }
        }.let {
            event.respondWith(it.asPromise())
        }
    })
}