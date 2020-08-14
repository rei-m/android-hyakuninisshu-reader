plugins {
    id("com.android.application")
    id("kotlin-android")
    id("kotlin-kapt")
    id("dagger.hilt.android.plugin")
    id("com.google.gms.google-services")
    id("com.google.firebase.crashlytics")
    id("com.google.android.gms.oss-licenses-plugin")
}

android {
    if ((File("./signingConfigs/releaseConfig.gradle")).exists()) {
        apply(from = "../signingConfigs/releaseConfig.gradle", to = android)
    } else {
        signingConfigs {
            create("releaseConfig") {// TODO
            }
        }
    }
    val compile_sdk_version: Int by project
    val build_tools_version: String by project
    val min_sdk_version: Int by project
    val target_sdk_version: Int by project
    val version_code: Int by project
    val version_name: String by project

    compileSdkVersion(compile_sdk_version)
    buildToolsVersion(build_tools_version)

    defaultConfig {
        applicationId = "net.hyakuninanki.reader"
        minSdkVersion(min_sdk_version)
        targetSdkVersion(target_sdk_version)
        versionCode = version_code
        versionName = version_name

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        getByName("debug") {
            setDebuggable(true)
            applicationIdSuffix = ".debug"
            versionNameSuffix = "d"
        }
        getByName("release") {
//            signingConfig = signingConfigs.releaseConfig // TODO
            setDebuggable(false)
            setMinifyEnabled(true)
            isShrinkResources = true
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
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
        dataBinding = true
    }
}

apply(from = rootProject.file("gradle/dependencies/dagger.gradle"), to = dependencies)

dependencies {
    val kotlin_version: String by project
    val leakcanary_version: String by project

    implementation(fileTree("dir" to "libs", "include" to listOf("*.jar")))
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk7:$kotlin_version")

    implementation(project(":domain"))

    implementation(project(":infrastructure:database"))
    implementation(project(":infrastructure:storage"))

    implementation(project(":feature:corecomponent"))
    implementation(project(":feature:splash"))
    implementation(project(":feature:trainingmenu"))
    implementation(project(":feature:trainingstarter"))
    implementation(project(":feature:trainingresult"))
    implementation(project(":feature:exammenu"))
    implementation(project(":feature:examstarter"))
    implementation(project(":feature:examresult"))
    implementation(project(":feature:examhistory"))
    implementation(project(":feature:question"))
    implementation(project(":feature:material"))
    implementation(project(":feature:support"))

    implementation("com.github.hotchemi:android-rate:1.0.1")

    debugImplementation("com.squareup.leakcanary:leakcanary-android:$leakcanary_version")

    testImplementation("junit:junit:4.12")
    androidTestImplementation("androidx.test.ext:junit:1.1.1")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.2.0")
}

task("addAppLicenseTask") {
    val LINE_SEPARATOR = System.getProperty("line.separator").toByteArray(Charsets.UTF_8)
    mustRunAfter(tasks.findByName("generateLicenses"))
    doLast {
        val dependencyOutput = File(project.buildDir, "generated/third_party_licenses")
        val resourceOutput = File(dependencyOutput, "/res")
        val outputDir = File(resourceOutput, "/raw")

        val licensesFile = File(outputDir, "third_party_licenses")
        val licensesMetadataFile = File(outputDir, "third_party_license_metadata")

        var start = licensesFile.length()

        val fontLicenseContent = """
ライセンスはIPAフォントに準拠します。

http://ipafont.ipa.go.jp/ipa_font_license_v1.html
"""
        licensesFile.writeText(fontLicenseContent, Charsets.UTF_8)
        licensesFile.writeBytes(LINE_SEPARATOR)

        licensesMetadataFile.writeText("${start}:${fontLicenseContent.toByteArray(Charsets.UTF_8).size} はんなり明朝", Charsets.UTF_8)
        licensesMetadataFile.writeBytes(LINE_SEPARATOR)

        start = licensesFile.length()

        val soundsLicenseContent = """
アプリ内の百人一首の音声データは著作権によって保護されます。著作権は Rei Matsushita に帰属します。
"""
        licensesFile.writeText(soundsLicenseContent, Charsets.UTF_8)
        licensesFile.writeBytes(LINE_SEPARATOR)

        licensesMetadataFile.writeText("${start}:${soundsLicenseContent.toByteArray(Charsets.UTF_8).size} 百人一首の音声データ", Charsets.UTF_8)
        licensesMetadataFile.writeBytes(LINE_SEPARATOR)
    }
}

//// preBuild前にライセンス情報を追加する
tasks.findByPath(":app:preBuild")?.dependsOn("addAppLicenseTask")
