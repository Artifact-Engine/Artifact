plugins {
    kotlin("jvm") version "1.9.23"
}

group = "org.openartifact"
version = "1.0-SNAPSHOT"

val lwjglNatives = Pair(
    System.getProperty("os.name")!!,
    System.getProperty("os.arch")!!
).let { (name, arch) ->
    when {
        arrayOf("Linux", "SunOS", "Unit").any { name.startsWith(it) } ->
            if (arrayOf("arm", "aarch64").any { arch.startsWith(it) })
                "natives-linux${if (arch.contains("64") || arch.startsWith("armv8")) "-arm64" else "-arm32"}"
            else if (arch.startsWith("ppc"))
                "natives-linux-ppc64le"
            else if (arch.startsWith("riscv"))
                "natives-linux-riscv64"
            else
                "natives-linux"
        arrayOf("Windows").any { name.startsWith(it) }                ->
            "natives-windows"
        else                                                                            ->
            throw Error("Unrecognized or unsupported platform. Please set \"lwjglNatives\" manually")
    }
}

repositories {
    mavenCentral()

    maven { url = uri("https://jitpack.io") }
}

val kotlinVersion = project.properties["kotlin_version"].toString()

dependencies {

    implementation("io.github.classgraph:classgraph:4.8.172")

    // Kotlin-reflect
    implementation(kotlin("reflect"))

    // Gson
    implementation("com.google.code.gson:gson:2.11.0")

    // glm
    implementation("io.github.kotlin-graphics:glm:0.9.9.1-12")

    // SLF4J
    implementation("org.slf4j:slf4j-api:2.0.13")
    implementation("org.slf4j:slf4j-simple:2.0.13")

    // Kotlinx
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.8.1-Beta")

    // Apache Commons
    implementation("org.apache.commons:commons-collections4:4.5.0-M1")

    // LWJGL
    implementation(platform("org.lwjgl:lwjgl-bom:3.3.3"))

    implementation("org.lwjgl", "lwjgl")
    implementation("org.lwjgl", "lwjgl-assimp")
    implementation("org.lwjgl", "lwjgl-glfw")
    implementation("org.lwjgl", "lwjgl-nfd")
    implementation("org.lwjgl", "lwjgl-openal")
    implementation("org.lwjgl", "lwjgl-opengl")
    implementation("org.lwjgl", "lwjgl-stb")
    runtimeOnly("org.lwjgl", "lwjgl", classifier = lwjglNatives)
    runtimeOnly("org.lwjgl", "lwjgl-assimp", classifier = lwjglNatives)
    runtimeOnly("org.lwjgl", "lwjgl-glfw", classifier = lwjglNatives)
    runtimeOnly("org.lwjgl", "lwjgl-nfd", classifier = lwjglNatives)
    runtimeOnly("org.lwjgl", "lwjgl-openal", classifier = lwjglNatives)
    runtimeOnly("org.lwjgl", "lwjgl-opengl", classifier = lwjglNatives)
    runtimeOnly("org.lwjgl", "lwjgl-stb", classifier = lwjglNatives)
}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(17)
}