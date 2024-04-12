plugins {
    kotlin("jvm") version "1.9.23"
    kotlin("plugin.serialization") version "1.9.22"
}

group = "org.openartifact"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()

    maven { url = uri("https://jitpack.io") }
}

dependencies {

    // Gson
    implementation("com.google.code.gson:gson:2.10.1")

    // GLM
    implementation("io.github.kotlin-graphics:glm:0.9.9.1-12")

    // SLF4J and SLF4J Simple Provider
    implementation("org.slf4j:slf4j-simple:2.1.0-alpha1")
    implementation("org.slf4j:slf4j-api:2.1.0-alpha1")

    // Kotlinx
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.8.1-Beta")

    // LWJGL Dependencies
    implementation(platform("org.lwjgl:lwjgl-bom:3.3.3"))

    implementation("org.lwjgl", "lwjgl")
    implementation("org.lwjgl", "lwjgl-assimp")
    implementation("org.lwjgl", "lwjgl-glfw")
    implementation("org.lwjgl", "lwjgl-nanovg")
    implementation("org.lwjgl", "lwjgl-nfd")
    implementation("org.lwjgl", "lwjgl-openal")
    implementation("org.lwjgl", "lwjgl-opencl")
    implementation("org.lwjgl", "lwjgl-opengl")
    implementation("org.lwjgl", "lwjgl-stb")
    runtimeOnly("org.lwjgl", "lwjgl", classifier = "natives-linux")
    runtimeOnly("org.lwjgl", "lwjgl-assimp", classifier = "natives-linux")
    runtimeOnly("org.lwjgl", "lwjgl-glfw", classifier = "natives-linux")
    runtimeOnly("org.lwjgl", "lwjgl-nanovg", classifier = "natives-linux")
    runtimeOnly("org.lwjgl", "lwjgl-nfd", classifier = "natives-linux")
    runtimeOnly("org.lwjgl", "lwjgl-openal", classifier = "natives-linux")
    runtimeOnly("org.lwjgl", "lwjgl-opengl", classifier = "natives-linux")
    runtimeOnly("org.lwjgl", "lwjgl-stb", classifier = "natives-linux")
}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(17)
}