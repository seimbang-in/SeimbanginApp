plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    id("kotlin-parcelize")
    id("androidx.navigation.safeargs.kotlin")
    id("com.google.android.libraries.mapsplatform.secrets-gradle-plugin")
    id("com.google.devtools.ksp")
}

android {
    namespace = "com.aeryz.seimbanginapp"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.aeryz.seimbanginapp"
        minSdk = 24
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
    flavorDimensions += "env"
    productFlavors {
        create("production") {
            buildConfigField("String", "BASE_URl", "\"https://seimbangin.vercel.app/\"")
        }
        create("integration") {
            buildConfigField("String", "BASE_URl", "\"https://seimbangin.vercel.app/\"")
        }
        create("mock") {
            buildConfigField("String", "BASE_URl", "\"https://seimbangin.vercel.app/\"")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
    buildFeatures {
        viewBinding = true
        buildConfig = true
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.androidx.lifecycle.livedata.ktx)
    implementation(libs.androidx.lifecycle.viewmodel.ktx)
    implementation(libs.androidx.navigation.fragment.ktx)
    implementation(libs.androidx.navigation.ui.ktx)
    implementation(libs.androidx.activity)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    // retrofit & okhttp
    implementation(libs.retrofit)
    implementation(libs.converter.gson)
    implementation(libs.okhttp)
    // chucker interceptor
    debugImplementation(libs.library)
    releaseImplementation(libs.library.no.op)
    // Koin for Android
    implementation(libs.koin.android)
    // data store
    implementation(libs.androidx.datastore.preferences)
    // fragment ktx
    implementation(libs.androidx.fragment.ktx)
    // coroutine
    implementation(libs.kotlinx.coroutines.core)
    implementation(libs.kotlinx.coroutines.android)
    // Coil image loader
    implementation(libs.coil)
    // FancyToast
    implementation(libs.fancytoast)
    //uCrop
    implementation (libs.ucrop)
    // Google AI client SDK for Android
    implementation(libs.generativeai)
    // room database
    implementation(libs.androidx.room.ktx)
    implementation(libs.androidx.room.runtime)
    ksp(libs.room.compiler)
    // viewpager2
    implementation("androidx.viewpager2:viewpager2:1.0.0")
    // MPAndroid Chart
    implementation("com.github.PhilJay:MPAndroidChart:v3.1.0")
    // Android Image Cropper
    implementation("com.vanniktech:android-image-cropper:4.6.0")
}