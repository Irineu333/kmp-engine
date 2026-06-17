plugins {
    kotlin("jvm") version "2.3.21"
    application
}

group = "com.neoutils"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(kotlin("test"))
}

kotlin {
    jvmToolchain(21)
}

application {
    mainClass.set("com.neoutils.MainKt")
}

tasks.test {
    useJUnitPlatform()
}