// Archivo: build.gradle.kts (El que está en la RAÍZ del proyecto)

plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin.android) apply false
    alias(libs.plugins.kotlin.compose) apply false

    // --- ¡¡ARREGLO DE SINTAXIS!! ---
    // Usamos el ID del plugin y su versión.
    id("com.google.devtools.ksp") version "2.0.21-1.0.22" apply false
}