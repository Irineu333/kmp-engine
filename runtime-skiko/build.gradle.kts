@file:OptIn(ExperimentalKotlinGradlePluginApi::class, ExperimentalWasmDsl::class)

import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi
import org.jetbrains.kotlin.gradle.ExperimentalWasmDsl

plugins {
    alias(libs.plugins.kotlin.multiplatform)
}

// Skiko publishes one AWT runtime artifact per OS + architecture (it bundles Skia's native
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
        // InputQueue is a multiplatform expect/actual class (still Beta in the compiler).
        freeCompilerArgs.add("-Xexpect-actual-classes")
    }

    jvm()

    wasmJs {
        browser()
    }

    sourceSets {
        commonMain.dependencies {
            api(project(":core"))
            api(project(":core-dsl"))
            // Umbrella artifact: exposes the common org.jetbrains.skia.* API and the
            // SkikoRenderDelegate/SkiaLayer abstractions; Gradle metadata resolves the
            // per-target variant (skiko-awt for jvm, skiko-wasm-js for wasmJs).
            implementation("org.jetbrains.skiko:skiko:${libs.versions.skiko.get()}")
        }
        jvmMain.dependencies {
            // Native Skia binaries for the host (the umbrella jvm variant has API only).
            api("org.jetbrains.skiko:skiko-awt-runtime-$skikoTarget:${libs.versions.skiko.get()}")
        }
        wasmJsMain.dependencies {
            // Browser DOM bindings (document, HTMLCanvasElement, KeyboardEvent): these
            // live outside the wasm stdlib.
            implementation(libs.kotlinx.browser)
            // Coroutines provide Promise.await(), so the async font fetch reads as linear
            // code instead of nested then() callbacks (only the browser fetches fonts).
            implementation(libs.kotlinx.coroutines.core)
        }
    }
}
