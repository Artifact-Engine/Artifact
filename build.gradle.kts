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

    // GLM
    implementation("io.github.kotlin-graphics:glm:0.9.9.1-12")

    // SLF4J
    implementation("org.slf4j:slf4j-api:2.1.0-alpha1")

    // Kotlinx json serialization
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.6.3")

    // Kevet Event System
    implementation("com.github.meo209:Kevet:0bf7452ce4")

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