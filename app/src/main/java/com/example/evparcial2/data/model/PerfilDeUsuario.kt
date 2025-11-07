package com.example.evparcial2.data.model

import android.net.Uri

data class PerfilDeUsuario(
    val id: Int,
    val nombre: String,
    val email: String,
    val rol: String,
    val imagenUri: Uri? = null
)

