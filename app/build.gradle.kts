/*
 * This file was generated by the Gradle 'init' task.
 *
 * This is a general purpose Gradle build.
 * Learn more about Gradle by exploring our Samples at https://docs.gradle.org/8.10.2/samples
 */

plugins {
  application
  jacoco
}

repositories {
  mavenCentral()
}

dependencies {
  implementation("info.picocli:picocli:4.7.6")

  testImplementation("org.junit.jupiter:junit-jupiter:5.10.3")
  testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

application {
  mainClass = "Program"
}

tasks.named<Test>("test") {
  useJUnitPlatform()
}

tasks.jacocoTestCoverageVerification {
  violationRules {
    rule {
      limit {
        minimum = "0.5".toBigDecimal()
      }
    }
  }
}
