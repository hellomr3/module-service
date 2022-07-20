plugins {
//    id("maven-publish")
    id("com.google.devtools.ksp")
    kotlin("jvm")
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
    implementation(project(":annotation"))
    implementation(project(":compiler"))
    ksp(project(":compiler"))

    implementation( "androidx.collection:collection:1.1.0")
}