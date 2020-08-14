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
}

apply(from = rootProject.file("gradle/android.gradle"))

apply(from = rootProject.file("gradle/dependencies/test.gradle"), to = dependencies)

dependencies {
    val kotlin_version: String by project
    val androidx_annotation_version: String by project
    val androidx_lifecycle_version: String by project

    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk7:$kotlin_version")
    implementation("androidx.annotation:annotation:$androidx_annotation_version")
    implementation("androidx.lifecycle:lifecycle-extensions:$androidx_lifecycle_version")
    implementation("androidx.lifecycle:lifecycle-common-java8:$androidx_lifecycle_version")
}
