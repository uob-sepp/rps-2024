/*
 * This file was generated by the Gradle 'init' task.
 *
 * This is a general purpose Gradle build.
 * Learn more about Gradle by exploring our Samples at https://docs.gradle.org/8.10.2/samples
 */

plugins {
  application
}

repositories {
  mavenCentral()
}

dependencies {
  implementation("info.picocli:picocli:4.7.6")
  implementation("io.javalin:javalin:6.3.0")
  implementation("com.fasterxml.jackson.core:jackson-databind:2.17.2")
  implementation("org.hibernate.orm:hibernate-core:6.6.3.Final")

  runtimeOnly("org.postgresql:postgresql:42.6.0")

  testImplementation("org.junit.jupiter:junit-jupiter:5.11.3")
  testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

application {
  mainClass = "Program"
}

tasks.named<Test>("test") {
  useJUnitPlatform()
}
