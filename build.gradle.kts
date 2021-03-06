import org.jetbrains.kotlin.config.KotlinCompilerVersion
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

val compileKotlin: KotlinCompile by tasks
compileKotlin.kotlinOptions.useIR = true

plugins {
    id("maven-publish")
    id("java")
    id("org.jetbrains.kotlin.jvm") version "1.3.72"
    id("org.jetbrains.dokka") version "1.4.10.2"
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        jvmTarget = "1.8"
    }
}

group "space.d_lowl"
version "0.3.1"

repositories {
    maven(url = "https://dl.bintray.com/kyonifer/maven")
    mavenCentral()
    jcenter()
}

dependencies {
    implementation(kotlin("stdlib", KotlinCompilerVersion.getVersion()))
    implementation("org.junit.jupiter:junit-jupiter:5.4.2")
    testImplementation("org.junit.jupiter:junit-jupiter:5.7.0")
    implementation("io.github.microutils:kotlin-logging-jvm:2.0.3")
    implementation("ch.qos.logback:logback-classic:1.2.3")
    implementation("ch.qos.logback:logback-core:1.2.3")
    implementation(group = "com.kyonifer", name = "koma-core-ejml", version = "0.12")
    implementation(group = "com.kyonifer", name = "koma-plotting", version = "0.12")
}

tasks.withType<Test> {
    useJUnitPlatform()
}

