import org.jetbrains.dokka.gradle.DokkaTask

plugins {
    id("java-library")
    kotlin("jvm") version "1.3.40"
    id("org.jetbrains.dokka") version "0.9.18"
    id("maven-publish")
}

group = "hr.vpriscan.kost"

repositories {
    jcenter()
    mavenCentral()
}

dependencies {

    implementation(kotlin("stdlib"))

    testImplementation("junit:junit:4.12")
    testImplementation("com.google.code.gson:gson:2.8.5")
    testImplementation("com.fasterxml.jackson.core:jackson-databind:2.9.9")
}

val sourcesJar by tasks.creating(Jar::class) {
    from(sourceSets.main.get().allSource)
    archiveClassifier.set("sources")
}

val dokka by tasks.getting(DokkaTask::class) {
    outputFormat = "html"
    outputDirectory = "$buildDir/javadoc"

}

val dokkaJavadoc by tasks.creating(DokkaTask::class) {
    outputFormat = "javadoc"
    outputDirectory = "$buildDir/javadoc"
}

val javadocJar by tasks.creating(Jar::class) {
    dependsOn(dokkaJavadoc)
    archiveClassifier.set("javadoc")
    from("$buildDir/javadoc")
}

publishing {
    publications {
        create<MavenPublication>("release") {
            artifactId = "kost"
            version = "1.0.0"

            from(components["java"])
            artifact(sourcesJar)
            artifact(javadocJar)

            pom {
                name.set("Kost")
                description.set("Kotlin JSON-like DSL for building complex hierarchical data structures")
                url.set("https://github.com/vpriscan/Kost")

                licenses {
                    license {
                        name.set("The Apache License, Version 2.0")
                        url.set("http://www.apache.org/licenses/LICENSE-2.0.txt")
                    }
                }
                scm {
                    url.set("https://github.com/vpriscan/Kost.git")
                }
                developers {
                    developer {
                        id.set("vpriscan")
                        name.set("Vedran Prišćan")
                        email.set("priscan.vedran@gmail.com")
                    }
                }
            }
        }
    }
}