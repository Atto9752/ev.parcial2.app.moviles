// Archivo: build.gradle.kts (El que está en la RAÍZ del proyecto)

plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin.android) apply false
    alias(libs.plugins.kotlin.compose) apply false // <-- Aseguramos que esté

    // --- ¡¡LÍNEA NUEVA Y CLAVE!! ---
    // Definimos el plugin KSP aquí para todo el proyecto
    id("com.google.devtools.ksp") version "1.9.21-1.0.16" apply false
}