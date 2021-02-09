import io.ktor.application.*
import io.ktor.features.*
import io.ktor.http.*
import io.ktor.http.content.*
import io.ktor.response.*
import io.ktor.routing.*
import io.ktor.serialization.*
import io.ktor.util.*
import org.koin.core.context.startKoin
import org.slf4j.event.Level
import routes.index

@KtorExperimentalAPI
internal fun Application.mainModule() {
    val dbModule = createDbModule(environment.config.config("database"))
    startKoin { modules(dbModule) }

    install(CallLogging) {
        level = Level.INFO
    }
    install(DefaultHeaders)

    install(StatusPages) {
        exception<Throwable> { throwable ->
            call.respond(HttpStatusCode.InternalServerError, throwable.message ?: "Internal Server Error")
            throw throwable //re-throw to not swallow error and show it in console
        }
    }

    install(CORS) {
        anyHost()
        allowNonSimpleContentTypes = true
        method(HttpMethod.Options)
        allowSameOrigin = true
    }

    install(ContentNegotiation) {
        json()
    }

    install(Routing) {
        static("/") {
            resource("output.js")
            resource("serviceworker.js")
        }
        static("/static") {
            resources("/data")
        }
        index()
    }
}