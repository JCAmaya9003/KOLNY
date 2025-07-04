val SUPABASE_URL: String by project
val SUPABASE_KEY: String by project

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.hilt)
    kotlin("kapt")
    id("com.google.gms.google-services")
    //id("com.google.devtools.ksp") version "1.0.21"
    //id("com.google.devtools.ksp") version "2.0.21-1.0.27"
    id("kotlin-kapt")
    id("dagger.hilt.android.plugin")
    kotlin("plugin.serialization")
    //id("com.android.application")
   // id("org.jetbrains.kotlin.plugin.serialization") version "1.9.23"
    //id("com.google.dagger.hilt.android")
}

hilt {
    enableAggregatingTask = false
}

android {
    namespace = "com.pdm_proyecto.kolny"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.pdm_proyecto.kolny"
        minSdk = 24
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        buildConfigField("String", "SUPABASE_URL", "\"$SUPABASE_URL\"")
        buildConfigField("String", "SUPABASE_KEY", "\"$SUPABASE_KEY\"")

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
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
    buildFeatures {
        buildConfig = true
        compose = true
    }
}

dependencies {
    val supabase = "3.2.0-ksp-b1"
    val ktorVersion = "3.0.0-rc-1"
    val hiltVersion = "2.52"

    //implementation("at.favre.lib:bcrypt:0.10.2")
    implementation("org.mindrot:jbcrypt:0.4")

    implementation("com.google.dagger:hilt-android:$hiltVersion")
    kapt("com.google.dagger:hilt-compiler:$hiltVersion")

    /*implementation("io.github.jan-tennert.supabase:supabase-kt-android:3.2.0-ksp-b1")
    implementation("io.github.jan-tennert.supabase:postgrest-kt-android:3.1.4")
    //implementation(platform("io.github.jan-tennert.supabase:bom:3.2.0-ksp-b1"))
    implementation("io.github.jan-tennert.supabase:storage-kt-android:3.2.0-ksp-b1")
    implementation("io.github.jan-tennert.supabase:realtime-kt-android:3.1.4")*/

    implementation("io.github.jan-tennert.supabase:supabase-kt-android:$supabase")

    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.6.3")


    implementation("io.github.jan-tennert.supabase:postgrest-kt-android:$supabase")
    implementation("io.github.jan-tennert.supabase:storage-kt-android:$supabase")
    implementation("io.github.jan-tennert.supabase:realtime-kt-android:$supabase")

    //implementation("org.jetbrains.kotlinx:kotlinx-metadata-jvm:0.6.0")

    implementation("com.google.android.gms:play-services-auth:21.0.0")

    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.6.0")
    implementation("androidx.compose.material:material:1.6.4")
    implementation("androidx.navigation:navigation-compose:2.7.7")
    implementation("androidx.room:room-runtime:2.6.1")
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.7.0")
    implementation("androidx.room:room-ktx:2.6.1")

    //implementation(platform("com.google.firebase:firebase-bom:33.13.0"))
    implementation(platform("com.google.firebase:firebase-bom:33.1.0"))
    implementation("com.google.firebase:firebase-analytics")
    //implementation("com.google.android.gms:play-services-auth:20.7.0")
    implementation("com.google.android.gms:play-services-auth:21.1.1")
    implementation("com.google.firebase:firebase-auth-ktx:22.3.1")

    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.7.3")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-play-services:1.6.3")
    implementation("androidx.compose.material:material-icons-extended:1.6.4")

    kapt("androidx.room:room-compiler:2.6.1")
    implementation("com.google.code.gson:gson:2.10.1")
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.6.2")

    // Hilt
    //implementation("com.google.dagger:hilt-android:2.51")
    //kapt("com.google.dagger:hilt-compiler:2.51")

// Hilt con ViewModel
    implementation("androidx.hilt:hilt-navigation-compose:1.2.0")
    kapt("androidx.hilt:hilt-compiler:1.2.0")

    // ----------- Ktor engine ------------
    implementation(platform("io.ktor:ktor-bom:$ktorVersion"))
    implementation("io.ktor:ktor-client-okhttp")
    //implementation("io.ktor:ktor-client-core:$ktorVersion")

    implementation(libs.hilt.android)
    implementation(libs.androidx.hilt.navigation.compose)
    implementation(libs.javapoet)
    kapt(libs.hilt.compiler)
    implementation ("com.github.yalantis:ucrop:2.2.8")
    implementation("com.github.CanHub:Android-Image-Cropper:4.3.2")
    implementation("androidx.room:room-runtime:2.5.2")
    implementation("androidx.room:room-ktx:2.5.2")
    implementation("androidx.compose.runtime:runtime-livedata:1.5.4")
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:2.6.1")
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.6.1")
    implementation("androidx.compose.material:material:1.5.4")
    implementation("androidx.navigation:navigation-compose:2.7.0")
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.3.2")
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-core:1.3.2")
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    implementation(libs.coil.compose)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
}

configurations.all {
    resolutionStrategy {
        force("com.squareup:javapoet:1.13.0")
        //force("org.jetbrains.kotlinx:kotlinx-metadata-jvm:0.6.0")
        exclude(group = "io.github.jan-tennert.supabase", module = "supabase-kt-android-debug")
        exclude(group = "io.github.jan-tennert.supabase", module = "postgrest-kt-android-debug")
        exclude(group = "io.github.jan-tennert.supabase", module = "storage-kt-android-debug")
        exclude(group = "io.github.jan-tennert.supabase", module = "realtime-kt-android-debug")
    }
}