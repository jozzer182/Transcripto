plugins {
    alias(libs.plugins.kotlin.jvm)
}

dependencies {
    implementation(project(":domain"))
    testImplementation(libs.junit.jupiter.api)
    testRuntimeOnly(libs.junit.jupiter.engine)
    testImplementation(libs.androidx.truth)
    testImplementation(libs.mockk)
}
