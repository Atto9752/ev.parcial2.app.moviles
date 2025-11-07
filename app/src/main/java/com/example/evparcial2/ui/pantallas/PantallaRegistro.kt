package com.example.evparcial2.ui.pantallas

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.evparcial2.domain.viewmodels.EventoDeNavegacion
import com.example.evparcial2.domain.viewmodels.EventoRegistro
import com.example.evparcial2.domain.viewmodels.ViewModelUsuarios
import com.example.evparcial2.ui.components.common.CampoTexto
import kotlinx.coroutines.flow.collectLatest

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PantallaRegistro(
    vm: ViewModelUsuarios,
    onRegistroExitoso: () -> Unit,
    onVolver: () -> Unit
) {
    val uiState by vm.uiStateRegistro.collectAsState()

    // Listener para el evento de navegación
    LaunchedEffect(key1 = Unit) {
        vm.eventoNavegacion.collectLatest { evento ->
            when (evento) {
                is EventoDeNavegacion.NavegarALogin -> {
                    onRegistroExitoso()
                }
                else -> { /* No hacer nada en otros eventos */ }
            }
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Crear Cuenta") },
                navigationIcon = {
                    IconButton(onClick = onVolver) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = "Volver")
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(32.dp)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

            // --- CAMPO NOMBRE ---
            CampoTexto(
                valor = uiState.nombre,
                alCambiar = { vm.onRegistroEvent(EventoRegistro.OnNombreChange(it)) },
                etiqueta = "Nombre Completo"
            )
            val nombreError = uiState.nombreError
            if (nombreError != null) {
                Text(text = nombreError, color = MaterialTheme.colorScheme.error, modifier = Modifier.padding(top = 8.dp))
            }
            Spacer(modifier = Modifier.height(16.dp))

            // --- CAMPO EMAIL ---
            CampoTexto(
                valor = uiState.email,
                alCambiar = { vm.onRegistroEvent(EventoRegistro.OnEmailChange(it)) },
                etiqueta = "Email",
                tipoTeclado = KeyboardType.Email
            )
            val emailError = uiState.emailError
            if (emailError != null) {
                Text(text = emailError, color = MaterialTheme.colorScheme.error, modifier = Modifier.padding(top = 8.dp))
            }
            Spacer(modifier = Modifier.height(16.dp))

            // --- CAMPO CONTRASEÑA ---
            CampoTexto(
                valor = uiState.pass,
                alCambiar = { vm.onRegistroEvent(EventoRegistro.OnPassChange(it)) },
                etiqueta = "Contraseña (mín. 6 caracteres)",
                tipoTeclado = KeyboardType.Password
            )
            val passError = uiState.passError
            if (passError != null) {
                Text(text = passError, color = MaterialTheme.colorScheme.error, modifier = Modifier.padding(top = 8.dp))
            }
            Spacer(modifier = Modifier.height(16.dp))

            // --- CAMPO CONFIRMAR CONTRASEÑA ---
            CampoTexto(
                valor = uiState.passConfirm,
                alCambiar = { vm.onRegistroEvent(EventoRegistro.OnPassConfirmChange(it)) },
                etiqueta = "Confirmar Contraseña",
                tipoTeclado = KeyboardType.Password
            )
            val passConfirmError = uiState.passConfirmError
            if (passConfirmError != null) {
                Text(text = passConfirmError, color = MaterialTheme.colorScheme.error, modifier = Modifier.padding(top = 8.dp))
            }
            Spacer(modifier = Modifier.height(32.dp))

            // --- BOTÓN REGISTRAR ---
            Button(
                onClick = {
                    vm.onRegistroEvent(EventoRegistro.OnRegistroClicked)
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Registrar")
            }
        }
    }
}