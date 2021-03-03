package pages

import kotlinx.html.*

fun buildIndexPage(data: Any): (HTML) -> Unit {

    fun HTML.vis() {
        head {
            title("Kotlin PWA")
            meta("charset", "UTF-8")
            meta("viewport", "width=device-width, initial-scale=1")
            meta("theme-color", "#212529")

            link(rel = "manifest", href = "/static/manifest.webmanifest")

            styleLink("https://cdn.jsdelivr.net/npm/uikit@3.6.17/dist/css/uikit.min.css")
            script(src = "https://cdn.jsdelivr.net/npm/uikit@3.6.17/dist/js/uikit.min.js") {}
            script(src = "https://cdn.jsdelivr.net/npm/uikit@3.6.17/dist/js/uikit-icons.min.js") {}

            style {
                //+"div { border-style: solid; border-width: 1px } .nav-item {width: 150px; height: 64px}"
            }
        }
        body("uk-overflow-hidden") {
            div("uk-grid-collapse") {
                //main grid
                attributes["uk-grid"] = ""
                div("uk-width-small@l uk-height-1-1@l uk-width-auto uk-flex uk-flex-wrap uk-flex-wrap-top") {
                    //navigation
                    id = "navigation-1"
                    div("uk-width-auto uk-width-small@l uk-padding-small") {
                        a("#", classes = "uk-button uk-button-text") { +"ONE" }
                    }
                    div("uk-width-auto uk-width-small@l uk-padding-small") {
                        a("#", classes = "uk-button uk-button-text") { +"TWO" }
                    }
                }
                div("uk-width-expand@l uk-width-1-1 uk-panel-scrollable") {
                    //main
                    attributes["uk-height-viewport"] = "expand: true"
                    attributes["style"] = "resize: none"
                    ul("uk-breadcrumb") {
                        li { a("#") { +"HOME" } }
                    }
                    div("uk-child-width-auto uk-child-width-1-2@s uk-child-width-1-3@m uk-child-width-1-4@l uk-child-width-1-6@xl uk-grid-small") {
                        attributes["uk-grid"] = ""
                        id = "section-1"
                    }
                }
            }
            //floating button + modal
            button(
                classes = "uk-icon-button uk-position-medium uk-position-bottom-right uk-position-absolute",
                type = ButtonType.button
            ) {
                attributes["uk-icon"] = "plus"
                attributes["uk-toggle"] = "target: #action-modal"
            }
            div("uk-modal-container") {
                id = "action-modal"
                attributes["uk-modal"] = ""
                div("uk-modal-dialog uk-modal-body") {
                    button(type = ButtonType.button, classes = "uk-modal-close-default") {
                        attributes["uk-close"] = ""
                    }
                    h2("uk-modal-title") { +"MODAL TITLE" }
                    div {
                        id = "modal-1"
                        +"MODAL CONTENT"
                    }
                }
            }
            //JS
            script { src = "/output.js" }
            script { src = "/serviceworker.js" }
        }
    }

    return HTML::vis
}