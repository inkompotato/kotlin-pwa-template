package routes

import components.getProjectCard
import io.ktor.application.*
import io.ktor.html.*
import io.ktor.http.*
import io.ktor.response.*
import io.ktor.routing.*
import kotlin.reflect.jvm.reflect

fun Routing.components() {

    get("/components/card") {
        val card = getProjectCard(
            "hello world",
            "#",
            "lorem ipsum dolor sit amet bla bla bla",
            "https://potatotv.net/img/alps_16_9.jpg"
        )
        println(card)
        call.respondText(card)
    }
}