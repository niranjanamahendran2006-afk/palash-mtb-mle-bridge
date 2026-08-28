plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
}

android {
    namespace = "com.palash.mtbmle"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.palash.mtbmle"
        // PROTOTYPE TARGET: real devices are Android 9 (API 28) / ~2GB RAM tablets
        minSdk = 28
        targetSdk = 34
        versionCode = 1
        versionName = "prototype-1.0"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }

    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.14"
    }

    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {
    implementation("androidx.core:core-ktx:1.13.1")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.8.4")
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.8.4")
    implementation("androidx.activity:activity-compose:1.9.1")

    // Compose (BOM keeps versions aligned)
    implementation(platform("androidx.compose:compose-bom:2024.06.00"))
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.ui:ui-graphics")
    implementation("androidx.compose.ui:ui-tooling-preview")
    implementation("androidx.compose.material3:material3")
    implementation("androidx.compose.material:material-icons-extended")
    implementation("androidx.navigation:navigation-compose:2.7.7")

    // Local offline storage for settings / first-launch flag
    implementation("androidx.datastore:datastore-preferences:1.1.1")

    // NOTE (future ML integration — see README):
    // implementation("org.tensorflow:tensorflow-lite:2.16.1")
    // implementation("org.tensorflow:tensorflow-lite-support:0.4.4")
    // These are intentionally NOT added yet — the prototype uses Mock*Engine
    // implementations only, per the roadmap's "no fake ML" rule.

    debugImplementation("androidx.compose.ui:ui-tooling")
    implementation("com.microsoft.onnxruntime:onnxruntime-android:1.18.0")
}
