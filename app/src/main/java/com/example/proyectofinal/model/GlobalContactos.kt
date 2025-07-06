package com.example.proyectofinal.model

import Contacto
import java.text.SimpleDateFormat
import java.util.*

fun obtenerHoraActual(): String {
    val sdf = SimpleDateFormat("hh:mm a", Locale.getDefault())
    return sdf.format(Date())
}

val listaGlobalContactos = mutableListOf(
    Contacto(
        1, "Alisson Polo", "¿Cómo estás?", obtenerHoraActual(),
        mensajes = mutableListOf(
            Mensaje(1, "¿Cómo estás?", false, System.currentTimeMillis())
        )
    ),
    Contacto(
        2, "Juan Pérez", "Ok, nos vemos", obtenerHoraActual(),
        mensajes = mutableListOf(
            Mensaje(2, "Ok, nos vemos", false, System.currentTimeMillis())
        )
    ),
    Contacto(
        3, "María López", "Gracias!", obtenerHoraActual(),
        mensajes = mutableListOf(
            Mensaje(3, "Gracias!", false, System.currentTimeMillis())
        )
    ),
    Contacto(
        4, "Carlos Díaz", "Nos hablamos luego", obtenerHoraActual(),
        mensajes = mutableListOf(
            Mensaje(4, "Nos hablamos luego", false, System.currentTimeMillis())
        )
    ),
    Contacto(
        5, "Laura Méndez", "Hola, ¿todo bien?", obtenerHoraActual(),
        mensajes = mutableListOf(
            Mensaje(5, "Hola, ¿todo bien?", false, System.currentTimeMillis())
        )
    ),
    Contacto(
        6, "Pedro Ruiz", "¡Nos vemos mañana!", obtenerHoraActual(),
        mensajes = mutableListOf(
            Mensaje(6, "¡Nos vemos mañana!", false, System.currentTimeMillis())
        )
    ),
    Contacto(
        7, "Carlos Díaz", "Nos hablamos luego", obtenerHoraActual(),
        mensajes = mutableListOf(
            Mensaje(4, "Nos hablamos luego", false, System.currentTimeMillis())
        )
    ),
    Contacto(
        8, "Laura Méndez", "Hola, ¿todo bien?", obtenerHoraActual(),
        mensajes = mutableListOf(
            Mensaje(5, "Hola, ¿todo bien?", false, System.currentTimeMillis())
        )
    ),
    Contacto(
        9, "Pedro Ruiz", "¡Nos vemos mañana!", obtenerHoraActual(),
        mensajes = mutableListOf(
            Mensaje(6, "¡Nos vemos mañana!", false, System.currentTimeMillis())
        )
    ),
    Contacto(
        10, "Daniela Torres", "Estoy ocupada, luego te llamo", obtenerHoraActual(),
        mensajes = mutableListOf(
            Mensaje(7, "Estoy ocupada, luego te llamo", false, System.currentTimeMillis())
        )
    ),
    Contacto(
        11, "Luis Gómez", "¿Tienes los apuntes?", obtenerHoraActual(),
        mensajes = mutableListOf(
            Mensaje(8, "¿Tienes los apuntes?", false, System.currentTimeMillis())
        )
    )


)
