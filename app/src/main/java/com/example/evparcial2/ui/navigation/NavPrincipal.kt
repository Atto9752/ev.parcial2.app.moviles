package com.example.evparcial2.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.evparcial2.domain.viewmodels.ViewModelProductos
import com.example.evparcial2.domain.viewmodels.ViewModelUsuarios
import com.example.evparcial2.ui.pantallas.*

@Composable
fun NavPrincipal(
    navController: NavHostController = rememberNavController(),
    vm: ViewModelUsuarios = viewModel(),
    vmProductos: ViewModelProductos = viewModel()
) {
    val usuarioActual by vm.usuarioActual.collectAsState()

    LaunchedEffect(usuarioActual) {
        if (usuarioActual == null && navController.currentDestination?.route != "login" && navController.currentDestination?.route != "registro") {
            navController.navigate("login") {
                popUpTo(navController.graph.startDestinationId) {
                    inclusive = true
                }
                launchSingleTop = true
            }
        }
    }

    NavHost(
        navController = navController,
        startDestination = if (usuarioActual != null) "inicio" else "login"
    ) {
        composable("login") {
            PantallaLogin(
                vm = vm,
                onLoginExitoso = {
                    navController.navigate("inicio") {
                        popUpTo("login") { inclusive = true }
                    }
                },
                onIrARegistro = {
                    navController.navigate("registro")
                }
            )
        }

        composable("registro") {
            PantallaRegistro(
                vm = vm,
                onRegistroExitoso = {
                    navController.navigate("login") {
                        popUpTo("registro") { inclusive = true }
                    }
                },
                onVolver = {
                    navController.popBackStack()
                }
            )
        }

        composable("inicio") {
            PantallaInicio(
                usuario = usuarioActual!!,
                irProductos = { navController.navigate("productos") },
                irCarrito = { navController.navigate("carrito") },
                irPedidos = { navController.navigate("pedidos") },
                irGestion = { navController.navigate("gestion") }, // <-- Botón de Admin
                irPerfil = { navController.navigate("perfil") }
            )
        }

        // --- RUTA COMPARTIDA: Productos (Cliente) y Tienda (Admin) ---
        composable("productos") {
            PantallaProductos(
                vm = vmProductos,
                esAdmin = usuarioActual?.rol == "admin",
                onAgregarProducto = { navController.navigate("nuevo_producto") },
                onVerDetalle = { producto ->
                    navController.navigate("producto_detalle/${producto.id}")
                },
                onVolver = { navController.popBackStack() },
                onIrACarrito = { navController.navigate("carrito") },
                // --- CONEXIÓN LÁPIZ ---
                onEditarProducto = { producto ->
                    navController.navigate("editar_producto/${producto.id}")
                }
            )
        }

        // --- RUTA GESTIÓN (Solo Admin) ---
        composable("gestion") {
            PantallaProductos(
                vm = vmProductos,
                esAdmin = true, // Forzamos la vista de admin
                onAgregarProducto = { navController.navigate("nuevo_producto") },
                onVerDetalle = { producto ->
                    navController.navigate("producto_detalle/${producto.id}")
                },
                onVolver = { navController.popBackStack() },
                onIrACarrito = { /* No hay carrito en gestión */ },
                // --- CONEXIÓN LÁPIZ ---
                onEditarProducto = { producto ->
                    navController.navigate("editar_producto/${producto.id}")
                }
            )
        }

        // --- RUTA NUEVA PARA EDICIÓN (Carga de datos) ---
        composable("editar_producto/{productoId}") { backStackEntry ->
            // Captura el ID del producto de la ruta
            val productoId = backStackEntry.arguments?.getString("productoId")?.toLongOrNull()

            PantallaFormProducto( // Reutilizamos el formulario
                vm = vmProductos,
                productoId = productoId, // <-- LE PASAMOS EL ID DEL PRODUCTO
                onVolver = { navController.popBackStack() },
                onGuardar = { navController.popBackStack() }
            )
        }

        // --- RUTA CREAR (Sin ID de producto) ---
        composable("nuevo_producto") {
            PantallaFormProducto(
                vm = vmProductos,
                productoId = null, // <-- Aseguramos que el ID sea nulo para CREAR
                onVolver = { navController.popBackStack() },
                onGuardar = { navController.popBackStack() }
            )
        }

        composable("carrito") {
            PantallaCarrito(
                onVolver = { navController.popBackStack() },
                onConfirmar = {
                    navController.navigate("pedidos")
                }
            )
        }

        composable("pedidos") {
            PantallaPedidos(
                onVolver = { navController.popBackStack() }
            )
        }

        composable("perfil") {
            PantallaPerfil(
                usuario = usuarioActual!!,
                onVolver = { navController.popBackStack() },
                onCerrarSesion = {
                    vm.cerrarSesion()
                }
            )
        }
    }
}