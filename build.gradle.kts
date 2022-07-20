// Top-level build file where you can add configuration options common to all sub-projects/modules.

buildscript {
    dependencies {
        classpath(kotlin("gradle-plugin", version = "1.6.10"))
    }
}

plugins {
    id("com.android.application") version "7.2.1" apply false
    id("com.android.library") version "7.2.1" apply false
    id("com.google.devtools.ksp") version "1.6.10-1.0.3" apply false
    kotlin("jvm") version "1.6.10" apply false
}

tasks.register("clean", Delete::class.java) {
    delete(rootProject.buildDir)
}