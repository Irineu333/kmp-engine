plugins {
    alias(libs.plugins.kotlin.multiplatform)
}

kotlin {
    jvmToolchain(21)

    compilerOptions {
        freeCompilerArgs.add("-XXLanguage:+ExplicitBackingFields")
    }

    jvm()

    sourceSets {
        jvmMain.dependencies {
            api(project(":core"))
            implementation(kotlin("reflect"))
        }
    }
}
