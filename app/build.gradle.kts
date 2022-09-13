plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
}

android {
    compileSdk = 32

    defaultConfig {
        applicationId = "com.cc.t820"
        minSdk = 21
        targetSdk = 32
        versionCode = 1
        versionName = "1.0"
       // multiDexEnabled= true
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        val release by getting {
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
        viewBinding = true
    }
}
val withoutStuff = fun ExternalModuleDependency.() {
    exclude(group = "androidx.lifecycle")
}
val withoutStuff1 = fun ExternalModuleDependency.() {
    exclude(group = "androidx.fragment",module="fragment")

}
val withoutStuff2 = fun ExternalModuleDependency.() {
    exclude(group="androidx.lifecycle",module = "lifecycle-runtime")
    exclude(group="androidx.fragment",module = "fragment")
    exclude(group="androidx.core",module= "core")
    exclude(group = "androidx.dynamicanimation",module="dynamicanimation")
}
val withoutStuff3 = fun ExternalModuleDependency.() {
    exclude(group = "com.google.android.material",module=":material")
}
val withoutStuff4 = fun ExternalModuleDependency.() {
    exclude(group = "androidx.activity", module = "activity-ktx")
}
dependencies {

   implementation("androidx.core:core-ktx:1.8.0")

    implementation("androidx.appcompat:appcompat:1.5.0")


    implementation("com.google.android.material:material:1.5.0-alpha04",withoutStuff2)
   implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.5.0")
    implementation("androidx.navigation:navigation-fragment-ktx:2.4.1",withoutStuff4)
    implementation("androidx.navigation:navigation-ui-ktx:2.4.1",withoutStuff3)
    implementation("com.google.code.gson:gson:2.8.8")
    implementation ("androidx.lifecycle:lifecycle-viewmodel-ktx:2.5.0")


    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")

    implementation("androidx.datastore:datastore-preferences:1.0.0")

    implementation("androidx.core:core-splashscreen:1.0.0-beta02")

//    implementation ("androidx.multidex:multidex:2.0.1")
}