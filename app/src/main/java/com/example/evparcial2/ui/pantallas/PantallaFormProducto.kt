package com.example.evparcial2.ui.pantallas

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.evparcial2.domain.viewmodels.ViewModelProductos // <-- IMPORT NUEVO
import com.example.evparcial2.ui.components.common.CampoTexto
import com.example.evparcial2.ui.components.common.BotonCargando

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PantallaFormProducto(
    vm: ViewModelProductos, // <-- CAMBIO 1: Recibe el ViewModel
    onVolver: () -> Unit,
    onGuardar: () -> Unit
) {
    var nombre by remember { mutableStateOf("") }
    var descripcion by remember { mutableStateOf("") }
    var precio by remember { mutableStateOf("") }
    var stock by remember { mutableStateOf("") }
    var categoria by remember { mutableStateOf("") }
    var tipo by remember { mutableStateOf("") } // <-- CAMBIO 2: Campo 'tipo' añadido

    var error by remember { mutableStateOf<String?>(null) } // <-- Estado para errores

    // --- CAMBIO 3: Función de validación y guardado ---
    fun validarYGuardar() {
        error = null // Limpiar error anterior

        // Validación simple
        val precioDouble = precio.toDoubleOrNull()
        val stockInt = stock.toIntOrNull()

        if (nombre.isBlank() || categoria.isBlank() || tipo.isBlank()) {
            error = "Nombre, Categoría y Tipo son obligatorios"
            return
        }
        if (precioDouble == null) {
            error = "El precio debe ser un número válido"
            return
        }
        if (stockInt == null) {
            error = "El stock debe ser un número válido"
            return
        }

        // Si todo está bien, llamamos al ViewModel
        vm.agregarProducto(
            nombre = nombre,
            descripcion = descripcion,
            precio = precioDouble,
            stock = stockInt,
            categoria = categoria,
            tipo = tipo
        )

        // Y llamamos a la navegación
        onGuardar()
    }
    // --- FIN DEL CAMBIO 3 ---

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
                etiqueta = "Nombre del producto *"
            )
            Spacer(modifier = Modifier.height(16.dp))

            // descripcion
            CampoTexto(
                valor = descripcion,
                alCambiar = { descripcion = it },
                etiqueta = "Descripción",
                modificador = Modifier.height(100.dp) // Mantengo 'modificador' como en tu código
            )
            Spacer(modifier = Modifier.height(16.dp))

            // precio
            CampoTexto(
                valor = precio,
                alCambiar = { precio = it },
                etiqueta = "Precio *",
                tipoTeclado = KeyboardType.Decimal
            )
            Spacer(modifier = Modifier.height(16.dp))

            // stock
            CampoTexto(
                valor = stock,
                alCambiar = { stock = it },
                etiqueta = "Stock disponible *",
                tipoTeclado = KeyboardType.Number
            )
            Spacer(modifier = Modifier.height(16.dp))

            // categoria
            CampoTexto(
                valor = categoria,
                alCambiar = { categoria = it },
                etiqueta = "Categoría (ej: Venta, Arriendo) *"
            )
            Spacer(modifier = Modifier.height(16.dp))

            // --- CAMBIO 4: Campo 'tipo' añadido ---
            CampoTexto(
                valor = tipo,
                alCambiar = { tipo = it },
                etiqueta = "Tipo (ej: casa, depto, oficina) *"
            )
            Spacer(modifier = Modifier.height(24.dp))

            // --- Mostrar error si existe ---
            if (error != null) {
                Text(
                    text = error!!,
                    color = MaterialTheme.colorScheme.error,
                    modifier = Modifier.padding(bottom = 16.dp)
                )
            }

            // guardar
            BotonCargando(
                onClick = {
                    // --- CAMBIO 5: Llamamos a la nueva función ---
                    validarYGuardar()
                },
                isLoading = false, // (Puedes conectar esto a un estado en el futuro)
                text = "Guardar Producto",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
            )
        }
    }
}