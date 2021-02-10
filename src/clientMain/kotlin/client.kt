import io.ktor.client.*
import io.ktor.client.engine.js.*
import io.ktor.client.request.*
import kotlinx.browser.document
import kotlinx.browser.window
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.html.div
import kotlinx.html.dom.append
import kotlinx.html.unsafe
import org.w3c.dom.Node

fun main() {
    window.onload = {
        addHelloCard(document.getElementById("main") as Node)
        window.navigator.serviceWorker.register("/serviceworker.js").then { regitration ->
            println("service worker registered with scope: ${regitration.scope}")
        }.catch { error ->
            println("service worker registration failed: $error")
        }
    }
}

val client = HttpClient(Js) {}

fun addHelloCard(component: Node) {
    GlobalScope.launch {
        component.createUnsafe(fetchComponent())
    }
}

fun Node.createUnsafe(str: String) {
    append {
        div {
            unsafe {
                +str
            }
        }
    }
}

suspend fun fetchComponent(): String {
    return client.get("http://127.0.0.1:8080/components/card")
}