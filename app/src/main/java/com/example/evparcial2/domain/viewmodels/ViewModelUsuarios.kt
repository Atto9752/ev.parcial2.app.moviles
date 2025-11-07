package com.example.evparcial2.domain.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.evparcial2.data.model.Usuario // Asegúrate que la ruta a Usuario sea correcta
import com.example.evparcial2.util.Validadores
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

// --- ESTADO PARA EL LOGIN ---
data class UiStateLogin(
    val email: String = "",
    val pass: String = "",
    val emailError: String? = null,
    val passError: String? = null
)

// --- ESTADO NUEVO PARA EL REGISTRO ---
data class UiStateRegistro(
    val nombre: String = "",
    val email: String = "",
    val pass: String = "",
    val passConfirm: String = "",
    val nombreError: String? = null,
    val emailError: String? = null,
    val passError: String? = null,
    val passConfirmError: String? = null
)

// --- EVENTOS PARA EL LOGIN ---
sealed class EventoLogin {
    data class OnEmailChange(val email: String) : EventoLogin()
    data class OnPassChange(val pass: String) : EventoLogin()
    object OnLoginClicked : EventoLogin()
}

// --- EVENTOS NUEVOS PARA EL REGISTRO ---
sealed class EventoRegistro {
    data class OnNombreChange(val nombre: String) : EventoRegistro()
    data class OnEmailChange(val email: String) : EventoRegistro()
    data class OnPassChange(val pass: String) : EventoRegistro()
    data class OnPassConfirmChange(val pass: String) : EventoRegistro() // La variable se llama 'pass'
    object OnRegistroClicked : EventoRegistro()
}

// --- EVENTOS DE NAVEGACIÓN (Añadimos NavegarALogin) ---
sealed interface EventoDeNavegacion {
    object NavegarAInicio : EventoDeNavegacion
    object NavegarALogin : EventoDeNavegacion
}

class ViewModelUsuarios : ViewModel() {

    // --- NUESTRA "BASE DE DATOS" FALSA ---
    private val _listaUsuarios = MutableStateFlow<List<Usuario>>(
        listOf(
            Usuario(
                nombre = "Admin",
                email = "admin@demo.com",
                rol = "admin",
                pass = "admin123"
            ),
            Usuario(
                nombre = "Cliente",
                email = "cliente@demo.com",
                rol = "cliente",
                pass = "cliente123"
            )
        )
    )

    // --- ESTADOS ---
    private val _usuarioActual = MutableStateFlow<Usuario?>(null)
    val usuarioActual: StateFlow<Usuario?> = _usuarioActual.asStateFlow()

    private val _uiStateLogin = MutableStateFlow(UiStateLogin())
    val uiState: StateFlow<UiStateLogin> = _uiStateLogin.asStateFlow()

    private val _uiStateRegistro = MutableStateFlow(UiStateRegistro())
    val uiStateRegistro: StateFlow<UiStateRegistro> = _uiStateRegistro.asStateFlow()

    // --- CANAL DE NAVEGACIÓN (Sin cambios) ---
    private val _eventoNavegacion = MutableSharedFlow<EventoDeNavegacion>()
    val eventoNavegacion = _eventoNavegacion.asSharedFlow()

    // --- MANEJADOR DE EVENTOS LOGIN (Sin cambios) ---
    fun onLoginEvent(evento: EventoLogin) {
        when (evento) {
            is EventoLogin.OnEmailChange -> {
                _uiStateLogin.update { it.copy(email = evento.email) }
            }
            is EventoLogin.OnPassChange -> {
                _uiStateLogin.update { it.copy(pass = evento.pass) }
            }
            is EventoLogin.OnLoginClicked -> {
                validarYLoguear()
            }
        }
    }

    // --- MANEJADOR DE EVENTOS REGISTRO (CORREGIDO) ---
    fun onRegistroEvent(evento: EventoRegistro) {
        when (evento) {
            is EventoRegistro.OnNombreChange -> {
                _uiStateRegistro.update { it.copy(nombre = evento.nombre) }
            }
            is EventoRegistro.OnEmailChange -> {
                _uiStateRegistro.update { it.copy(email = evento.email) }
            }
            is EventoRegistro.OnPassChange -> {
                _uiStateRegistro.update { it.copy(pass = evento.pass) }
            }
            is EventoRegistro.OnPassConfirmChange -> {
                // --- ¡¡AQUÍ ESTÁ LA CORRECCIÓN!! ---
                _uiStateRegistro.update { it.copy(passConfirm = evento.pass) } // antes decía evento.passConfirm
            }
            is EventoRegistro.OnRegistroClicked -> {
                validarYRegistrar()
            }
        }
    }

    // --- LÓGICA DE LOGIN (MODIFICADA) ---
    private fun validarYLoguear() {
        _uiStateLogin.update { it.copy(emailError = null, passError = null) }

        val email = _uiStateLogin.value.email
        val pass = _uiStateLogin.value.pass

        val errorEmail = Validadores.validarEmail(email)
        val errorPass = if (pass.isBlank()) "La contraseña es obligatoria" else null

        if (errorEmail != null || errorPass != null) {
            _uiStateLogin.update { it.copy(emailError = errorEmail, passError = errorPass) }
            return
        }

        viewModelScope.launch {
            val usuarioEncontrado = _listaUsuarios.value.find { it.email == email }

            if (usuarioEncontrado != null && usuarioEncontrado.pass == pass) {
                _usuarioActual.value = usuarioEncontrado
                _eventoNavegacion.emit(EventoDeNavegacion.NavegarAInicio)
            } else {
                _uiStateLogin.update { it.copy(passError = "Email o contraseña incorrectos") }
            }
        }
    }

    // --- LÓGICA DE REGISTRO (NUEVA) ---
    private fun validarYRegistrar() {
        _uiStateRegistro.update { it.copy(
            nombreError = null,
            emailError = null,
            passError = null,
            passConfirmError = null
        )}

        val state = _uiStateRegistro.value
        val nombre = state.nombre
        val email = state.email
        val pass = state.pass
        val passConfirm = state.passConfirm

        val errorNombre = if (nombre.isBlank()) "El nombre es obligatorio" else null
        val errorEmail = Validadores.validarEmail(email)
        val errorPass = if (pass.length < 6) "La contraseña debe tener al menos 6 caracteres" else null
        val errorPassConfirm = if (pass != passConfirm) "Las contraseñas no coinciden" else null
        val errorEmailExistente = if (_listaUsuarios.value.any { it.email == email }) "El email ya está registrado" else null

        if (errorNombre != null || errorEmail != null || errorPass != null || errorPassConfirm != null || errorEmailExistente != null) {
            _uiStateRegistro.update { it.copy(
                nombreError = errorNombre,
                emailError = errorEmail ?: errorEmailExistente,
                passError = errorPass,
                passConfirmError = errorPassConfirm
            )}
            return
        }

        viewModelScope.launch {
            val nuevoUsuario = Usuario(
                nombre = nombre,
                email = email,
                pass = pass,
                rol = "cliente"
            )

            _listaUsuarios.update { listaActual -> listaActual + nuevoUsuario }

            _uiStateRegistro.value = UiStateRegistro()

            _eventoNavegacion.emit(EventoDeNavegacion.NavegarALogin)
        }
    }

    // --- CERRAR SESIÓN (Sin cambios) ---
    fun cerrarSesion() {
        _usuarioActual.value = null
    }
}