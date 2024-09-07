import com.android.build.gradle.internal.cxx.configure.gradleLocalProperties
import java.util.Properties

plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    kotlin("kapt")
    id("com.google.dagger.hilt.android")
    id("org.jetbrains.kotlin.plugin.serialization") version "1.8.10"
    id("com.google.gms.google-services")

    val room_version = "2.6.1"
    id("androidx.room") version "$room_version" apply false
}

val properties = Properties().apply {
    load(project.rootProject.file("local.properties").inputStream())
}

android {
    namespace = "com.company.teacherforboss"
    compileSdk = 34

    defaultConfig {
        buildConfigField("String", "KAKAO_APPKEY", getApiKey("KAKAO_APPKEY"))
        buildConfigField("String", "BASE_URL", properties["base.url"].toString())

        // manifest
        manifestPlaceholders["KAKAO_APPKEY"] = getApiKey("KAKAO_APPKEY")

        applicationId = "com.company.teacherforboss"
        minSdk = 28
        targetSdk = 34
        versionCode = 13
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
                "proguard-rules.pro",
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
        dataBinding = true
        compose = true
        buildConfig = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.4.3"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

tasks.withType(type = org.jetbrains.kotlin.gradle.internal.KaptGenerateStubsTask::class) {
    kotlinOptions.jvmTarget = JavaVersion.VERSION_1_8.toString()
}

fun getApiKey(propertyKey: String): String {
    return gradleLocalProperties(rootDir).getProperty(propertyKey)
}

dependencies {
    implementation("androidx.collection:collection-ktx:1.3.0")
    implementation("androidx.constraintlayout:constraintlayout:2.2.0-alpha07")

    // Google
    implementation("com.google.android.material:material:1.11.0")

    // To use constraintlayout in compose
    implementation("androidx.constraintlayout:constraintlayout-compose:1.1.0-alpha07")

    // DataStore
    implementation("androidx.datastore:datastore-preferences:1.0.0")
    implementation("javax.inject:javax.inject:1")
    implementation("com.google.firebase:firebase-messaging-ktx:23.4.1")
    implementation("androidx.room:room-common:2.6.1")
    implementation("androidx.activity:activity:1.8.0")

    // room db
    val room_version = "2.6.1"

    implementation("androidx.room:room-runtime:$room_version")
    annotationProcessor("androidx.room:room-compiler:$room_version")
    implementation("androidx.room:room-ktx:$room_version")

    // To use Kotlin annotation processing tool (kapt)
    kapt("androidx.room:room-compiler:$room_version")

    // Hilt
    implementation("com.google.dagger:hilt-android:2.50")
    kapt("com.google.dagger:hilt-compiler:2.50")
//    annotationProcessor("com.google.dagger:hilt-android-compiler:2.44")

    // For instrumentation tests
    androidTestImplementation("com.google.dagger:hilt-android-testing:2.50")
    kaptAndroidTest("com.google.dagger:hilt-compiler:2.50")

    // For local unit tests
    testImplementation("com.google.dagger:hilt-android-testing:2.50")
    kaptTest("com.google.dagger:hilt-compiler:2.50")

    // retrofit2
    val retrofit_version = "2.9.0"
    implementation("com.squareup.retrofit2:retrofit:$retrofit_version")
    implementation("com.squareup.retrofit2:converter-gson:$retrofit_version")
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.4.1")
    implementation("org.jetbrains.kotlinx:kotlinx-datetime:0.5.0")
    implementation("com.jakewharton.retrofit:retrofit2-kotlinx-serialization-converter:1.0.0")

    // okhttp3
    implementation(platform("com.squareup.okhttp3:okhttp-bom:4.12.0"))
    implementation("com.squareup.okhttp3:okhttp")
    implementation("com.squareup.okhttp3:logging-interceptor")

    // Gson
    implementation("com.google.code.gson:gson:2.10.1")

    // coroutine
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.8.0-RC2")

    // ViewModel, lifecycle
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.6.1")
    implementation("androidx.collection:collection-ktx:1.3.0")

    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.6.2")
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:2.6.2")

    // navigation
    implementation("androidx.navigation:navigation-runtime-ktx:2.7.6")
    implementation("androidx.navigation:navigation-fragment-ktx:2.7.6")
    implementation("androidx.navigation:navigation-ui-ktx:2.7.6")

    // kakao
    implementation("com.kakao.sdk:v2-all:2.19.0") // 전체 모듈 설치, 2.11.0 버전부터 지원
    implementation("com.kakao.sdk:v2-user:2.19.0") // 카카오 로그인

    // progress Bar
    implementation("com.github.skydoves:progressview:1.1.3")

    // view pager 2
    implementation("androidx.viewpager2:viewpager2:1.0.0")

    // etc

    // event bus
    implementation("org.greenrobot:eventbus:3.3.1")

    // sms
    implementation("com.google.android.gms:play-services-auth-api-phone:18.0.1")

    // naver
    // groovy
    implementation("com.navercorp.nid:oauth:5.9.0") // jdk 11

    // kts
    implementation("com.navercorp.nid:oauth:5.9.0") // jdk 11

    // coil

    implementation("io.coil-kt:coil:1.2.0")
    implementation("io.coil-kt:coil-svg:1.2.0")

    // glide
    implementation("com.github.bumptech.glide:glide:4.16.0")
    implementation("com.github.chrisbanes:PhotoView:2.3.0")


    // firebase
    implementation(platform("com.google.firebase:firebase-bom:33.1.2"))
    implementation("com.google.android.flexbox:flexbox:3.0.0")

    implementation("androidx.core:core-ktx:1.9.0")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.6.2")
    implementation("androidx.activity:activity-compose:1.8.2")
    implementation(platform("androidx.compose:compose-bom:2023.03.00"))
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.ui:ui-graphics")
    implementation("androidx.compose.ui:ui-tooling-preview")
    implementation("androidx.compose.material3:material3")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.10.0")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    androidTestImplementation(platform("androidx.compose:compose-bom:2023.03.00"))
    androidTestImplementation("androidx.compose.ui:ui-test-junit4")
    debugImplementation("androidx.compose.ui:ui-tooling")
    debugImplementation("androidx.compose.ui:ui-test-manifest")
}

kapt {
    correctErrorTypes = true
}
