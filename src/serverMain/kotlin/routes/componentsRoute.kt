package routes

import components.getProjectCard
import io.ktor.application.*
import io.ktor.response.*
import io.ktor.routing.*

fun Routing.components() {

    get("/components/card") {
        val card = getProjectCard(
            "hello world",
            "#",
            "lorem ipsum dolor sit amet bla bla bla",
            "https://potatotv.net/img/alps_16_9.jpg"
        )
        call.respondText(card)
    }
}