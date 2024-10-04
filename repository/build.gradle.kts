plugins {
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.jetbrainsKotlinAndroid)
    id("com.google.devtools.ksp") version "2.0.20-1.0.24"
    id("org.jetbrains.kotlin.plugin.serialization") version "2.0.10"
    id("de.jensklingenberg.ktorfit") version "2.0.1"
}

ktorfit {

}

android {
    namespace = "ru.lim1x.repository"
    compileSdk = 34

    defaultConfig {
        minSdk = 26

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }

}
val ktorfit = "2.0.0"
val ktor = "2.3.12"

dependencies {


    implementation(libs.kotlinx.coroutines.core)
    implementation(libs.koin.core)
    implementation(project(":domain"))
    testImplementation(libs.junit.jupiter)
    testImplementation(libs.kotlinx.coroutines.test)

    implementation("de.jensklingenberg.ktorfit:ktorfit-lib:$ktorfit")
    implementation("io.ktor:ktor-client-serialization:$ktor")
    implementation("io.ktor:ktor-client-content-negotiation:$ktor")
    implementation("io.ktor:ktor-serialization-kotlinx-json:$ktor")
    implementation("de.jensklingenberg.ktorfit:ktorfit-converters-response:$ktorfit")
    implementation("de.jensklingenberg.ktorfit:ktorfit-converters-call:$ktorfit")
    implementation("de.jensklingenberg.ktorfit:ktorfit-converters-flow:$ktorfit")
    implementation("io.ktor:ktor-client-logging:2.3.0") // Логирование


}

tasks.withType<Test> {
    useJUnitPlatform()
}
