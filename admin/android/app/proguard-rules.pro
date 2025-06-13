# Quy tắc cơ bản cho Flutter
-keep class io.flutter.** { *; }
-dontwarn io.flutter.**

# Quy tắc cho com.google.crypto.tink (dùng trong flutter_secure_storage)
-keep class com.google.crypto.tink.** { *; }
-dontwarn com.google.crypto.tink.**

# Quy tắc cho các annotation bị thiếu
-keep class com.google.errorprone.annotations.** { *; }
-dontwarn com.google.errorprone.annotations.**

# Quy tắc cho javax.annotation
-keep class javax.annotation.** { *; }
-dontwarn javax.annotation.**

# Quy tắc cho licensing service
-keep public class com.google.vending.licensing.ILicensingService { void <init>(); *; }
-keep public class com.android.vending.licensing.ILicensingService { void <init>(); *; }
-keep public class com.google.android.vending.licensing.ILicensingService { void <init>(); *; }

# Quy tắc cho androidx
-keep class android.support.annotation.Keep { void <init>(); *; }
-keep class androidx.annotation.Keep { void <init>(); *; }
-keep class androidx.startup.Initializer { void <init>(); *; }
-keep class androidx.versionedparcelable.** { *; }