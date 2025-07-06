package com.example.proyectofinal.model

data class Mensaje(
    val id: Int,
    val texto: String,
    val esEmisorUsuario: Boolean,
    val timestamp: Long
)