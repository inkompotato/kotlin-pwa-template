package components

import kotlinx.html.DIV
import kotlinx.html.*
import kotlinx.html.stream.appendHTML
import kotlin.reflect.jvm.reflect

fun getProjectCard(name: String, link: String, description: String, image: String): String {
    val text = buildString {
        appendHTML().div("uk-card uk-card-small uk-card-default") {
            div("uk-card-media-top") {
                img(src = image, classes = "card-img-top", alt = "Cover Image") {
                    //attributes["style"] = "max-width: 576px"
                }
            }
            div("uk-card-body") {
                h3("uk-card-title") { +name }
                p("card-text") { +description.take(500) }
                a(href = link, classes = "uk-button uk-button-text") { +"see more" }
            }

        }
    }
    return text
}