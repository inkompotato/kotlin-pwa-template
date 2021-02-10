import org.jetbrains.kotlin.gradle.targets.js.webpack.KotlinWebpack
import org.jetbrains.kotlin.gradle.targets.js.webpack.KotlinWebpackOutput.Target

plugins {
    kotlin("multiplatform") version "1.4.30"
    kotlin("plugin.serialization") version "1.4.30"
    application
}

group = "net.potatotv"
version = "1.0-SNAPSHOT"

repositories {
    jcenter()
    mavenCentral()
    maven { url = uri("https://dl.bintray.com/kotlin/kotlinx") }
    maven { url = uri("https://dl.bintray.com/kotlin/ktor") }
}

kotlin {
    jvm("server") {
        compilations.all {
            kotlinOptions.jvmTarget = "11"
        }
        withJava()
        testRuns["test"].executionTask.configure {
            useJUnitPlatform()
        }
    }
    js("client", IR) {
        browser {
            binaries.executable()
            webpackTask {
                cssSupport.enabled = true
            }
            runTask {
                cssSupport.enabled = true
            }
        }
    }
    js("serviceWorker", IR) {
        browser {
            binaries.executable()
            webpackTask {
                output.libraryTarget = Target.SELF
            }
        }
    }

    sourceSets {
        val ktorVersion = "1.4.1"
        val koinVersion = "2.1.6"
        val kmongoVersion = "4.1.2"

        val commonMain by getting {
            dependencies {
                implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.0.1")
                implementation("org.jetbrains.kotlinx:kotlinx-serialization-core:1.0.1")
                implementation("org.jetbrains.kotlinx:kotlinx-serialization-protobuf:1.0.1")
            }
        }
        val commonTest by getting {
            dependencies {
                implementation(kotlin("test-common"))
                implementation(kotlin("test-annotations-common"))
            }
        }
        val serverMain by getting {
            dependencies {
                //ktor
                implementation("io.ktor:ktor-serialization:$ktorVersion")
                implementation("io.ktor:ktor-server-netty:$ktorVersion")
                implementation("io.ktor:ktor-html-builder:$ktorVersion")

                //kotlinx
                implementation("org.jetbrains.kotlinx:kotlinx-html-jvm:0.7.2")

                //dependency injection
                implementation ("org.koin:koin-ktor:$koinVersion")

                //mongo serialization
                implementation("org.litote.kmongo:kmongo-coroutine-serialization:$kmongoVersion")
                implementation("org.litote.kmongo:kmongo-id-serialization:$kmongoVersion")

                //logging
                implementation("org.slf4j:slf4j-simple:1.7.30")
            }
        }
        val serverTest by getting {
            dependencies {
                implementation(kotlin("test-junit5"))
                implementation("org.junit.jupiter:junit-jupiter-api:5.6.0")
                runtimeOnly("org.junit.jupiter:junit-jupiter-engine:5.6.0")
            }
        }
        val clientMain by getting {
            dependencies {
                implementation("org.jetbrains.kotlinx:kotlinx-html:0.7.2")
                implementation("io.ktor:ktor-client-js:$ktorVersion")

            }
        }
        val clientTest by getting

        val serviceWorkerMain by getting {
            dependencies {
                implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core-js:1.4.2")
            }
        }
        val serviceWorkerTest by getting
    }
}

application {
    mainClassName = "ServerKt"
}

tasks.getByName<KotlinWebpack>("clientBrowserProductionWebpack") {
    outputFileName = "output.js"
}
tasks.getByName<KotlinWebpack>("serviceWorkerBrowserProductionWebpack") {
    outputFileName = "serviceworker.js"
}

tasks.getByName<Jar>("serverJar") {
    dependsOn(tasks.getByName("clientBrowserProductionWebpack"))
    dependsOn(tasks.getByName("serviceWorkerBrowserProductionWebpack"))

    val clientBrowserProductionWebpack = tasks.getByName<KotlinWebpack>("clientBrowserProductionWebpack")
    from(File(clientBrowserProductionWebpack.destinationDirectory, clientBrowserProductionWebpack.outputFileName))

    val serviceWorkerBrowserProductionWebpack = tasks.getByName<KotlinWebpack>("serviceWorkerBrowserProductionWebpack")
    from(File(serviceWorkerBrowserProductionWebpack.destinationDirectory, serviceWorkerBrowserProductionWebpack.outputFileName))
}

tasks.getByName<JavaExec>("run") {
    dependsOn(tasks.getByName<Jar>("serverJar"))
    classpath(tasks.getByName<Jar>("serverJar"))
}