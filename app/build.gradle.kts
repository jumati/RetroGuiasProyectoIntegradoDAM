plugins {
    id("com.android.application")
    id("com.google.gms.google-services")
}

android {
    namespace = "com.jm.retroguias"
    compileSdk = 33

    defaultConfig {
        applicationId = "com.jm.retroguias"
        minSdk = 29
        targetSdk = 33
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

    implementation("androidx.appcompat:appcompat:1.6.1")
    // La última versión no funciona correctamente. Cambiar a 1.8.0
    implementation("com.google.android.material:material:1.8.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation("com.google.firebase:firebase-database:20.3.0")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")

    // RecyclerView y CardView
    // implementation ("com.android.support:cardview-v7:33.0.0")
    // implementation ("com.android.support:recyclerview-v7:33.0.0")


    // FireBase
    // Import the Firebase BoM
    implementation(platform("com.google.firebase:firebase-bom:32.6.0"))
    // TODO: Add the dependencies for Firebase products you want to use
    // When using the BoM, don't specify versions in Firebase dependencies
    implementation("com.google.firebase:firebase-analytics")
    // Add the dependencies for any other desired Firebase products
    // https://firebase.google.com/docs/android/setup#available-libraries
    // Import the FireBase Authentication
    implementation("com.google.firebase:firebase-auth:22.3.0")
    implementation("com.google.firebase:firebase-firestore:24.9.1")
    // Import the FirebaseDatabase
    implementation("com.google.firebase:firebase-database-ktx")
    implementation ("com.google.firebase:firebase-core:21.1.1")
    // Getting a "Could not find" error? Make sure you have
    // the latest Google Repository in the Android SDK manager
    // FirebaseUI
    implementation("com.firebaseui:firebase-ui-firestore:8.0.1")
}
