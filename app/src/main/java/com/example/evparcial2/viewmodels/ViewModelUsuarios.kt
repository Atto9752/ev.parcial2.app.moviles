package com.example.evparcial2.viewmodels

// imports
import androidx.lifecycle.*
import com.example.evparcial2.data.model.Usuario
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class ViewModelUsuarios : ViewModel() {
    private val _usuarioActual = MutableStateFlow<Usuario?>(null)
    val usuarioActual: StateFlow<Usuario?> = _usuarioActual.asStateFlow()

    init {
        _usuarioActual.value = Usuario(
            id = 0,
            nombre = "Cliente Demo",
            email = "cliente@demo.com",
            rol = "cliente",
        )
    }

    fun iniciarSesion(usuario: Usuario) {
        _usuarioActual.value = usuario
    }

    fun cerrarSesion() {
        _usuarioActual.value = null
    }
}