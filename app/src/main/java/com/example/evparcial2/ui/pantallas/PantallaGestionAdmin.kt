package com.example.evparcial2.ui.pantallas

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.evparcial2.data.model.Producto

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PantallaGestion(onVolver: () -> Unit) {
    var productos by remember {
        mutableStateOf(
            listOf(
                Producto(
                    id = 1,
                    nombre = "Casa moderna",
                    descripcion = "Casa estilo moderno con piscina, 3 dormitorios, 2 baños",
                    precio = 250000.0,
                    stock = 1,
                    categoria = "Venta",
                    tipo = "venta"
                ),
                Producto(
                    id = 2,
                    nombre = "Departamento de lujo",
                    descripcion = "Departamento amplio en el centro de la ciudad, con acceso a piscina temperada, gimnasio, etc.",
                    precio = 300000.0,
                    stock = 2,
                    categoria = "Venta",
                    tipo = "venta"
                ),
                Producto(
                    id = 3,
                    nombre = "Departamento para arriendo",
                    descripcion = "Departamento pequeño en el segundo piso del edificio. Ideal para estudiantes.",
                    precio = 450.0,
                    stock = 5,
                    categoria = "Arriendo",
                    tipo = "arriendo"
                ),
                Producto(
                    id = 4,
                    nombre = "Oficina comercial",
                    descripcion = "Oficina en zona céntrica, 40m², ideal para profesionales",
                    precio = 800.0,
                    stock = 3,
                    categoria = "Arriendo",
                    tipo = "arriendo"
                ),
                Producto(
                    id = 5,
                    nombre = "Casa playa",
                    descripcion = "Casa frente al mar con vista panorámica, 4 dormitorios, piscina",
                    precio = 450000.0,
                    stock = 1,
                    categoria = "Venta",
                    tipo = "venta"
                )
            )
        )
    }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Gestión de Productos") },
                navigationIcon = {
                    IconButton(onClick = onVolver) {
                        Text("←", style = MaterialTheme.typography.titleLarge)
                    }
                }
            )
        }
    ) { padding ->
        if (productos.isEmpty()) {
            Column(
                modifier = Modifier.padding(padding).fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text("No hay productos")
            }
        } else {
            LazyColumn(modifier = Modifier.padding(padding)) {
                items(productos) { producto ->
                    Card(modifier = Modifier.fillMaxWidth().padding(8.dp)) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Column(modifier = Modifier.weight(1f)) {
                                    Text(producto.nombre, style = MaterialTheme.typography.titleLarge)
                                    Text("$${producto.precio}")
                                    Text("Stock: ${producto.stock}")
                                }
                                Row {
                                    IconButton(onClick = { /* Editar */ }) {
                                        Text("E", style = MaterialTheme.typography.bodyLarge)
                                    }
                                    IconButton(onClick = {
                                        productos = productos.filter { it.id != producto.id }
                                    }) {
                                        Text("X", style = MaterialTheme.typography.bodyLarge)
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}