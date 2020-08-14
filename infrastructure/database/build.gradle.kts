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
}

apply(from = rootProject.file("gradle/android.gradle"))

android {
    packagingOptions {
        exclude("META-INF/atomicfu.kotlin_module")
    }
}

apply(from = rootProject.file("gradle/dependencies/dagger.gradle"), to = dependencies)
apply(from = rootProject.file("gradle/dependencies/test.gradle"), to = dependencies)

dependencies {
    val kotlin_version: String by project
    val androidx_annotation_version: String by project
    val androidx_lifecycle_version: String by project
    val androidx_room_version: String by project
    val moshi_version: String by project

    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk7:$kotlin_version")
    implementation("org.jetbrains.kotlin:kotlin-reflect:$kotlin_version")

    implementation(project(":domain"))
    implementation(project(":infrastructure:storage"))

    implementation("androidx.annotation:annotation:$androidx_annotation_version")
    api("androidx.room:room-runtime:$androidx_room_version")
    api("androidx.room:room-ktx:$androidx_room_version")
    kapt("androidx.room:room-compiler:$androidx_room_version")
    androidTestImplementation("androidx.room:room-testing:$androidx_room_version")

    implementation("androidx.lifecycle:lifecycle-extensions:$androidx_lifecycle_version")
    implementation("androidx.lifecycle:lifecycle-common-java8:$androidx_lifecycle_version")
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:$androidx_lifecycle_version")

    implementation("com.squareup.moshi:moshi:$moshi_version")
    implementation("com.squareup.moshi:moshi-kotlin:$moshi_version")
}
