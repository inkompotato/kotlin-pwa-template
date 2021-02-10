package routes

import io.ktor.application.*
import io.ktor.response.*
import io.ktor.routing.*

fun Routing.components() {

    get("/components/card") {
        call.respondText { "<p>hello world</p>"}
    }
}