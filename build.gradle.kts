// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    // Version catalog plugins (from libs.versions.toml)
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin.android) apply false
    alias(libs.plugins.kotlin.compose) apply false

    // Directly declared plugins
    id("com.android.library") version "7.4.2" apply false
    id("com.google.gms.google-services") version "4.3.15" apply false  // Firebase
}

// Optional: Define versions in a central place
extra.apply {
    set("firebaseBomVersion", "32.3.1")
}