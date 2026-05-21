plugins {
    kotlin("jvm") version "2.0.20"
}

group = "pe.edu.upc.aaw"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(kotlin("test"))
}

tasks.test {
    useJUnitPlatform()
}