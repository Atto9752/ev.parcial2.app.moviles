package com.example.evparcial2.ui.components.common

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.evparcial2.data.model.Producto

@Composable
fun PlantillaProducto(
    producto: Producto,
    esAdmin: Boolean,
    onVerDetalle: () -> Unit,
    onAgregarCarrito: () -> Unit,
    onEditarProducto: () -> Unit,
    onEliminarProducto: () -> Unit,
    modifier: Modifier = Modifier
) {
    // --- ¡¡AQUÍ ESTÁ LA CORRECCIÓN!! ---
    var expanded by remember { mutableStateOf(false) } // Antes decía mutableStateof

    Card(
        modifier = modifier
            .fillMaxWidth()
            .clickable { onVerDetalle() },
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        producto.nombre,
                        style = MaterialTheme.typography.titleLarge
                    )
                    Text(
                        "$${producto.precio}",
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.primary
                    )
                }

                if (!esAdmin) {
                    Button(
                        onClick = onAgregarCarrito,
                        modifier = Modifier.size(48.dp)
                    ) {
                        Text("+", style = MaterialTheme.typography.bodyLarge)
                    }
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                producto.descripcion,
                maxLines = if (expanded) 10 else 2,
                overflow = TextOverflow.Ellipsis
            )

            if (producto.descripcion.length > 50) {
                TextButton(onClick = { expanded = !expanded }) {
                    Text(if (expanded) "Ver menos" else "Ver más")
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            if (esAdmin) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        "Stock: ${producto.stock}",
                        style = MaterialTheme.typography.bodySmall,
                        color = if (producto.stock == 0) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.onSurface
                    )

                    Row {
                        IconButton(onClick = onEditarProducto) {
                            Icon(Icons.Default.Edit, contentDescription = "Editar")
                        }
                        IconButton(onClick = onEliminarProducto) {
                            Icon(
                                Icons.Default.Delete,
                                contentDescription = "Eliminar",
                                tint = MaterialTheme.colorScheme.error
                            )
                        }
                    }
                }
            }
        }
    }
}