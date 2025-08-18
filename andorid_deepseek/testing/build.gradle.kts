plugins {
    id("java-library")
    id("org.jetbrains.kotlin.jvm")
}

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

dependencies {
    // Testing
    api("junit:junit:4.13.2")
    api("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.7.3")
    api("io.mockk:mockk:1.13.5")
    
    // Compose testing
    api("androidx.compose.ui:ui-test-junit4:1.5.1")
}