package pages

import kotlinx.html.*

fun buildIndexPage(data: Any) : (HTML) -> Unit {

    fun HTML.vis() {
        head {
            title("Kotlin PWA")
            meta("charset", "UTF-8")
            meta("viewport", "width=device-width, initial-scale=1")
            meta("theme-color", "#212529")

            link(rel= "manifest", href="/static/manifest.webmanifest")

            styleLink("https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta1/dist/css/bootstrap.min.css")
            script(src= "https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta1/dist/js/bootstrap.bundle.min.js") {}
        }
        body("bg-dark") {
            div("container-lg") {
                nav("navbar navbar-expand-lg navbar-dark bg-secondary") {
                    div("container-fluid") {
                        a("#", classes = "navbar-brand") {
                            +"potatoTV"
                        }
                    }
                }
            }

            //JS
            script {src = "/output.js"}
            script {src= "/serviceworker.js"}
        }
    }

    return HTML::vis
}