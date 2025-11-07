package com.example.evparcial2.ui.pantallas

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.filled.Store
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
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
                        Icon(
                            Icons.Filled.AccountCircle,
                            contentDescription = "Perfil"
                        )
                    }
                }
            )
        },
        // --- ¡¡BARRA INFERIOR SIMPLIFICADA!! ---
        bottomBar = {
            BottomAppBar {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    // 'SpaceAround' para Cliente, 'Center' para Admin
                    horizontalArrangement = if (esAdmin) Arrangement.Center else Arrangement.SpaceAround
                ) {
                    if (esAdmin) {
                        // --- AHORA SOLO HAY UN BOTÓN PARA EL ADMIN ---
                        Button(onClick = irGestion) {
                            Text("Gestionar Tienda")
                            Spacer(modifier = Modifier.width(8.dp)) // Más espacio
                            Icon(Icons.Filled.Build, contentDescription = null)
                        }
                    } else {
                        // --- LOS 3 BOTONES DEL CLIENTE SE QUEDAN IGUAL ---
                        Button(onClick = irProductos) {
                            Text("Tienda")
                            Spacer(modifier = Modifier.width(4.dp))
                            Icon(Icons.Filled.Store, contentDescription = null)
                        }
                        Button(onClick = irPedidos) {
                            Text("Pedidos")
                            Spacer(modifier = Modifier.width(4.dp))
                            Icon(Icons.Filled.List, contentDescription = null)
                        }
                        Button(onClick = irCarrito) {
                            Text("Carrito")
                            Spacer(modifier = Modifier.width(4.dp))
                            Icon(Icons.Filled.ShoppingCart, contentDescription = null)
                        }
                    }
                }
            }
        }
    ) { padding ->
        // --- Contenido de bienvenida (sin botones confusos) ---
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                if (esAdmin) "Panel de Administrador" else "¡Bienvenido a la Tienda!",
                style = MaterialTheme.typography.headlineMedium
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                "Usa la barra de navegación inferior para moverte por la app.",
                style = MaterialTheme.typography.bodyLarge,
                textAlign = TextAlign.Center
            )
        }
    }
}