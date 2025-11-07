package com.example.evparcial2.ui.pantallas

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.flow.collectLatest
import com.example.evparcial2.domain.viewmodels.EventoLogin
import com.example.evparcial2.domain.viewmodels.ViewModelUsuarios
import com.example.evparcial2.ui.components.common.CampoTexto
import com.example.evparcial2.domain.viewmodels.EventoDeNavegacion

@Composable
fun PantallaLogin(
    vm: ViewModelUsuarios,
    onLoginExitoso: () -> Unit,
    onIrARegistro: () -> Unit // <-- NUEVO PARÁMETRO
) {
    val uiState by vm.uiState.collectAsState()

    LaunchedEffect(key1 = Unit) {
        vm.eventoNavegacion.collectLatest { evento ->
            when (evento) {
                is EventoDeNavegacion.NavegarAInicio -> {
                    onLoginExitoso()
                }
                else -> { /* No hacer nada */ }
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(32.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text("Ventas y Arriendos", style = MaterialTheme.typography.headlineLarge)
        Spacer(modifier = Modifier.height(48.dp))

        // ... (CampoTexto Email y su error, sin cambios) ...
        CampoTexto(
            valor = uiState.email,
            alCambiar = { vm.onLoginEvent(EventoLogin.OnEmailChange(it)) },
            etiqueta = "Email"
        )
        val emailError = uiState.emailError
        if (emailError != null) {
            Text(
                text = emailError,
                color = MaterialTheme.colorScheme.error,
                modifier = Modifier.padding(top = 8.dp)
            )
        }
        Spacer(modifier = Modifier.height(16.dp))

        // ... (CampoTexto Contraseña y su error, sin cambios) ...
        CampoTexto(
            valor = uiState.pass,
            alCambiar = { vm.onLoginEvent(EventoLogin.OnPassChange(it)) },
            etiqueta = "Contraseña",
            tipoTeclado = KeyboardType.Password
        )
        val passError = uiState.passError
        if (passError != null) {
            Text(
                text = passError,
                color = MaterialTheme.colorScheme.error,
                modifier = Modifier.padding(top = 8.dp)
            )
        }

        Spacer(modifier = Modifier.height(32.dp))

        Button(
            onClick = {
                vm.onLoginEvent(EventoLogin.OnLoginClicked)
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Entrar")
        }

        // --- ¡¡NUEVO BOTÓN DE REGISTRO!! ---
        TextButton(
            onClick = onIrARegistro,
            modifier = Modifier.padding(top = 16.dp)
        ) {
            Text("¿No tienes cuenta? Regístrate")
        }
        // --- FIN ---
    }
}