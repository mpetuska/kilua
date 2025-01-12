@file:Suppress("UnstableApiUsage")

pluginManagement {
    repositories {
        gradlePluginPortal()
        mavenCentral()
        maven {
            url = uri("https://maven.pkg.jetbrains.space/kotlin/p/dokka/dev")
        }
        mavenLocal()
    }
}

dependencyResolutionManagement {
    repositories {
        mavenCentral()
        maven {
            url = uri("https://maven.pkg.jetbrains.space/kotlin/p/dokka/dev")
        }
        mavenLocal()
    }
}

rootProject.name = "kilua-project"
include(":kilua")
include(":modules:kilua-dom")
include(":modules:kilua-bootstrap")
include(":modules:kilua-bootstrap-icons")
include(":modules:kilua-fontawesome")
include(":modules:kilua-imask")
include(":modules:kilua-lazy-layouts")
include(":modules:kilua-rest")
include(":modules:kilua-routing")
include(":modules:kilua-rsup-progress")
include(":modules:kilua-splitjs")
include(":modules:kilua-tabulator")
include(":modules:kilua-tempus-dominus")
include(":modules:kilua-toastify")
include(":modules:kilua-tom-select")
include(":modules:kilua-trix")
include(":modules:kilua-testutils")
include(":kilua-assets")
include(":examples:bootstrap-form")
include(":examples:hello-world")
include(":examples:js-framework-benchmark")
include(":examples:playground")
include(":examples:resources")
