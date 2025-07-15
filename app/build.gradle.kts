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

import java.io.FileInputStream
import java.util.Properties

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.daggar.hilt)
    alias(libs.plugins.ksp)
    alias(libs.plugins.kapt)
    alias(libs.plugins.google.services)
    alias(libs.plugins.firebase.crashlytics)
    alias(libs.plugins.oss.licenses)
    alias(libs.plugins.ktlint)
}

val keystoreProperties = Properties()

if (rootProject.file("signingConfigs/keystore.properties").exists()) {
    val keystorePropertiesFile = rootProject.file("signingConfigs/keystore.properties")
    keystoreProperties.load(FileInputStream(keystorePropertiesFile))
} else {
    keystoreProperties["keyAlias"] = "dummyKeyAlias"
    keystoreProperties["keyPassword"] = "dummyKeyPassword"
    keystoreProperties["storeFile"] = "README.md"
    keystoreProperties["storePassword"] = "dummyStorePassword"
}

android {
    namespace = "net.hyakuninanki.reader"

    signingConfigs {
        create("releaseConfig") {
            keyAlias = keystoreProperties["keyAlias"] as String
            keyPassword = keystoreProperties["keyPassword"] as String
            storeFile = rootProject.file(keystoreProperties["storeFile"] as String)
            storePassword = keystoreProperties["storePassword"] as String
        }
    }

    compileSdk =
        libs.versions.compileSdk
            .get()
            .toInt()

    defaultConfig {
        applicationId = "net.hyakuninanki.reader"
        minSdk =
            libs.versions.minSdk
                .get()
                .toInt()
        targetSdk =
            libs.versions.targetSdk
                .get()
                .toInt()
        versionCode =
            libs.versions.versionCode
                .get()
                .toInt()
        versionName = libs.versions.versionName.get()
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        debug {
            isDebuggable = true
            applicationIdSuffix = ".debug"
            versionNameSuffix = "d"
            manifestPlaceholders["crashlyticsEnabled"] = false
        }
        release {
            signingConfig = signingConfigs.getByName("releaseConfig")
            isDebuggable = false
            isMinifyEnabled = true
            isShrinkResources = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro",
            )
            manifestPlaceholders["crashlyticsEnabled"] = true
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        dataBinding = true
        buildConfig = true
    }
    testOptions {
        unitTests {
            isIncludeAndroidResources = true
        }
    }
}

dependencies {
    implementation(project(":domain"))
    implementation(project(":infrastructure:database"))
    implementation(project(":infrastructure:storage"))
    implementation(project(":feature:corecomponent"))
    implementation(project(":feature:splash"))
    implementation(project(":feature:question"))
    implementation(project(":feature:trainingmenu"))
    implementation(project(":feature:trainingstarter"))
    implementation(project(":feature:trainingresult"))
    implementation(project(":feature:exammenu"))
    implementation(project(":feature:examstarter"))
    implementation(project(":feature:examresult"))
    implementation(project(":feature:examhistory"))
    implementation(project(":feature:material"))
    implementation(project(":feature:support"))
    implementation(platform(libs.firebase.bom))
    implementation(libs.firebase.crashlytics)
    implementation(libs.firebase.analytics)

    implementation(libs.dagger.hilt.android)
    ksp(libs.dagger.hilt.android.compiler)
    androidTestImplementation(libs.dagger.hilt.android.testing)
    kspAndroidTest(libs.dagger.hilt.android.compiler)

    debugImplementation(libs.leakcanary)
}

ktlint {
    android.set(true)
    outputColorName.set("RED")
}

project.android.applicationVariants.all {
    tasks.findByName("${name}OssLicensesTask")?.doLast {
        addOssLicense(
            project,
            "はんなり明朝",
            """
ライセンスはIPAフォントに準拠します。
https://moji.or.jp/ipafont/license/
""",
        )
        addOssLicense(
            project,
            "百人一首の音声データ",
            """
アプリ内の百人一首の音声データは著作権によって保護されます。著作権は Rei Matsushita に帰属します。
""",
        )
    }
}

fun addOssLicense(
    project: Project,
    libName: String,
    licenseContent: String,
) {
    val lineSeparator = System.getProperty("line.separator").toByteArray(Charsets.UTF_8)

    project.android.applicationVariants.all {
        if (project.file("build/generated/third_party_licenses/$name").exists()) {
            val dependencyOutput = project.file("build/generated/third_party_licenses/$name")

            val resourceOutput = File(dependencyOutput, "res")
            val outputDir = File(resourceOutput, "raw")

            // ライセンスファイル
            val licensesFile = File(outputDir, "third_party_licenses")
            // ライセンスファイルへの書き込み前に現在の位置を保持
            val start = licensesFile.length()

            // ライセンスファイルへ書き込み
            licensesFile.appendText(licenseContent)
            licensesFile.appendBytes(lineSeparator)

            // ライセンスメタデータファイルに書き込み
            val licensesMetadataFile = File(outputDir, "third_party_license_metadata")
            licensesMetadataFile.appendText("$start:${licenseContent.toByteArray(Charsets.UTF_8).size} $libName")
            licensesMetadataFile.appendBytes(lineSeparator)
        }
    }
}
