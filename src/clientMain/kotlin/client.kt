import kotlinx.browser.document
import kotlinx.browser.window
import kotlinx.html.div
import kotlinx.html.dom.append
import org.w3c.dom.Node

fun main() {
    window.onload = {
        document.body?.sayHello()
        window.navigator.serviceWorker.register("/serviceworker.js").then { regitration ->
            println("service worker registered with scope: ${regitration.scope}")
        }.catch { error ->
            println("service worker registration failed: $error")
        }
    }
}

fun Node.sayHello() {
    append {
        div("container-lg text-light") {
            +"Hello from JS"
        }
    }
}