plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("kotlin-kapt")
}

android {
    namespace = "com.example.apteczka"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.apteczka"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"
    }

    buildFeatures {
        compose = true
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    kotlinOptions {
        jvmTarget = "17"
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.3"
    }
}

kapt {
    javacOptions {
        option("-J--add-exports=jdk.compiler/com.sun.tools.javac.main=ALL-UNNAMED")
    }
}

dependencies {
    val composeVersion = "1.5.3"

    implementation("androidx.core:core-ktx:1.13.1")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.6.1")
    implementation("androidx.activity:activity-compose:1.9.0")

    implementation("androidx.compose.ui:ui:$composeVersion")
    implementation("androidx.compose.material3:material3:1.1.1")
    implementation("androidx.compose.ui:ui-tooling-preview:$composeVersion")
    debugImplementation("androidx.compose.ui:ui-tooling:$composeVersion")

    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.6.1")

    implementation("androidx.room:room-runtime:2.6.1")
    kapt("androidx.room:room-compiler:2.6.1")
    implementation("androidx.room:room-ktx:2.6.1")

    implementation("androidx.work:work-runtime-ktx:2.9.0")

    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.7.3")

    // Compose UI
    implementation ("androidx.compose.ui:ui:1.5.0")
    implementation ("androidx.compose.material3:material3:1.1.0")

    // Compose Tooling (Preview itd.)
    implementation ("androidx.compose.ui:ui-tooling-preview:1.5.0")
    debugImplementation ("androidx.compose.ui:ui-tooling:1.5.0")

    // Compose Navigation
    implementation ("androidx.navigation:navigation-compose:2.7.3")

    // Kotlin Coroutines + Flow
    implementation ("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.7.3")
    implementation ("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.7.3")

    // Lifecycle + ViewModel Compose (jeśli używasz)
    implementation ("androidx.lifecycle:lifecycle-runtime-ktx:2.6.1")
    implementation ("androidx.lifecycle:lifecycle-viewmodel-compose:2.6.1")
    implementation ("androidx.compose.ui:ui:1.6.1")
    implementation ("androidx.compose.material:material:1.6.1")
    implementation ("androidx.compose.material:material-icons-extended:1.6.1")
    implementation ("androidx.navigation:navigation-compose:2.7.5")


}
