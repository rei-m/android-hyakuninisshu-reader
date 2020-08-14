# Add project specific ProGuard rules here.
# You can control the set of applied configuration files using the
# proguardFiles setting in build.gradle.kts.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

# Uncomment this to preserve the line number information for
# debugging stack traces.
#-keepattributes SourceFile,LineNumberTable

# If you keep the line number information, uncomment this to
# hide the original source file name.
#-renamesourcefileattribute SourceFile

-keepnames class net.hyakuninanki.reader.state.training.model.*
-keepnames class net.hyakuninanki.reader.state.question.model.*
-keepnames class net.hyakuninanki.reader.state.material.model.*

-keep class kotlin.reflect.jvm.internal.impl.builtins.BuiltInsLoaderImpl

-keep class kotlin.Metadata {
    public <methods>;
}
-keepclassmembers class net.hyakuninanki.reader.infrastructure.database.karuta.* {
  <init>(...);
  <fields>;
}
-keepclassmembers class net.hyakuninanki.reader.infrastructure.database.question.* {
  <init>(...);
  <fields>;
}
