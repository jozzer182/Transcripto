plugins {
    alias(libs.plugins.kotlin.jvm)
}

dependencies {
    testImplementation(libs.truth)
    testImplementation(libs.junit.jupiter)
}

tasks.test {
    useJUnitPlatform()
}
