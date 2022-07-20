plugins {
    `maven-publish`
    kotlin("jvm")
}

publishing {
    publications {
        create<MavenPublication>("maven") {
            groupId = "com.looptry.service"
            artifactId = "demo"
            version = "1.0.0"
            from(components["java"])
        }
    }
}

dependencies {
    implementation("androidx.collection:collection:1.1.0")
}