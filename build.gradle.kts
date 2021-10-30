repositories {
  mavenCentral()
  maven("https://jitpack.io")
}
plugins {
  `java-library`
  `maven-publish`
  kotlin("jvm") version "1.5.31"
}
dependencies {
  api("com.github.demidko:aot:2021.10.28")
  api("com.github.demidko:tokenizer:2021.10.20")
  testImplementation("org.junit.jupiter:junit-jupiter:5.8.1")
  testImplementation("com.willowtreeapps.assertk:assertk-jvm:0.25")
  testImplementation("io.mockk:mockk:1.12.0")
}
tasks.test {
  minHeapSize = "1024m"
  maxHeapSize = "2048m"
  useJUnitPlatform()
}
publishing {
  publications {
    create<MavenPublication>("chisla") {
      from(components["java"])
    }
  }
}
