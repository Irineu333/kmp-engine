plugins {
    kotlin("multiplatform") version "2.3.21"
}

group = "com.neoutils"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

kotlin {
    jvmToolchain(21)

    jvm {
        binaries {
            executable {
                mainClass.set("com.neoutils.MainKt")
            }
        }
    }

    sourceSets {
        commonTest.dependencies {
            implementation(kotlin("test"))
        }
    }
}
