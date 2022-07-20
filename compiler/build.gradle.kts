plugins {
    id("com.google.devtools.ksp")
    kotlin("jvm")
}

sourceSets.main {
    java.srcDirs("src/main/kotlin")
}

dependencies {
    implementation(project(":annotation"))

    implementation("com.squareup:kotlinpoet:1.10.2")
    implementation("com.squareup:kotlinpoet-ksp:1.10.2")
    implementation(kotlin("stdlib"))
    implementation("com.google.devtools.ksp:symbol-processing-api:1.6.10-1.0.3")

    // 生成META-INF 其实你可以自定义一个META-INF文件 就不需要ksp或者apt来生成了
    implementation("com.google.auto.service:auto-service-annotations:1.0")
    ksp("dev.zacsweers.autoservice:auto-service-ksp:0.5.2")
}


sourceSets.main {
    java.srcDirs("src/main/kotlin")
}