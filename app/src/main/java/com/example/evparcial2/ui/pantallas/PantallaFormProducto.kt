package com.example.evparcial2.ui.pantallas

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.evparcial2.ui.components.common.CampoTexto
import com.example.evparcial2.ui.components.common.BotonCargando

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PantallaFormProducto(onVolver: () -> Unit, onGuardar: () -> Unit) {
    var nombre by androidx.compose.runtime.remember { androidx.compose.runtime.mutableStateOf("") }
    var descripcion by androidx.compose.runtime.remember { androidx.compose.runtime.mutableStateOf("") }
    var precio by androidx.compose.runtime.remember { androidx.compose.runtime.mutableStateOf("") }
    var stock by androidx.compose.runtime.remember { androidx.compose.runtime.mutableStateOf("") }
    var categoria by androidx.compose.runtime.remember { androidx.compose.runtime.mutableStateOf("") }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Nuevo Producto") },
                navigationIcon = {
                    IconButton(onClick = onVolver) {
                        Text("←", style = MaterialTheme.typography.titleLarge)
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(16.dp)
        ) {
            // nombre
            CampoTexto(
                valor = nombre,
                alCambiar = { nombre = it },
                etiqueta = "Nombre del producto"
            )

            Spacer(modifier = Modifier.height(16.dp))

            // descripcion
            CampoTexto(
                valor = descripcion,
                alCambiar = { descripcion = it },
                etiqueta = "Descripción",
                modificador = Modifier.height(100.dp)
            )

            Spacer(modifier = Modifier.height(16.dp))

            // precio
            CampoTexto(
                valor = precio,
                alCambiar = { precio = it },
                etiqueta = "Precio",
                tipoTeclado = KeyboardType.Decimal
            )

            Spacer(modifier = Modifier.height(16.dp))

            // stock
            CampoTexto(
                valor = stock,
                alCambiar = { stock = it },
                etiqueta = "Stock disponible",
                tipoTeclado = KeyboardType.Number
            )

            Spacer(modifier = Modifier.height(16.dp))

            // categoria
            CampoTexto(
                valor = categoria,
                alCambiar = { categoria = it },
                etiqueta = "Categoría"
            )

            Spacer(modifier = Modifier.height(32.dp))

            // guardar
            BotonCargando(
                onClick = {
                    if (nombre.isNotBlank() && precio.isNotBlank()) {
                        onGuardar()
                    }
                },
                isLoading = false,
                text = "Guardar Producto",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
            )
        }
    }
}