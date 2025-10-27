    package com.example.evparcial2.ui.pantallas

    import androidx.compose.foundation.layout.*
    import androidx.compose.foundation.lazy.LazyColumn
    import androidx.compose.foundation.lazy.items
    import androidx.compose.material3.*
    import androidx.compose.runtime.Composable
    import androidx.compose.runtime.collectAsState
    import androidx.compose.ui.Alignment
    import androidx.compose.ui.Modifier
    import androidx.compose.ui.unit.dp
    import androidx.lifecycle.viewmodel.compose.viewModel
    import com.example.evparcial2.data.model.Producto
    import com.example.evparcial2.domain.viewmodels.ViewModelPedidos
    import com.example.evparcial2.domain.viewmodels.ViewModelProductos
    import com.example.evparcial2.ui.components.common.PlantillaProducto

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun PantallaProductos(
        esAdmin: Boolean,
        onAgregarProducto: () -> Unit,
        onVerDetalle: (Producto) -> Unit,
        onVolver: () -> Unit
    ) {
        val viewModel: ViewModelProductos = viewModel()
        val productos = viewModel.productos.collectAsState(initial = emptyList())
        val viewModelPedidos: ViewModelPedidos = viewModel()

        Scaffold(
            topBar = {
                CenterAlignedTopAppBar(
                    title = { Text("Productos") },
                    navigationIcon = {
                        IconButton(onClick = onVolver) {
                            Text("←", style = MaterialTheme.typography.titleLarge)
                        }
                    },
                    actions = {
                        if (esAdmin) {
                            IconButton(onClick = onAgregarProducto) {
                                Text("+", style = MaterialTheme.typography.titleLarge)
                            }
                        } else {
                            val cantidadCarrito = viewModelPedidos.estadoCarrito.collectAsState()
                            BadgedBox(
                                badge = {
                                    if (cantidadCarrito.value.cantidadItems > 0) {
                                        Badge {
                                            Text(cantidadCarrito.value.cantidadItems.toString())
                                        }
                                    }
                                }
                            ) {
                                Text("🛒", style = MaterialTheme.typography.bodyLarge)
                            }
                        }
                    }
                )
            },
            floatingActionButton = {
                if (esAdmin) {
                    FloatingActionButton(onClick = onAgregarProducto) {
                        Text("+", style = MaterialTheme.typography.titleLarge)
                    }
                }
            }
        ) { padding ->
            if (productos.value.isEmpty()) {
                Column(
                    modifier = Modifier
                        .padding(padding)
                        .fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text("No hay productos disponibles")
                    if (esAdmin) {
                        Spacer(modifier = Modifier.height(16.dp))
                        Button(onClick = onAgregarProducto) {
                            Text("Agregar Primer Producto")
                        }
                    }
                }
            } else {
                LazyColumn(modifier = Modifier.padding(padding)) {
                    items(productos.value) { producto ->
                        PlantillaProducto(
                            producto = producto,
                            esAdmin = esAdmin,
                            onVerDetalle = { onVerDetalle(producto) },
                            onAgregarCarrito = {
                                if (!esAdmin) viewModelPedidos.agregarAlCarrito(producto)
                            },
                            modifier = Modifier.padding(8.dp)
                        )
                    }
                }
            }
        }
    }