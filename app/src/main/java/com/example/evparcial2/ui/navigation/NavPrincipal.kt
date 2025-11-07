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

        // Esta es la vista para el CLIENTE
        composable("productos") {
            PantallaProductos(
                vm = vmProductos,
                esAdmin = usuarioActual?.rol == "admin",
                onAgregarProducto = { navController.navigate("nuevo_producto") },
                onVerDetalle = { producto ->
                    // Aquí asumimos que tienes una ruta para el detalle
                    // navController.navigate("producto_detalle/${producto.id}")
                },
                onVolver = { navController.popBackStack() },
                onIrACarrito = { navController.navigate("carrito") }
            ) // <-- ¡¡CORREGIDO!! La 'V' ha sido eliminada.
        }

        // Esta es la vista para el ADMIN (cuando presiona "Gestión")
        composable("gestion") {
            PantallaProductos( // <-- Llamamos a la pantalla BUENA
                vm = vmProductos,
                esAdmin = true, // <-- Forzamos la vista de admin
                onAgregarProducto = { navController.navigate("nuevo_producto") },
                onVerDetalle = { producto ->
                    // navController.navigate("producto_detalle/${producto.id}")
                },
                onVolver = { navController.popBackStack() }, // Vuelve a "inicio"
                onIrACarrito = { /* No hay carrito en gestión */ }
            )
        }

        composable("nuevo_producto") {
            PantallaFormProducto(
                vm = vmProductos,
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