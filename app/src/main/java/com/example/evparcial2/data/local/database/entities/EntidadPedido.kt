// --- ¡¡ARREGLO #1: El paquete AHORA SÍ coincide con la carpeta!! ---
package com.example.evparcial2.data.local.database.entities

// --- ¡¡ARREGLO #2: Importamos las "etiquetas" de Room!! ---
import androidx.room.Entity
import androidx.room.PrimaryKey

// --- ¡¡ARREGLO #3: Le decimos a Room que esto es una "tabla"!! ---
@Entity(tableName = "tabla_pedido")
data class EntidadPedido(

    // --- ¡¡ARREGLO #4: Le decimos a Room cuál es el ID!! ---
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,

    // El resto de tus campos estaba perfecto
    val usuarioId: Long,
    val fechaPedido: Long = System.currentTimeMillis(),
    val total: Double,
    val estado: String,
    val direccionEntrega: String,
    val metodoPago: String = "tarjeta",
    val productosJson: String = ""
)