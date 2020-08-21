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

package dependencies

object Deps {
    object Kotlin {
        private const val version = "1.4.0"
        const val kotlinGradlePlugin = "org.jetbrains.kotlin:kotlin-gradle-plugin:$version"
        const val kotlinStdlibJdk7 = "org.jetbrains.kotlin:kotlin-stdlib-jdk7:$version"
        const val kotlinReflect = "org.jetbrains.kotlin:kotlin-reflect:$version"
    }

    object AndroidX {
        object Arch {
            const val coreTesting = "androidx.arch.core:core-testing:2.1.0"
        }

        object Test {
            private const val version = "1.2.0"
            const val core = "androidx.test:core:$version"
            const val runner = "androidx.test:runner:$version"
            const val rules = "androidx.test:rules:$version"
            const val junit = "androidx.test.ext:junit:1.1.1"

            object Espresso {
                private const val version = "3.2.0"
                const val core = "androidx.test.espresso:espresso-core:${version}"
                const val intents = "androidx.test.espresso:espresso-intents:${version}"
            }
        }
    }

    object Dagger {
        private const val version = "2.28-alpha"
        const val hiltAndroidGradlePlugin = "com.google.dagger:hilt-android-gradle-plugin:$version"
        const val hiltAndroid = "com.google.dagger:hilt-android:$version"
        const val hiltAndroidCompiler = "com.google.dagger:hilt-android-compiler:$version"
        const val hiltAndroidTesting = "com.google.dagger:hilt-android-testing:$version"
    }

    object AssertJ {
        const val core = "org.assertj:assertj-core:3.16.1"
    }

    object Robolectric {
        const val robolectric = "org.robolectric:robolectric:4.3.1"
    }

    object MockitoKotlin2 {
        const val mockitokotlin2 = "com.nhaarman.mockitokotlin2:mockito-kotlin:2.2.0"
    }
}
