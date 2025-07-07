package com.example.proyectofinal.ui

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.proyectofinal.model.Mensaje

@Database(entities = [Mensaje::class], version = 2)
abstract class AppDatabase : RoomDatabase() {
    abstract fun mensajeDao(): MensajeDao

    companion object {
        @Volatile private var instancia: AppDatabase? = null

        fun obtenerInstancia(context: Context): AppDatabase {
            return instancia ?: synchronized(this) {
                Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "chat_database"
                ).build().also { instancia = it }
            }
        }
    }
}
