import java.net.URI

plugins {
    alias(libs.plugins.kotlin.multiplatform)

    id("maven-publish")
}

group = "dk.anigif"
version = "0.1.0"
val baseArtifactId = "anigif-kmp"

kotlin {
    jvm()
    iosX64()
    iosArm64()
    iosSimulatorArm64()
    linuxX64()

    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(libs.kotlin.coroutines.core)
            }
        }
        val commonTest by getting {
            dependencies {
                implementation(libs.kotlin.test)
            }
        }
    }
}

publishing {
    publications {
        publications.withType<MavenPublication> {
            if (artifactId.startsWith(project.name, ignoreCase = true)) {
                artifactId = artifactId.replaceFirst(project.name, baseArtifactId)
            }
        }
    }

    repositories {
        maven {
            name = "GitHubPackages"
            url = URI("https://maven.pkg.github.com/anigif/anigif-kmp")
            credentials {
                username = System.getenv("GITHUB_ACTOR")
                password = System.getenv("GITHUB_TOKEN")
            }
        }
    }
}
