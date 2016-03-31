# Add project specific ProGuard rules here.
# By default, the flags in this file are appended to flags specified
# in G:\Android\sdk/tools/proguard/proguard-android.txt
# You can edit the include path and order by changing the proguardFiles
# directive in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# Add any project specific keep options here:

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}
-dontpreverify
-dontshrink

-dontwarn org.codehaus.jackson.**
-dontwarn org.apache.http.**
-dontwarn com.paypal.android.**
-dontwarn android.support.**

-repackageclasses 'com.zetagile.customer'
-allowaccessmodification
-optimizations !code/simplification/arithmetic,!field

-keepattributes InnerClasses,EnclosingMethod
-keepattributes Signature
-keepattributes *Annotation*
-renamesourcefileattribute SourceFile
-keepattributes SourceFile,LineNumberTable

-keep public class * extends android.app.Activity
-keep public class * extends android.app.Application
-keep public class * extends android.app.Service
-keep public class * extends android.content.BroadcastReceiver
-keep public class * extends android.content.ContentProvider
-keep public class * extends android.app.backup.BackupAgentHelper
-keep public class * extends android.preference.Preference
-keep public class com.android.vending.licensing.ILicensingService
-dontnote com.android.vending.licensing.ILicensingService

# Preserve the special static methods that are required in all enumeration classes.
-keepclassmembers enum * {
    public static **[] values();
    public static ** valueOf(java.lang.String);
}

-keepclassmembers public class com.zetagile.foodcart.dto.** {*;}

-keep public class org.codehaus.jackson.**{ *; }

-keep public class org.jivesoftware.smack.** { *; }
-keep public class org.jivesoftware.smackx.** { *; }

-keep class *android.support.** { *; }
-keep interface *android.support.** { *; }