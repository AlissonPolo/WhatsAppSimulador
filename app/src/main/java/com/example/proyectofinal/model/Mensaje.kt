package com.example.proyectofinal.model

import android.graphics.Bitmap
import android.net.Uri
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey

@Entity(tableName = "mensajes")
data class Mensaje(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val contactoId: Int,
    val texto: String? = null,
    val esEmisorUsuario: Boolean,
    val timestamp: Long,
    @Ignore var imagen: Bitmap? = null,
    var archivoUriString: String? = null
) {
    constructor(
        id: Int,
        contactoId: Int,
        texto: String?,
        esEmisorUsuario: Boolean,
        timestamp: Long,
        archivoUriString: String?
    ) : this(id, contactoId, texto, esEmisorUsuario, timestamp, null, archivoUriString)

}
