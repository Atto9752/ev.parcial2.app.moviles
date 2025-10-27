package com.example.evparcial2.domain.viewmodels

import androidx.lifecycle.ViewModel
import com.example.evparcial2.data.model.Producto
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class ViewModelProductos : ViewModel() {
    private val _productos = MutableStateFlow<List<Producto>>(emptyList())
    val productos: StateFlow<List<Producto>> = _productos.asStateFlow()

    init {
        _productos.value = listOf(
            Producto(
                id = 1,
                nombre = "Casa moderna",
                descripcion = "Casa estilo moderno con piscina, 3 dormitorios, 2 baños",
                precio = 250000.0,
                stock = 1,
                categoria = "Venta",
                tipo = "venta"
            ),
            Producto(
                id = 2,
                nombre = "Departamento de lujo",
                descripcion = "Departamento amplio en el centro de la ciudad, con acceso a piscina temperada, gimnasio, etc.",
                precio = 300000.0,
                stock = 2,
                categoria = "Venta",
                tipo = "venta"
            ),
            Producto(
                id = 3,
                nombre = "Departamento para arriendo",
                descripcion = "Departamento pequeño en el segundo piso del edificio. Ideal para estudiantes.",
                precio = 450.0,
                stock = 5,
                categoria = "Arriendo",
                tipo = "arriendo"
            ),
            Producto(
                id = 4,
                nombre = "Oficina comercial",
                descripcion = "Oficina en zona céntrica, 40m², ideal para profesionales",
                precio = 800.0,
                stock = 3,
                categoria = "Arriendo",
                tipo = "arriendo"
            ),
            Producto(
                id = 5,
                nombre = "Casa playa",
                descripcion = "Casa frente al mar con vista panorámica, 4 dormitorios, piscina",
                precio = 450000.0,
                stock = 1,
                categoria = "Venta",
                tipo = "venta"
            )
        )
    }

    fun agregarProducto(producto: Producto) {
        _productos.value = _productos.value + producto
    }

    fun actualizarProducto(producto: Producto) {
        _productos.value = _productos.value.map {
            if (it.id == producto.id) producto else it
        }
    }

    fun eliminarProducto(producto: Producto) {
        _productos.value = _productos.value.filter { it.id != producto.id }
    }
}