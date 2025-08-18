plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
}

android {
    namespace = "com.example.transcripto.core.common"
    compileSdk = 34

    defaultConfig {
        minSdk = 24

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
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
}

dependencies {
    implementation("androidx.core:core-ktx:1.10.1")
    implementation("org.jetbrains.kotlin:kotlin-stdlib:2.0.21") {
        attributes {
            attribute(Attribute.of("org.jetbrains.kotlin.platform.type", String::class.java), "androidJvm")
        }
    }
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.7.3") {
        attributes {
            attribute(Attribute.of("org.jetbrains.kotlin.platform.type", String::class.java), "androidJvm")
        }
    }
    
    testImplementation("junit:junit:4.13.2")
}