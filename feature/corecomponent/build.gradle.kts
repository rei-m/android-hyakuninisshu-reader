/*
 * Copyright (c) 2025. Rei Matsushita.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.ksp)
    alias(libs.plugins.kapt)
    alias(libs.plugins.navigation.safeargs)
    alias(libs.plugins.ktlint)
}

android {
    namespace = "net.hyakuninanki.reader.feature.corecomponent"

    compileSdk =
        libs.versions.compileSdk
            .get()
            .toInt()

    defaultConfig {
        minSdk =
            libs.versions.minSdk
                .get()
                .toInt()

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
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
    buildFeatures {
        dataBinding = true
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
}

dependencies {
    api(project(":state"))
    api(libs.material)
    api(platform(libs.firebase.bom))
    api(libs.firebase.crashlytics)
    api(libs.firebase.analytics)
    api(libs.firebase.ads)

    api(libs.androidx.annotation)
    api(libs.androidx.appcompat)
    api(libs.androidx.core.ktx)
    api(libs.androidx.fragment.ktx)
    api(libs.androidx.constraintlayout)
    api(libs.androidx.lifecycle.viewmodel.savedstate)
    api(libs.androidx.lifecycle.viewmodel.ktx)
    api(libs.androidx.lifecycle.reactivestreams)
    api(libs.androidx.navigation.fragment)
    api(libs.androidx.navigation.ui)
    api(libs.androidx.navigation.fragment.ktx)
    api(libs.androidx.navigation.ui.ktx)
    api(libs.androidx.viewpager2)

    api(libs.glide)
    ksp(libs.glide.compiler)

    api(libs.rxjava)
    api(libs.rxandroid)

    api(libs.timber)

    implementation(libs.dagger.hilt.android)
    ksp(libs.dagger.hilt.android.compiler)
}
