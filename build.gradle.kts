import org.jetbrains.kotlin.config.KotlinCompilerVersion
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("maven-publish")
    id("java")
    id("org.jetbrains.kotlin.jvm") version "1.3.72"
    id("org.jetbrains.dokka") version "1.4.10.2"
}

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
    kotlinOptions {
        jvmTarget = "1.8"
    }
}

group "space.d_lowl"
version "0.3.1"

repositories {
    mavenCentral()
    jcenter()
}

dependencies {
    implementation(kotlin("stdlib", KotlinCompilerVersion.VERSION))
    implementation("org.junit.jupiter:junit-jupiter:5.4.2")
    testImplementation("org.junit.jupiter:junit-jupiter:5.7.0")
    implementation("io.github.microutils:kotlin-logging-jvm:2.0.3")
    implementation("ch.qos.logback:logback-classic:1.2.3")
    implementation("ch.qos.logback:logback-core:1.2.3")
}

tasks.withType<Test> {
    useJUnitPlatform()
}

