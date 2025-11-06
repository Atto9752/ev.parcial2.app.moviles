
// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin.android) apply false
    alias(libs.plugins.kotlin.compose) apply false
    implementation("io.coil-kt:coil-compose:2.5.0")
    implementation "androidx.lifecycle:lifecycle-viewmodel-compose:2.6.2"
}