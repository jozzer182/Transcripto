// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.android.library) apply false
    alias(libs.plugins.kotlin.android) apply false
    alias(libs.plugins.kotlin.jvm) apply false
    alias(libs.plugins.kotlin.compose) apply false
    alias(libs.plugins.kotlin.kapt) apply false
    alias(libs.plugins.hilt.android) apply false
    alias(libs.plugins.detekt)
    alias(libs.plugins.ktlint)
}

subprojects {
    apply(plugin = "io.gitlab.arturbosch.detekt")
    apply(plugin = "org.jlleitschuh.gradle.ktlint")
    detekt {
        buildUponDefaultConfig = true
        allRules = false
        config = files("${rootDir}/detekt.yml")
        baseline = file("${rootDir}/detekt-baseline.xml")
    }
    ktlint {
        android.set(true)
        ignoreFailures.set(false)
    }
}