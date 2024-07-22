plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.jetbrainsKotlinAndroid)
    id("kotlin-kapt")
}

android {
    namespace = "com.bignerdranch.android.photogallery"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.bignerdranch.android.photogallery"
        minSdk = 21
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
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

    buildFeatures {
        dataBinding = true
        compose = true
        viewBinding = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.1"
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies {

    implementation ("com.google.code.gson:gson:2.8.5")
    implementation ("com.squareup.retrofit2:converter-gson:2.4.0")

    implementation ("com.squareup.retrofit2:converter-scalars:2.5.0")
    implementation ("com.squareup.retrofit2:retrofit:2.5.0")

    implementation("androidx.appcompat:appcompat:1.1.0-alpha02")
    implementation("androidx.core:core-ktx:1.1.0-alpha04")
    implementation("androidx.lifecycle:lifecycle-extensions:2.0.0")
    implementation("androidx.recyclerview:recyclerview:1.0.0")
    implementation("androidx.activity:activity-compose:1.9.0")


    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}