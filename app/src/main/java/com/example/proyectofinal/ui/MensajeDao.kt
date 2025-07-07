package com.example.proyectofinal.ui

import androidx.room.Dao
import androidx.room.Database
import androidx.room.Insert
import androidx.room.Query
import androidx.room.RoomDatabase
import com.example.proyectofinal.model.Mensaje

@Dao
interface MensajeDao {
    @Query("SELECT * FROM mensajes WHERE contactoId = :contactoId ORDER BY timestamp ASC")
    fun getMensajesPorContacto(contactoId: Int): List<Mensaje>

    @Insert
    fun insertarMensaje(mensaje: Mensaje)
}

