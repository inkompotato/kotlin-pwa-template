package routes

import Database
import io.ktor.application.*
import io.ktor.html.*
import io.ktor.http.*
import io.ktor.routing.*
import org.koin.ktor.ext.inject
import pages.buildIndexPage

fun Routing.index() {
    val database: Database by inject()

    get("/") {
        call.respondHtml(HttpStatusCode.OK, buildIndexPage("null"))
    }
}