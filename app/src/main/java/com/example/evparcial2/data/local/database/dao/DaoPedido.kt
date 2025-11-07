// --- ¡¡ARREGLO #1: El paquete AHORA SÍ coincide con la carpeta!! ---
package com.example.evparcial2.data.local.database.dao

// --- ¡¡ARREGLO #2: Importamos las "etiquetas" de Room!! ---
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.evparcial2.data.local.database.entities.EntidadPedido
import kotlinx.coroutines.flow.Flow

// --- ¡¡ARREGLO #3: Lo convertimos de 'class' a 'interface'!! ---
@Dao // <-- Le decimos a Room que esto es un DAO
interface DaoPedido {

    // (Toda tu lista "falsa" de pedidos se borra)

    // --- ¡¡ARREGLO #4: Añadimos etiquetas a cada función!! ---

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertar(pedido: EntidadPedido) // (Room no necesita devolver Long aquí)

    @Query("SELECT * FROM tabla_pedido") // Le decimos qué comando SQL ejecutar
    fun obtenerTodos(): Flow<List<EntidadPedido>>

    @Query("SELECT * FROM tabla_pedido WHERE id = :id")
    suspend fun obtenerPorId(id: Long): EntidadPedido?

    @Query("SELECT * FROM tabla_pedido WHERE usuarioId = :usuarioId")
    fun obtenerPorUsuario(usuarioId: Long): Flow<List<EntidadPedido>>

    @Update
    suspend fun actualizar(pedido: EntidadPedido)
}