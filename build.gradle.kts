plugins {
    id("java")
    id("com.github.johnrengelman.shadow") version "8.1.1"
}

base {
    group = "com.thecrownstudios"
    version = "1.2.4"
    archivesName = "minestom-launcher"
}

java {
    sourceCompatibility = JavaVersion.VERSION_21
    targetCompatibility = JavaVersion.VERSION_21

    toolchain {
        languageVersion = JavaLanguageVersion.of(21)
    }

    tasks.withType<ProcessResources> {
        filteringCharset = "UTF-8"
    }

    tasks.withType<JavaCompile> {
        options.encoding = "UTF-8"
    }

    tasks.withType<Javadoc> {
        options.encoding = "UTF-8"
    }

    tasks.withType<Jar> {
        manifest {
            attributes["Main-Class"] = "${project.group}.minestomlauncher.Main"
        }

        exclude("**/resources/server.json")
    }
}

repositories {
    mavenCentral()

    maven { url = uri("https://jitpack.io") }
    maven {
        name = "Sponge"
        url = uri("https://repo.spongepowered.org/maven")
    }
    maven {
        name = "Sonatype"
        url = uri("https://s01.oss.sonatype.org/content/repositories/snapshots/")
    }
    maven {
        name = "CentralSonatype"
        url = uri("https://central.sonatype.com/")
    }
    maven {
        name = "Minecraft"
        url = uri("https://libraries.minecraft.net")
    }
}

dependencies {
    val minestom_version             = findProperty("minestom_version")
    val minestom_extensions_version  = findProperty("minestom_extensions_version")

    val slimeloader_version          = findProperty("slimeloader_version")
    val polar_version                = findProperty("polar_version")

    val minimessage_version          = findProperty("minimessage_version")
    val jackson_version              = findProperty("jackson_version")
    val jnoise_version               = findProperty("jnoise_version")

    val ansi_version                 = findProperty("ansi_version")
    val jansi_version                = findProperty("jansi_version")
    val jline_version                = findProperty("jline_version")
    val jline_terminal_jansi_version = findProperty("jline_terminal_jansi_version")
    val log4j_version                = findProperty("log4j_version")

    // Minestom modules
    implementation("net.minestom", "minestom-snapshots", "$minestom_version")
    implementation("dev.hollowcube", "minestom-ce-extensions", "$minestom_extensions_version")

    // World formats
    implementation("com.github.CatDevz", "SlimeLoader", "$slimeloader_version")
    implementation("dev.hollowcube", "polar", "$polar_version")

    // Misc
    implementation("net.kyori", "adventure-text-minimessage", "$minimessage_version")
    implementation("com.fasterxml.jackson.core", "jackson-databind", "$jackson_version")
    implementation("de.articdive", "jnoise-pipeline", "$jnoise_version")

    // Terminal & Logging
    implementation("net.kyori", "ansi", "$ansi_version")
    implementation("org.fusesource.jansi", "jansi", "$jansi_version")
    implementation("org.jline", "jline", "$jline_version")
    implementation("org.jline", "jline-terminal-jansi", "$jline_terminal_jansi_version")
    implementation("org.apache.logging.log4j", "log4j-core", "$log4j_version")
}

tasks {
    shadowJar {
        exclude("server.json")

        println(message = "ShadowJar Informations")
        println(message = "- project_name:     ${rootProject.name}")
        println(message = "- module_name:      ${archiveBaseName.get()}")
        println(message = "- module_version:   ${archiveVersion.get()}")
        println(message = "- module_extension: ${archiveExtension.get()}")
        println()

        archiveFileName.set("${rootProject.name}-${archiveVersion.get()}.${archiveExtension.get()}")
    }

    build {
        finalizedBy(shadowJar)
    }
}