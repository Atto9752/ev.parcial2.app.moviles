// --- ¡¡ARREGLO #1: El paquete AHORA SÍ coincide con la carpeta!! ---
package com.example.evparcial2.data.local.database.entities

// --- ¡¡ARREGLO #2: Importamos las "etiquetas" de Room!! ---
import androidx.room.Entity
import androidx.room.PrimaryKey

// --- ¡¡ARREGLO #3: Le decimos a Room que esto es una "tabla"!! ---
@Entity(tableName = "tabla_producto")
data class EntidadProducto(

    // --- ¡¡ARREGLO #4: Le decimos a Room cuál es el ID!! ---
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,

    // El resto de tu código (tus campos) estaba perfecto
    val nombre: String,
    val descripcion: String,
    val precio: Double,
    val stock: Int,
    val categoria: String,
    val tipo: String = "venta",
    val imagenUrl: String? = null,
    val disponible: Boolean = true,
    val fechaCreacion: Long = System.currentTimeMillis()
)