package com.example.evparcial2.ui.components.common

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
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
    modifier: Modifier = Modifier
) {
    var expanded by remember { mutableStateOf(false) }

    Card(
        modifier = modifier
            .fillMaxWidth()
            .clickable { onVerDetalle() },
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
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

            if (esAdmin) {
                Text(
                    "Stock: ${producto.stock}",
                    style = MaterialTheme.typography.bodySmall,
                    color = if (producto.stock == 0) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.onSurface
                )
            }
        }
    }
}