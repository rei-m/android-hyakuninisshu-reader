/*
 * Copyright (c) 2020. Rei Matsushita.
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
    id("com.android.library")
    id("kotlin-android")
    id("kotlin-kapt")
    id("androidx.navigation.safeargs.kotlin")
}

apply(from = rootProject.file("gradle/android.gradle"))
apply(from = rootProject.file("gradle/android-feature.gradle"))

apply(from = rootProject.file("gradle/dependencies/dagger.gradle"), to = dependencies)
apply(from = rootProject.file("gradle/dependencies/test.gradle"), to = dependencies)

dependencies {
    val kotlin_version: String by project
    val coroutines_version: String by project
    val google_material_version: String by project
    val firebase_analytics_version: String by project
    val firebase_crashlytics_version: String by project
    val firebase_ads_version: String by project
    val androidx_appcompat_version: String by project
    val androidx_core_ktx_version: String by project
    val androidx_fragment_ktx_version: String by project
    val androidx_annotation_version: String by project
    val androidx_constraintlayout_version: String by project
    val androidx_lifecycle_version: String by project
    val androidx_navigation_version: String by project
    val glide_version: String by project
    val rxjava_version: String by project
    val rxandroid_version: String by project
    val timber_version: String by project

    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk7:$kotlin_version")

    api(project(":state"))

    api("org.jetbrains.kotlinx:kotlinx-coroutines-core:$coroutines_version")
    api("org.jetbrains.kotlinx:kotlinx-coroutines-android:$coroutines_version")

    api("com.google.android.material:material:$google_material_version")

    api("com.google.firebase:firebase-analytics:$firebase_analytics_version")
    api("com.google.firebase:firebase-crashlytics:$firebase_crashlytics_version")
    api("com.google.android.gms:play-services-ads:$firebase_ads_version")

    api("androidx.appcompat:appcompat:$androidx_appcompat_version")
    api("androidx.core:core-ktx:$androidx_core_ktx_version")
    api("androidx.fragment:fragment-ktx:$androidx_fragment_ktx_version")
    api("androidx.annotation:annotation:$androidx_annotation_version")
    api("androidx.constraintlayout:constraintlayout:$androidx_constraintlayout_version")
    api("androidx.lifecycle:lifecycle-viewmodel-savedstate:$androidx_lifecycle_version")
    api("androidx.lifecycle:lifecycle-viewmodel-ktx:$androidx_lifecycle_version")
    api("androidx.lifecycle:lifecycle-extensions:$androidx_lifecycle_version")
    api("androidx.lifecycle:lifecycle-reactivestreams:$androidx_lifecycle_version")
    api("androidx.navigation:navigation-fragment:$androidx_navigation_version")
    api("androidx.navigation:navigation-ui:$androidx_navigation_version")
    api("androidx.navigation:navigation-fragment-ktx:$androidx_navigation_version")
    api("androidx.navigation:navigation-ui-ktx:$androidx_navigation_version")

    api("com.github.bumptech.glide:glide:$glide_version")
    kapt("com.github.bumptech.glide:compiler:$glide_version")

    api("io.reactivex.rxjava2:rxjava:$rxjava_version")
    api("io.reactivex.rxjava2:rxandroid:$rxandroid_version")
    api("com.jakewharton.timber:timber:$timber_version")
}
