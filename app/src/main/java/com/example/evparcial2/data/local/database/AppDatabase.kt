package com.example.evparcial2.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
// --- ¡¡ESTOS IMPORTS SON LA CLAVE!! ---
// (Nos aseguramos de que todos apunten a '...database.dao...')
import com.example.evparcial2.data.local.database.dao.DaoPedido
import com.example.evparcial2.data.local.database.dao.DaoProducto
import com.example.evparcial2.data.local.database.dao.DaoUsuario
// (Y que estos apunten a '...database.entities...')
import com.example.evparcial2.data.local.database.entities.EntidadPedido
import com.example.evparcial2.data.local.database.entities.EntidadProducto
import com.example.evparcial2.data.local.database.entities.EntidadUsuario
// --- FIN DE LOS IMPORTS ---

@Database(
    entities = [
        EntidadProducto::class,
        EntidadUsuario::class,
        EntidadPedido::class
    ],
    version = 1
)
abstract class AppDatabase : RoomDatabase() {

    // (Estas líneas deberían dejar de estar en rojo)
    abstract fun daoProducto(): DaoProducto
    abstract fun daoUsuario(): DaoUsuario
    abstract fun daoPedido(): DaoPedido

}