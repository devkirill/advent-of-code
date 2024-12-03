import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
	kotlin("jvm") version "1.8.22"
}

group = "advent.calendar"
version = "0.0.1-SNAPSHOT"

java {
	sourceCompatibility = JavaVersion.VERSION_17
}

repositories {
	mavenCentral()
}

dependencies {
	implementation("org.jetbrains.kotlin:kotlin-reflect")
	implementation("com.github.doyaaaaaken:kotlin-csv-jvm:1.9.1")
	implementation("org.reflections:reflections:0.10.2")
	implementation("org.jsoup:jsoup:1.17.1")
	implementation("org.jetbrains.kotlinx:multik-core:0.2.2")
	implementation("org.jetbrains.kotlinx:multik-default:0.2.2")
	implementation("gov.nist.math:jama:1.0.3")
}

tasks.withType<KotlinCompile> {
	kotlinOptions {
		freeCompilerArgs += "-Xjsr305=strict"
		jvmTarget = "17"
	}
}

tasks.withType<Test> {
	useJUnitPlatform()
}
