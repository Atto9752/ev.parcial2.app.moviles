package com.example.evparcial2.domain.viewmodels

import androidx.lifecycle.ViewModel
import com.example.evparcial2.data.model.Producto
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update // <-- Import necesario para .update()

class ViewModelProductos : ViewModel() {
    private val _productos = MutableStateFlow<List<Producto>>(emptyList())
    val productos: StateFlow<List<Producto>> = _productos.asStateFlow()

    init {
        _productos.value = listOf(
            Producto(
                id = 1L, // <-- CAMBIO 1: ID cambiado a Long (1L)
                nombre = "Casa moderna",
                descripcion = "Casa estilo moderno con piscina, 3 dormitorios, 2 baños",
                precio = 250000.0,
                stock = 1,
                categoria = "Venta",
                tipo = "venta"
            ),
            Producto(
                id = 2L, // <-- CAMBIO 1: ID cambiado a Long (2L)
                nombre = "Departamento de lujo",
                descripcion = "Departamento amplio en el centro de la ciudad, con acceso a piscina temperada, gimnasio, etc.",
                precio = 300000.0,
                stock = 2,
                categoria = "Venta",
                tipo = "venta"
            ),
            Producto(
                id = 3L, // <-- CAMBIO 1: ID cambiado a Long (3L)
                nombre = "Departamento para arriendo",
                descripcion = "Departamento pequeño en el segundo piso del edificio. Ideal para estudiantes.",
                precio = 450.0,
                stock = 5,
                categoria = "Arriendo",
                tipo = "arriendo"
            ),
            Producto(
                id = 4L, // <-- CAMBIO 1: ID cambiado a Long (4L)
                nombre = "Oficina comercial",
                descripcion = "Oficina en zona céntrica, 40m², ideal para profesionales",
                precio = 800.0,
                stock = 3,
                categoria = "Arriendo",
                tipo = "arriendo"
            ),
            Producto(
                id = 5L, // <-- CAMBIO 1: ID cambiado a Long (5L)
                nombre = "Casa playa",
                descripcion = "Casa frente al mar con vista panorámica, 4 dormitorios, piscina",
                precio = 450000.0,
                stock = 1,
                categoria = "Venta",
                tipo = "venta"
            )
        )
    }

    // --- CAMBIO 2: Esta función ahora coincide con PantallaFormProducto ---
    fun agregarProducto(
        nombre: String,
        descripcion: String,
        precio: Double,
        stock: Int,
        categoria: String,
        tipo: String
    ) {
        // Creamos un ID único (usando la hora actual, es un truco para la demo)
        val nuevoId = System.currentTimeMillis()

        val nuevoProducto = Producto(
            id = nuevoId,
            nombre = nombre,
            descripcion = descripcion,
            precio = precio,
            stock = stock,
            categoria = categoria,
            tipo = tipo
        )

        // Usamos .update() que es más seguro para StateFlow
        _productos.update { listaActual ->
            listaActual + nuevoProducto
        }
    }

    // --- MANTENEMOS TUS FUNCIONES (¡están geniales!) ---
    // (Solo cambié .value = por .update() que es más robusto)
    fun actualizarProducto(producto: Producto) {
        _productos.update { listaActual ->
            listaActual.map {
                if (it.id == producto.id) producto else it
            }
        }
    }

    fun eliminarProducto(producto: Producto) {
        _productos.update { listaActual ->
            listaActual.filter { it.id != producto.id }
        }
    }
}