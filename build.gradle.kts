// Top-level build file where you can add configuration options common to all sub-projects/modules.

buildscript {
    val kotlin_version by extra("1.3.72")
    val hilt_version by extra("2.28-alpha")

    repositories {
        google()
        jcenter()

    }
    dependencies {
        classpath("com.android.tools.build:gradle:4.0.1")
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:$kotlin_version")
        classpath("androidx.navigation:navigation-safe-args-gradle-plugin:2.3.0")
        classpath("com.google.dagger:hilt-android-gradle-plugin:$hilt_version")
        classpath("com.google.gms:google-services:4.3.3")
        classpath("com.google.firebase:firebase-crashlytics-gradle:2.2.0")
        classpath("com.google.android.gms:oss-licenses-plugin:0.10.2")

        // NOTE: Do not place your application dependencies here; they belong
        // in the individual module build.gradle files
    }
}

allprojects {
    repositories {
        google()
        jcenter()
    }
}

ext {
    val compile_sdk_version by extra(29)
    val build_tools_version by extra("29.0.2")
    val min_sdk_version by extra(21)
    val target_sdk_version by extra(29)
    val version_code by extra(4)
    val version_name by extra("1.0.3")

    val androidx_appcompat_version by extra("1.1.0")
    val androidx_core_ktx_version by extra("1.3.0")
    val androidx_fragment_ktx_version by extra("1.2.5")
    val androidx_annotation_version by extra("1.1.0")
    val androidx_navigation_version by extra("2.3.0")
    val androidx_lifecycle_version by extra("2.2.0")
    val androidx_constraintlayout_version by extra("2.0.0-beta8")
    val androidx_room_version by extra("2.2.5")
    val androidx_viewpager2_version by extra("1.0.0")
    val google_material_version by extra("1.1.0")
    val coroutines_version by extra("1.3.7")
    val glide_version by extra("4.11.0")
    val rxjava_version by extra("2.2.19")
    val rxandroid_version by extra("2.1.1")

    val firebase_analytics_version by extra("17.4.4")
    val firebase_crashlytics_version by extra("17.1.1")
    val firebase_ads_version by extra("19.2.0")

    val crashlytics_version by extra("2.10.1")
    val leakcanary_version by extra("2.3")
    val timber_version by extra("4.7.1")
    val moshi_version by extra("1.9.2")
}

task<Delete>("clean") {
    delete(rootProject.buildDir)
}
