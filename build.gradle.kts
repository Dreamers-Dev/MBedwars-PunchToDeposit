plugins {
    alias(libs.plugins.kotlin) // Kotlin plugin for JVM
    alias(libs.plugins.shadow) // Shadow plugin for creating fat JARs
}

allprojects {
    group = "dev.dreamers"
    version = "1.3.1"
    description = "High performance and Easy to use recreation of Hypixels PunchToDeposit feature."

    repositories {
        mavenLocal() // Local Maven repository
        mavenCentral() // Central Maven repository
    }
}

subprojects {
    apply(plugin = "java-library")
    apply(plugin = "kotlin")
    apply(plugin = "com.gradleup.shadow")

    tasks {
        build {
            dependsOn(shadowJar) // Include shadowJar in the build lifecycle
        }

        shadowJar {
            relocate("kotlin", "dev.dreamers.kotlin")
            relocate("org.jetbrains.annotations", "dev.dreamers.jetbrains.annotations")
            relocate("org.intellij.lang.annotations", "dev.dreamers.intellij.lang.annotations")
        }
    }
}