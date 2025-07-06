package com.example.proyectofinal.model

import com.example.proyectofinal.R
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

data class Contacto(
    val id: Int,
    val nombre: String,
    val ultimoMensaje: String = "",
    val horaUltimoMensaje: String = "",
    val fotoPerfilResId: Int = R.drawable.ic_person,
    val mensajes: MutableList<Mensaje> = mutableListOf()
)