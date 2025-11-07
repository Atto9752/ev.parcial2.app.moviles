// --- ¡¡ARREGLO #1: El paquete AHORA SÍ coincide con la carpeta!! ---
package com.example.evparcial2.data.local.database.dao

// --- ¡¡ARREGLO #2: Importamos las "etiquetas" de Room!! ---
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.evparcial2.data.local.database.entities.EntidadProducto
import kotlinx.coroutines.flow.Flow

// --- ¡¡ARREGLO #3: Lo convertimos de 'class' a 'interface'!! ---
@Dao // <-- Le decimos a Room que esto es un DAO (un set de comandos)
interface DaoProducto {

    // (Toda tu lista "falsa" y el 'init' se borran,
    // porque ahora la base de datos real se encargará de guardar)

    // --- ¡¡ARREGLO #4: Añadimos etiquetas a cada función!! ---

    @Insert(onConflict = OnConflictStrategy.REPLACE) // Si el producto existe, lo reemplaza
    suspend fun insertar(producto: EntidadProducto) // (Room no necesita devolver un Long aquí)

    @Query("SELECT * FROM tabla_producto") // Le decimos qué comando SQL ejecutar
    fun obtenerTodos(): Flow<List<EntidadProducto>>

    @Query("SELECT * FROM tabla_producto WHERE id = :id")
    suspend fun obtenerPorId(id: Long): EntidadProducto?

    @Query("SELECT * FROM tabla_producto WHERE nombre LIKE '%' || :query || '%' OR descripcion LIKE '%' || :query || '%'")
    fun buscar(query: String): Flow<List<EntidadProducto>>

    @Query("SELECT * FROM tabla_producto WHERE tipo = :tipo")
    fun obtenerPorTipo(tipo: String): Flow<List<EntidadProducto>>

    @Update
    suspend fun actualizar(producto: EntidadProducto)

    @Delete
    suspend fun eliminar(producto: EntidadProducto)
}