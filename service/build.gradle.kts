plugins {
    kotlin("jvm")
    id("maven-publish")
}

java {
    sourceCompatibility = JavaVersion.VERSION_1_7
    targetCompatibility = JavaVersion.VERSION_1_7
}


//
//publishing {
//    publications {
//        servicePublish(MavenPublication) {
//            groupId = "com.looptry.service"
//            artifactId = "demo"
//            version = "1.0.0"
//
////            from components . java
//        }
//    }
//}

dependencies {
    implementation( "androidx.collection:collection:1.1.0")
}