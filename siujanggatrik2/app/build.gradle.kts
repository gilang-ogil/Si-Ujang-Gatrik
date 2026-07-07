plugins {
    alias(libs.plugins.android.application)
    // JANGAN tambahkan plugin kotlin-android di sini.
    // AGP versi Anda (9.x, terlihat dari compileSdk { release(36) {...} })
    // sudah punya BUILT-IN KOTLIN SUPPORT secara otomatis.
}

android {
    namespace = "com.example.siujanggatrik"
    compileSdk {
        version = release(36) {
            minorApiLevel = 1
        }
    }

    buildFeatures {
        viewBinding = true
    }

    defaultConfig {
        applicationId = "com.example.siujanggatrik"
        minSdk = 31
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
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
}

dependencies {

    implementation("com.google.code.gson:gson:2.10.1")
    implementation("org.osmdroid:osmdroid-android:6.1.20")

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity.ktx)
    implementation(libs.androidx.constraintlayout)

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}