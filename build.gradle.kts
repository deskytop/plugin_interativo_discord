plugins {
    kotlin("jvm") version "2.1.10"
    id("java")
    id("com.github.johnrengelman.shadow") version "8.1.1"
}

group = "com.desky.deathwarn"
version = "1.0"

java {
    toolchain.languageVersion.set(JavaLanguageVersion.of(17))
}

repositories {
    mavenCentral()
    maven("https://hub.spigotmc.org/nexus/content/repositories/public/")
    maven("https://m2.dv8tion.net/releases")
    maven("https://jitpack.io")

}

dependencies {
    implementation("net.dv8tion:JDA:5.0.0-beta.20")
    implementation("com.squareup.okio:okio-jvm:3.5.0")
    compileOnly("org.spigotmc:spigot-api:1.20.1-R0.1-SNAPSHOT")
    compileOnly("net.luckperms:api:5.4")
    implementation("com.google.guava:guava:32.1.3-jre")
    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    implementation("org.slf4j:slf4j-simple:2.0.9")

}

tasks.processResources {
    duplicatesStrategy = DuplicatesStrategy.EXCLUDE
}

tasks {
    shadowJar {
        relocate("net.dv8tion", "com.desky.libs.discord")
        archiveFileName.set("DeathWarn.jar")
        duplicatesStrategy = DuplicatesStrategy.EXCLUDE

        from(sourceSets.main.get().output) {
            exclude("**/*.properties")
        }

        manifest {
            attributes(
                "Main-Class" to "com.desky.deathwarn.DeathWarn",
                "Implementation-Version" to project.version
            )
        }
    }
}

tasks.build {
    dependsOn(tasks.shadowJar)
}

sourceSets {
    main {
        java {
            srcDirs("src/main/java", "src/main/kotlin")
        }
        resources {
            srcDirs("src/main/resources")
        }
    }
}

tasks.test {
    useJUnitPlatform()
}

kotlin {
    jvmToolchain(17)
}

tasks.withType<JavaCompile> {
    options.encoding = "UTF-8"
}

tasks.jar {
    enabled = false
}

