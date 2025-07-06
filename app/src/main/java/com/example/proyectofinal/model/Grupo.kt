package com.example.proyectofinal.model

import Contacto

data class Grupo(
    val id: Int,
    val nombre: String,
    val integrantes: List<Contacto>
)