@file:OptIn(ExperimentalKotlinGradlePluginApi::class, ExperimentalWasmDsl::class)

import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi
import org.jetbrains.kotlin.gradle.ExperimentalWasmDsl

plugins {
    alias(libs.plugins.kotlin.multiplatform)
}

// Skiko's wasm klib doesn't bundle the Skia .wasm binary + JS glue: pull the runtime
// resource bundle and unpack it onto the wasm resources path so the browser can load it.
val skikoWasm: Configuration by configurations.creating

dependencies {
    skikoWasm("org.jetbrains.skiko:skiko-js-wasm-runtime:${libs.versions.skiko.get()}")
}

val unpackSkikoWasmRuntime by tasks.registering(Copy::class) {
    from(skikoWasm.map { zipTree(it) })
    into(layout.buildDirectory.dir("skiko-wasm"))
}

kotlin {
    jvmToolchain(21)

    compilerOptions {
        freeCompilerArgs.add("-XXLanguage:+ExplicitBackingFields")
    }

    jvm {
        binaries {
            executable {
                mainClass.set("com.neoutils.example.pong.MainKt")
            }
        }
    }

    wasmJs {
        browser {
            commonWebpackConfig {
                // Must match the <script src="..."> in wasmJsMain/resources/index.html.
                outputFileName = "pong.js"
            }
        }
        binaries.executable()
    }

    sourceSets {
        commonMain.dependencies {
            implementation(project(":core"))
            implementation(project(":runtime-skiko"))
            implementation(project(":core-dsl"))
        }
        val wasmJsMain by getting {
            resources.srcDir(unpackSkikoWasmRuntime)
            // wasmJs resources from dependency modules aren't bundled by webpack, so pull
            // the engine's default font (runtime-skiko) onto this app's resources path.
            resources.srcDir(project(":runtime-skiko").file("src/wasmJsMain/resources"))
        }
    }
}
