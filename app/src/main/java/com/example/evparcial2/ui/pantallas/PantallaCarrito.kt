package com.example.evparcial2.ui.pantallas

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.evparcial2.data.model.Producto
import com.example.evparcial2.domain.viewmodels.ViewModelPedidos

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PantallaCarrito(onVolver: () -> Unit, onConfirmar: () -> Unit) {
    val viewModel: ViewModelPedidos = viewModel()
    val estadoCarrito = viewModel.estadoCarrito.collectAsState()
    var mostrarDialogoConfirmar by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text("Mi Carrito (${estadoCarrito.value.cantidadItems})")
                },
                navigationIcon = {
                    IconButton(onClick = onVolver) {
                        Text("←", style = MaterialTheme.typography.titleLarge)
                    }
                },
                actions = {
                    if (estadoCarrito.value.items.isNotEmpty()) {
                        IconButton(
                            onClick = { viewModel.vaciarCarrito() }
                        ) {
                            Text("X", style = MaterialTheme.typography.bodyLarge)
                        }
                    }
                }
            )
        },
        bottomBar = {
            if (estadoCarrito.value.items.isNotEmpty()) {
                Surface(
                    tonalElevation = 8.dp,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Column {
                                Text("Total:", style = MaterialTheme.typography.bodyMedium)
                                Text(
                                    "$${"%.2f".format(estadoCarrito.value.total)}",
                                    style = MaterialTheme.typography.headlineSmall,
                                    color = MaterialTheme.colorScheme.primary
                                )
                            }

                            Button(
                                onClick = { mostrarDialogoConfirmar = true },
                                modifier = Modifier.width(180.dp)
                            ) {
                                Text("Confirmar Pedido")
                            }
                        }
                    }
                }
            }
        }
    ) { padding ->
        if (estadoCarrito.value.items.isEmpty()) {
            CarritoVacio(modifier = Modifier.padding(padding))
        } else {
            ListaCarrito(
                items = estadoCarrito.value.items,
                onQuitarItem = { producto -> viewModel.quitarDelCarrito(producto) },
                modifier = Modifier.padding(padding)
            )
        }
    }

    if (mostrarDialogoConfirmar) {
        AlertDialog(
            onDismissRequest = { mostrarDialogoConfirmar = false },
            title = { Text("Confirmar Pedido") },
            text = {
                Column {
                    Text("¿Estás seguro de que quieres confirmar tu pedido?")
                    Spacer(modifier = Modifier.height(8.dp))
                    Text("Total: $${"%.2f".format(estadoCarrito.value.total)}")
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        viewModel.confirmarPedido()
                        mostrarDialogoConfirmar = false
                        onConfirmar()
                    }
                ) {
                    Text("Confirmar")
                }
            },
            dismissButton = {
                TextButton(
                    onClick = { mostrarDialogoConfirmar = false }
                ) {
                    Text("Cancelar")
                }
            }
        )
    }
}

@Composable
fun CarritoVacio(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(32.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Box(
            modifier = Modifier
                .size(120.dp)
                .background(
                    MaterialTheme.colorScheme.onSurface.copy(alpha = 0.1f),
                    CircleShape
                ),
            contentAlignment = Alignment.Center
        ) {
            Text(
                "🛒",
                style = MaterialTheme.typography.headlineLarge
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            "Tu carrito está vacío",
            style = MaterialTheme.typography.headlineMedium
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            "Agrega algunos productos desde el catálogo",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f),
            textAlign = TextAlign.Center
        )
    }
}

@Composable
fun ListaCarrito(
    items: List<Producto>,
    onQuitarItem: (Producto) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(modifier = modifier) {
        items(items) { producto ->
            ItemCarrito(
                producto = producto,
                onQuitar = { onQuitarItem(producto) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp)
            )
        }

        item {
            Spacer(modifier = Modifier.height(16.dp))

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text("Resumen del Pedido", style = MaterialTheme.typography.titleMedium)
                    Spacer(modifier = Modifier.height(12.dp))
                    InfoResumen(titulo = "Subtotal", valor = "$${"%.2f".format(items.sumOf { it.precio })}")
                    InfoResumen(titulo = "Envío", valor = "Gratis")
                    InfoResumen(titulo = "Total", valor = "$${"%.2f".format(items.sumOf { it.precio })}", isTotal = true)
                }
            }
        }
    }
}

@Composable
fun ItemCarrito(
    producto: Producto,
    onQuitar: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(modifier = modifier) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(60.dp)
                    .background(
                        MaterialTheme.colorScheme.primary.copy(alpha = 0.1f),
                        RoundedCornerShape(8.dp)
                    ),
                contentAlignment = Alignment.Center
            ) {
                Text("📦", style = MaterialTheme.typography.bodyLarge)
            }

            Spacer(modifier = Modifier.width(16.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    producto.nombre,
                    style = MaterialTheme.typography.titleMedium,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    "$${"%.2f".format(producto.precio)}",
                    style = MaterialTheme.typography.titleSmall,
                    color = MaterialTheme.colorScheme.primary
                )
            }

            Spacer(modifier = Modifier.width(16.dp))

            IconButton(onClick = onQuitar) {
                Text("X", style = MaterialTheme.typography.bodyLarge)
            }
        }
    }
}

@Composable
fun InfoResumen(titulo: String, valor: String, isTotal: Boolean = false) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            titulo,
            style = if (isTotal) MaterialTheme.typography.titleMedium else MaterialTheme.typography.bodyMedium
        )
        Text(
            valor,
            style = if (isTotal) MaterialTheme.typography.titleMedium else MaterialTheme.typography.bodyMedium
        )
    }
}