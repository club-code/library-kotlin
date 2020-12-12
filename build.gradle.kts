plugins {
    java
    kotlin("jvm") version "1.4.21"
    `java-library`
    maven
}

group = "com.github.club-code"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation(platform("org.jetbrains.kotlin:kotlin-bom"))
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")

    implementation("junit", "junit", "4.12")
    api("junit", "junit", "4.12")

    implementation(kotlin("reflect"))
}
