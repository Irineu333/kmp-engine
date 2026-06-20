@file:OptIn(ExperimentalKotlinGradlePluginApi::class)

import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi

plugins {
    alias(libs.plugins.kotlin.multiplatform)
}

// Skiko publishes one runtime artifact per OS + architecture (it bundles Skia's native
// .dll/.dylib/.so). Detect the host at configuration time to pull the correct binary.
val osName: String = System.getProperty("os.name")

val targetOs = when {
    osName == "Mac OS X" -> "macos"
    osName.startsWith("Win") -> "windows"
    osName.startsWith("Linux") -> "linux"
    else -> error("Unsupported operating system: $osName")
}

val targetArch = when (val osArch = System.getProperty("os.arch")) {
    "x86_64", "amd64" -> "x64"
    "aarch64" -> "arm64"
    else -> error("Unsupported architecture: $osArch")
}

val skikoTarget = "$targetOs-$targetArch"

kotlin {
    jvmToolchain(21)

    compilerOptions {
        freeCompilerArgs.add("-XXLanguage:+ExplicitBackingFields")
    }

    jvm()

    sourceSets {
        jvmMain.dependencies {
            api(project(":core"))
            api(project(":core-dsl"))
            api("org.jetbrains.skiko:skiko-awt-runtime-$skikoTarget:${libs.versions.skiko.get()}")
        }
    }
}
