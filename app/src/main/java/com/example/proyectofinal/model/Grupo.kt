package com.example.proyectofinal.model

data class Grupo(
    val id: Int,
    val nombre: String,
    val integrantes: List<Contacto>
)