plugins {
    id("java-library")
    alias(libs.plugins.jetbrainsKotlinJvm)
}

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

dependencies {
    implementation(libs.kotlinx.coroutines.core)
    implementation(libs.koin.core)
    testImplementation(libs.junit.jupiter)
    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.9.0-RC")
   // testCompileOnly("io.insert-koin:koin-test:3.6.0-wasm-alpha2")
   // testCompileOnly("io.insert-koin:koin-test-junit5:3.6.0-wasm-alpha2")
}

tasks.test {
    useJUnitPlatform()
}