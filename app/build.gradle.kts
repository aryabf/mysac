plugins {
    alias(libs.plugins.androidApplication)
}

android {
    namespace = "com.finalproject.mysac"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.finalproject.mysac"
        minSdk = 26
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
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
}

dependencies {

    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)

    // Navigation
    implementation(libs.navigation.fragment.ktx)
    implementation(libs.navigation.ui.ktx)

    // Circle Image View
    implementation(libs.circleimageview)

    // Glide
    implementation(libs.glide)

    // Splash Screen
    implementation(libs.core.splashscreen)

    // Retrofit
    implementation(libs.retrofit)
    implementation(libs.converter.gson)
    implementation(libs.logging.interceptor)

    // Datastore
    implementation(libs.datastore.preferences)
    implementation("androidx.datastore:datastore-rxjava3:1.1.1")
    implementation(libs.datastore.preferences.rxjava3)

    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
}