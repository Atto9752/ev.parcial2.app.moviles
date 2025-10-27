package com.example.evparcial2.ui.pantallas

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.evparcial2.data.model.Usuario
import com.example.evparcial2.ui.components.common.CampoTexto

@Composable
fun PantallaLogin(alLogin: (Usuario) -> Unit) {
    var email by remember { mutableStateOf("") }
    var pass by remember { mutableStateOf("") }
    var mostrarError by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(32.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text("Ventas y Arriendos", style = MaterialTheme.typography.headlineLarge)
        Spacer(modifier = Modifier.height(48.dp))

        CampoTexto(
            valor = email,
            alCambiar = { email = it },
            etiqueta = "Email"
        )
        Spacer(modifier = Modifier.height(16.dp))

        CampoTexto(
            valor = pass,
            alCambiar = { pass = it },
            etiqueta = "Contraseña",
            tipoTeclado = KeyboardType.Password
        )

        if (mostrarError) {
            Text(
                "Credenciales incorrectas",
                color = MaterialTheme.colorScheme.error,
                modifier = Modifier.padding(top = 8.dp)
            )
        }

        Spacer(modifier = Modifier.height(32.dp))

        Column {
            Button(
                onClick = {
                    alLogin(Usuario(
                        nombre = "Cliente Demo",
                        email = "cliente@demo.com",
                        rol = "cliente"
                    ))
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Entrar como Cliente")
            }
            Spacer(modifier = Modifier.height(12.dp))
            Button(
                onClick = {
                    alLogin(Usuario(
                        nombre = "Admin Demo",
                        email = "admin@demo.com",
                        rol = "admin"
                    ))
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Entrar como Administrador")
            }
        }
    }
}