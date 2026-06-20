pluginManagement {
    repositories {
        gradlePluginPortal()
        mavenCentral()
    }
}

plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "1.0.0"
}

dependencyResolutionManagement {
    repositories {
        mavenCentral()
    }
}

rootProject.name = "kmp-engine"

include(":core")
include(":core-dsl")
include(":runtime-skiko")
include(":example:hello-world")
include(":example:bouncing-ball")
include(":example:colliding-balls")
include(":example:keyboard-input")
include(":example:pong")
