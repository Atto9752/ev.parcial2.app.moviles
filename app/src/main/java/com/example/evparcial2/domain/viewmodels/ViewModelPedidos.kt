package com.example.evparcial2.domain.viewmodels

// imports
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.*
import com.example.evparcial2.data.model.Pedido
import com.example.evparcial2.data.model.Producto
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class ViewModelPedidos : ViewModel() {

    private val _carrito = mutableStateListOf<Producto>()
    val carrito: List<Producto> get() = _carrito

    private val _pedidos = mutableStateListOf<Pedido>()
    val pedidos: List<Pedido> get() = _pedidos

    // StateFlow (datos en tiempo real)
    private val _estadoCarrito = MutableStateFlow(EstadoCarrito())
    val estadoCarrito: StateFlow<EstadoCarrito> = _estadoCarrito.asStateFlow()

    fun agregarAlCarrito(producto: Producto) {
        _carrito.add(producto)
        actualizarEstadoCarrito()
    }

    fun quitarDelCarrito(producto: Producto) {
        _carrito.remove(producto)
        actualizarEstadoCarrito()
    }

    fun vaciarCarrito() {
        _carrito.clear()
        actualizarEstadoCarrito()
    }
    fun confirmarPedido() {
        val nuevoPedido = Pedido(
            id = System.currentTimeMillis(),
            productos = _carrito.toList(),
            total = _carrito.sumOf { it.precio },
            fecha = System.currentTimeMillis(),
            estado = "confirmado"
        )
        _pedidos.add(nuevoPedido)
        _carrito.clear()
        actualizarEstadoCarrito()
    }

    private fun actualizarEstadoCarrito() {
        _estadoCarrito.value = EstadoCarrito(
            cantidadItems = _carrito.size,
            total = _carrito.sumOf { it.precio },
            items = _carrito.toList()
        )
    }
}

data class EstadoCarrito(
    val cantidadItems: Int = 0,
    val total: Double = 0.0,
    val items: List<Producto> = emptyList()
)

