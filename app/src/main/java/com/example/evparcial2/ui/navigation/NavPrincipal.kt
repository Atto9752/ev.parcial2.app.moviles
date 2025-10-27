package com.example.evparcial2.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.evparcial2.data.model.Usuario
import com.example.evparcial2.ui.pantallas.*

@Composable
fun NavPrincipal() {
    val navController = rememberNavController()

    var usuarioActual by remember { mutableStateOf<Usuario?>(null) }

    fun iniciarSesion(usuario: Usuario) {
        usuarioActual = usuario
    }

    fun cerrarSesion() {
        usuarioActual = null
    }

    NavHost(
        navController = navController,
        startDestination = if (usuarioActual != null) "inicio" else "login"
    ) {
        composable("login") {
            PantallaLogin { usuario ->
                iniciarSesion(usuario)
                navController.navigate("inicio") {
                    popUpTo("login") { inclusive = true }
                }
            }
        }

        composable("inicio") {
            PantallaInicio(
                usuario = usuarioActual!!,
                irProductos = { navController.navigate("productos") },
                irCarrito = { navController.navigate("carrito") },
                irPedidos = { navController.navigate("pedidos") },
                irGestion = { navController.navigate("gestion") },
                irPerfil = { navController.navigate("perfil") }
            )
        }

        composable("productos") {
            PantallaProductos(
                esAdmin = usuarioActual?.rol == "admin",
                onAgregarProducto = { navController.navigate("nuevo_producto") },
                onVerDetalle = { producto ->
                    navController.navigate("producto_detalle/${producto.id}")
                },
                onVolver = { navController.popBackStack() }
            )
        }

        composable("nuevo_producto") {
            PantallaFormProducto(
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

        composable("gestion") {
            PantallaGestion(
                onVolver = { navController.popBackStack() }
            )
        }

        composable("perfil") {
            PantallaPerfil(
                usuario = usuarioActual!!,
                onVolver = { navController.popBackStack() },
                onCerrarSesion = {
                    cerrarSesion()
                    navController.navigate("login") {
                        popUpTo("inicio") { inclusive = true }
                    }
                }
            )
        }
    }
}