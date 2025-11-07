pluginManagement {
    repositories {
        // --- ¡¡ARREGLO!! ---
        // Quitamos las restricciones "content { ... }"
        // y simplemente le damos acceso completo a los 3 repositorios.
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}

// (El resto de tu archivo estaba perfecto)
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "Ev Parcial 2"
include(":app")