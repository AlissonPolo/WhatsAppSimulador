package com.example.proyectofinal.model

import Contacto
import com.example.proyectofinal.R
import java.text.SimpleDateFormat
import java.util.*

fun obtenerHoraActual(): String {
    val sdf = SimpleDateFormat("hh:mm a", Locale.getDefault())
    return sdf.format(Date())
}

val listaGlobalContactos = mutableListOf(
    Contacto(
        1, "Alisson Polo", "¿Cómo estás?", obtenerHoraActual(), fotoPerfilResId = R.drawable.mujer1,
        mensajes = mutableListOf(
            Mensaje(contactoId = 1, texto = "¿Cómo estás?", esEmisorUsuario = false, timestamp = System.currentTimeMillis())
        )
    ),
    Contacto(
        2, "Juan Pérez", "Ok, nos vemos", obtenerHoraActual(), fotoPerfilResId = R.drawable.hombre1,
        mensajes = mutableListOf(
            Mensaje(contactoId = 2, texto = "Ok, nos vemos", esEmisorUsuario = false, timestamp = System.currentTimeMillis())
        )
    ),
    Contacto(
        3, "María López", "Gracias!", obtenerHoraActual(), fotoPerfilResId = R.drawable.mujer2,
        mensajes = mutableListOf(
            Mensaje(contactoId = 3, texto = "Gracias!", esEmisorUsuario = false, timestamp = System.currentTimeMillis())
        )
    ),
    Contacto(
        4, "Carlos Díaz", "Nos hablamos luego", obtenerHoraActual(), fotoPerfilResId = R.drawable.hombre2,
        mensajes = mutableListOf(
            Mensaje(contactoId = 4, texto = "Nos hablamos luego", esEmisorUsuario = false, timestamp = System.currentTimeMillis())
        )
    ),
    Contacto(
        5, "Laura Méndez", "Hola, ¿todo bien?", obtenerHoraActual(), fotoPerfilResId = R.drawable.mujer3,
        mensajes = mutableListOf(
            Mensaje(contactoId = 5, texto = "Hola, ¿todo bien?", esEmisorUsuario = false, timestamp = System.currentTimeMillis())
        )
    ),
    Contacto(
        6, "Pedro Ruiz", "¡Nos vemos mañana!", obtenerHoraActual(), fotoPerfilResId = R.drawable.hombre3,
        mensajes = mutableListOf(
            Mensaje(contactoId = 6, texto = "¡Nos vemos mañana!", esEmisorUsuario = false, timestamp = System.currentTimeMillis())
        )
    ),
    Contacto(
        7, "Carlos Díaz", "Nos hablamos luego", obtenerHoraActual(), fotoPerfilResId = R.drawable.hombre4,
        mensajes = mutableListOf(
            Mensaje(contactoId = 7, texto = "Nos hablamos luego", esEmisorUsuario = false, timestamp = System.currentTimeMillis())
        )
    ),
    Contacto(
        8, "Laura Méndez", "Hola, ¿todo bien?", obtenerHoraActual(), fotoPerfilResId = R.drawable.mujer4,
        mensajes = mutableListOf(
            Mensaje(contactoId = 8, texto = "Hola, ¿todo bien?", esEmisorUsuario = false, timestamp = System.currentTimeMillis())
        )
    ),
    Contacto(
        9, "Pedro Ruiz", "¡Nos vemos mañana!", obtenerHoraActual(),
        mensajes = mutableListOf(
            Mensaje(contactoId = 9, texto = "¡Nos vemos mañana!", esEmisorUsuario = false, timestamp = System.currentTimeMillis())
        )
    ),
    Contacto(
        10, "Daniela Torres", "Estoy ocupada, luego te llamo", obtenerHoraActual(), fotoPerfilResId = R.drawable.mujer5,
        mensajes = mutableListOf(
            Mensaje(contactoId = 10, texto = "Estoy ocupada, luego te llamo", esEmisorUsuario = false, timestamp = System.currentTimeMillis())
        )
    ),
    Contacto(
        11, "Luis Gómez", "¿Tienes los apuntes?", obtenerHoraActual(),
        mensajes = mutableListOf(
            Mensaje(contactoId = 11, texto = "¿Tienes los apuntes?", esEmisorUsuario = false, timestamp = System.currentTimeMillis())
        )
    )
)
