plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.jetbrainsKotlinAndroid)
    alias(libs.plugins.devtoolsKsp)
}

android {
    namespace = "com.example.lab1"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.lab1"
        minSdk = 28
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
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
        kotlinCompilerExtensionVersion = "1.5.1"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    buildToolsVersion = "34.0.0"
}
configurations {
    create("cleanedAnnotations")
    implementation{
        exclude(group = "org.jetbrains", module = "kotlinx")
    }
    implementation{
        exclude(group = "org.jetbrains", module = "annotations")
    }
}
configurations.implementation{
    exclude( group = "org.jetbrains.kotlinx", module = "kotlinx-coroutines-core-jvm")
}
configurations.implementation{
    exclude(group = "org.jetbrains.kotlin", module = "kotlin-stdlib")
}
configurations.implementation{
    exclude(group = "com.google.guava", module = "listenablefuture")
}
dependencies {
        implementation(libs.androidx.core.ktx)
        implementation(libs.androidx.lifecycle.runtime.ktx)
        implementation(libs.androidx.activity.compose)
        implementation(platform(libs.androidx.compose.bom))
        implementation(libs.androidx.ui)
        implementation(libs.androidx.ui.graphics)
        implementation(libs.androidx.ui.tooling.preview)
        implementation(libs.androidx.foundation.android)
        implementation(libs.androidx.material3.android)
        implementation(libs.androidx.lifecycle.viewmodel.compose.v270)
        implementation("androidx.lifecycle:lifecycle-livedata-ktx:2.8.0")
        implementation("androidx.room:room-runtime:2.6.1")
        implementation("androidx.room:room-ktx:2.6.1")
        implementation(libs.volley)
        implementation(libs.compose.preview.renderer)
        ksp("androidx.room:room-compiler:2.6.1")
        testImplementation(libs.junit)
        androidTestImplementation(libs.androidx.junit)
        androidTestImplementation(libs.androidx.espresso.core)
        androidTestImplementation(platform(libs.androidx.compose.bom))
        androidTestImplementation(libs.androidx.ui.test.junit4)
        debugImplementation(libs.androidx.ui.tooling)
        debugImplementation(libs.androidx.ui.test.manifest)
        implementation("androidx.slidingpanelayout:slidingpanelayout:1.2.0")
        implementation("com.github.bumptech.glide:glide:4.16.0")
        //annotationProcessor("com.github.bumptech.glide:compiler:4.11.0")
        //implementation("com.github.bumptech.glide:compose:1.0.0-beta01")
        //implementation(platform("org.jetbrains.kotlin:kotlin-bom:1.9.24"))
        //implementation("io.coil-kt:coil-compose:2.4.0")
        //implementation("io.coil-kt:coil-compose:2.4.0")
}