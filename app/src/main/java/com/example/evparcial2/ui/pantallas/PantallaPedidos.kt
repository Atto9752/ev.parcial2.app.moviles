package com.example.evparcial2.ui.pantallas

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.evparcial2.domain.viewmodels.ViewModelPedidos

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PantallaPedidos(onVolver: () -> Unit) {
    val viewModel: ViewModelPedidos = viewModel()
    val pedidos = viewModel.pedidos

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Mis Pedidos") },
                navigationIcon = {
                    IconButton(onClick = onVolver) {
                        Text("←", style = MaterialTheme.typography.titleLarge)
                    }
                }
            )
        }
    ) { padding ->
        if (pedidos.isEmpty()) {
            Column(
                modifier = Modifier
                    .padding(padding)
                    .fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Box(
                    modifier = Modifier
                        .size(64.dp)
                        .background(
                            MaterialTheme.colorScheme.onSurface.copy(alpha = 0.1f),
                            CircleShape
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Text("📋", style = MaterialTheme.typography.headlineMedium)
                }
                Spacer(modifier = Modifier.height(16.dp))
                Text("No tienes pedidos aún")
                Spacer(modifier = Modifier.height(8.dp))
                Text("Realiza tu primera compra")
            }
        } else {
            LazyColumn(modifier = Modifier.padding(padding)) {
                items(pedidos) { pedido ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp)
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Text("Pedido #${pedido.id}", style = MaterialTheme.typography.titleMedium)
                            Text("Total: $${pedido.total}")
                            Text("Estado: ${pedido.estado}")
                        }
                    }
                }
            }
        }
    }
}