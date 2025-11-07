// --- ¡¡ARREGLO #1: El paquete AHORA SÍ coincide con la carpeta!! ---
package com.example.evparcial2.data.local.database.dao

// --- ¡¡ARREGLO #2: Importamos las "etiquetas" de Room!! ---
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.evparcial2.data.local.database.entities.EntidadUsuario
import kotlinx.coroutines.flow.Flow

// --- ¡¡ARREGLO #3: Lo convertimos de 'class' a 'interface'!! ---
@Dao // <-- Le decimos a Room que esto es un DAO
interface DaoUsuario {

    // (Toda tu lista "falsa" de usuarios se borra,
    // porque ahora la base de datos real se encargará de guardar)

    // --- ¡¡ARREGLO #4: Añadimos etiquetas a cada función!! ---

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertar(usuario: EntidadUsuario) // (La 'class' se borra, las funciones se quedan)

    @Query("SELECT * FROM tabla_usuario") // Le decimos qué comando SQL ejecutar
    fun obtenerTodos(): Flow<List<EntidadUsuario>>

    @Query("SELECT * FROM tabla_usuario WHERE id = :id")
    suspend fun obtenerPorId(id: Long): EntidadUsuario?

    @Query("SELECT * FROM tabla_usuario WHERE email = :email AND contrasena = :contrasena")
    suspend fun login(email: String, contrasena: String): EntidadUsuario?

    @Query("SELECT * FROM tabla_usuario WHERE email = :email")
    suspend fun obtenerPorEmail(email: String): EntidadUsuario?

    @Update
    suspend fun actualizar(usuario: EntidadUsuario)
}