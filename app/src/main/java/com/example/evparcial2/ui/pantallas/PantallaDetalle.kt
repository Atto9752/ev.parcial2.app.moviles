package com.example.evparcial2.ui.pantallas

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.evparcial2.data.model.Producto

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PantallaDetalle(
    producto: Producto,
    esAdmin: Boolean,
    onVolver: () -> Unit,
    onAgregarCarrito: (Producto) -> Unit
) {
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Detalle del Producto") },
                navigationIcon = {
                    IconButton(onClick = onVolver) {
                        Text("←", style = MaterialTheme.typography.titleLarge)
                    }
                }
            )
        },
        floatingActionButton = {
            if (!esAdmin) {
                FloatingActionButton(
                    onClick = { onAgregarCarrito(producto) }
                ) {
                    Text("+", style = MaterialTheme.typography.titleLarge)
                }
            }
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(250.dp)
                    .background(
                        MaterialTheme.colorScheme.primary.copy(alpha = 0.1f),
                        RoundedCornerShape(bottomStart = 16.dp, bottomEnd = 16.dp)
                    ),
                contentAlignment = Alignment.Center
            ) {
                Text("Box", style = MaterialTheme.typography.headlineLarge)
            }

            Column(modifier = Modifier.padding(16.dp)) {
                Text(producto.nombre, style = MaterialTheme.typography.headlineLarge)
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    "$${"%.2f".format(producto.precio)}",
                    style = MaterialTheme.typography.headlineMedium,
                    color = MaterialTheme.colorScheme.primary
                )
                Spacer(modifier = Modifier.height(16.dp))
                Text("Descripcion:", style = MaterialTheme.typography.titleMedium)
                Text(producto.descripcion, style = MaterialTheme.typography.bodyLarge)
                Spacer(modifier = Modifier.height(16.dp))

                Card(modifier = Modifier.fillMaxWidth()) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        InfoDetalleSimple("Categoria: ${producto.categoria}")
                        InfoDetalleSimple("Stock disponible: ${producto.stock}")
                        if (esAdmin) {
                            InfoDetalleSimple("ID: ${producto.id}")
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun InfoDetalleSimple(texto: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(texto)
    }
}