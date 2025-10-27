package com.example.evparcial2.domain.viewmodels

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
        // Usuario demo por defecto (para testing)
        _usuarioActual.value = Usuario(
            nombre = "Cliente Demo",
            email = "cliente@demo.com",
            rol = "cliente"
        )
    }

    fun iniciarSesion(usuario: Usuario) {
        _usuarioActual.value = usuario
    }

    fun cerrarSesion() {
        _usuarioActual.value = null
    }
}