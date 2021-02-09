import io.ktor.config.*
import io.ktor.util.*
import org.koin.core.module.Module
import org.koin.dsl.module
import org.litote.kmongo.coroutine.CoroutineDatabase
import org.litote.kmongo.coroutine.coroutine
import org.litote.kmongo.reactivestreams.KMongo

internal class Database(private val collection: String, dbURI: String, dbName: String) {
    init {
        //registerModule() //do serializer here
    }

    private val database: CoroutineDatabase = KMongo
        .createClient(dbURI)
        .coroutine
        .getDatabase(dbName)

    suspend fun getData() : String {
        //
        return "hello world"
    }
}

// Creates module for Koin DI and returns it
@KtorExperimentalAPI
fun createDbModule(config: ApplicationConfig): Module {
    return module {
        single {
            config.run {
                Database(
                    collection = property("collection").getString(),
                    dbName = property("dbName").getString(),
                    dbURI = property("dbURI").getString()
                )
            }
        }
    }
}