package com.example.evparcial2.ui.pantallas

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.evparcial2.data.model.Usuario

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PantallaInicio(
    usuario: Usuario,
    irProductos: () -> Unit,
    irCarrito: () -> Unit,
    irPedidos: () -> Unit,
    irGestion: () -> Unit,
    irPerfil: () -> Unit
) {
    val esAdmin = usuario.rol == "admin"

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Bienvenido, ${usuario.nombre}") },
                actions = {
                    IconButton(onClick = irPerfil) {
                        Text("P", style = MaterialTheme.typography.bodyLarge)
                    }
                }
            )
        },
        bottomBar = {
            BottomAppBar {
                Row(modifier = Modifier.fillMaxWidth()) {
                    if (esAdmin) {
                        Button(onClick = irGestion) {
                            Text("Gestionar")
                        }
                    } else {
                        Button(onClick = irCarrito) {
                            Text("Carrito")
                        }
                    }
                    Spacer(Modifier.weight(1f))
                    Button(onClick = irProductos) {
                        Text("Productos")
                    }
                }
            }
        }
    ) { padding ->
        Column(
            modifier = Modifier.padding(padding).fillMaxSize().padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(if (esAdmin) "Panel Admin" else "Tienda", style = MaterialTheme.typography.headlineMedium)
            Spacer(modifier = Modifier.height(32.dp))

            // SIMPLE - sin clickable problemático
            if (esAdmin) {
                Button(onClick = irGestion, modifier = Modifier.fillMaxWidth()) {
                    Text("Gestionar Productos")
                }
            } else {
                Button(onClick = irProductos, modifier = Modifier.fillMaxWidth()) {
                    Text("Ver Catálogo")
                }
                Spacer(modifier = Modifier.height(16.dp))
                Button(onClick = irPedidos, modifier = Modifier.fillMaxWidth()) {
                    Text("Mis Pedidos")
                }
            }
        }
    }
}