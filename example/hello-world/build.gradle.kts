@file:OptIn(ExperimentalKotlinGradlePluginApi::class)

import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi

plugins {
    alias(libs.plugins.kotlin.multiplatform)
}

kotlin {
    jvmToolchain(21)

    compilerOptions {
        freeCompilerArgs.add("-XXLanguage:+ExplicitBackingFields")
    }

    jvm {
        binaries {
            executable {
                mainClass.set("com.neoutils.example.MainKt")
            }
        }
    }

    sourceSets {
        jvmMain.dependencies {
            implementation(project(":core"))
            implementation(project(":runtime-skiko"))
            implementation(project(":core-dsl"))
        }
    }
}
