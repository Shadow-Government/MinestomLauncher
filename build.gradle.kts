import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar

group	=	"com.thecrownstudios"
version	=	"1.1"

plugins {
	id("java")
	id("com.github.johnrengelman.shadow") version "7.1.2"
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
			attributes["Main-Class"] = "com.thecrownstudios.minestomlauncher.Main"
		}
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

		//archiveFileName.set("minestom.${archiveExtension.get()}")
		archiveFileName.set("${rootProject.name}-${archiveVersion.get()}.${archiveExtension.get()}")
	}
}

repositories {
	mavenCentral()

	maven(url = "https://repo.spongepowered.org/maven")
	maven(url = "https://libraries.minecraft.net")
	maven(url = "https://jitpack.io")
}

dependencies {
	implementation("com.github.Minestom:Minestom:${project.property("minestom_version")}"){
		exclude("org.jboss.shrinkwrap.resolver")
		exclude("shrinkwrap-resolver-depchain")
	}
	implementation("org.jboss.shrinkwrap.resolver:shrinkwrap-resolver-impl-maven:3.1.4")	//fix dep downloading
	implementation("com.github.Articdive:JNoise:${project.property("jnoise_version")}")
	implementation("com.github.CatDevz:SlimeLoader:master-SNAPSHOT")
	implementation("net.kyori:adventure-text-minimessage:4.11.0")
	implementation("com.fasterxml.jackson.core:jackson-databind:2.14.1")
}
