import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar

group   = "com.thecrownstudios"
version = "1.2.4"

plugins {
    id("java")
    id("com.github.johnrengelman.shadow") version "8.1.1"
}

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17

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

    tasks.withType<ProcessResources> {
        filteringCharset = "UTF-8"
    }
}

shadow {
    tasks.withType<ShadowJar> {
        exclude("server.json")

        println(message = "SHADOWJAR INFORMATION")
        println(message = "- project_name:     ${rootProject.name}")
        println(message = "- module_name:      ${archiveBaseName.get()}")
        println(message = "- module_version:   ${archiveVersion.get()}")
        println(message = "- module_extension: ${archiveExtension.get()}")
        println()

        archiveFileName.set("${rootProject.name}-${archiveVersion.get()}.${archiveExtension.get()}")
    }
}

repositories {
    mavenCentral()

    maven {
        name = "JitPack"
        url = uri("https://jitpack.io")
    }
    maven {
        name = "Sponge"
        url = uri("https://repo.spongepowered.org/maven")
    }
    maven {
        name = "Sonatype"
        url = uri("https://s01.oss.sonatype.org/content/repositories/snapshots/")
    }
    maven {
        name = "Minecraft"
        url = uri("https://libraries.minecraft.net")
    }
}

dependencies {
    val minestom_version    = project.property("minestom_version")!! as String
    val jnoise_version      = project.property("jnoise_version")!! as String
    val minimessage_version = project.property("minimessage_version")!! as String

    implementation("com.github.Minestom:Minestom:$minestom_version")
    implementation("de.articdive:jnoise-pipeline:$jnoise_version")
    implementation("com.github.CatDevz:SlimeLoader:master-SNAPSHOT")
    implementation("net.kyori:adventure-text-minimessage:$minimessage_version")
    implementation("com.fasterxml.jackson.core:jackson-databind:2.14.2")
}

tasks {
    build {
        finalizedBy(shadowJar)
    }
}