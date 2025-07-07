package com.example.proyectofinal.ui

import Contacto
import android.os.Bundle
import android.widget.EditText
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.proyectofinal.R
import com.example.proyectofinal.adapter.MensajesAdapter
import com.example.proyectofinal.model.Mensaje
import com.example.proyectofinal.model.listaGlobalContactos
import java.text.SimpleDateFormat
import java.util.*

class ChatActivity : AppCompatActivity() {

    private lateinit var adapter: MensajesAdapter
    private lateinit var contacto: Contacto
    private lateinit var mensajes: MutableList<Mensaje>
    private var siguienteIdMensaje = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)

        val contactoId = intent.getIntExtra("contactoId", -1)
        contacto = listaGlobalContactos.first { it.id == contactoId }
        mensajes = contacto.mensajes
        title = contacto.nombre

        val rvMensajes: RecyclerView = findViewById(R.id.recyclerView)
        val etMensaje: EditText = findViewById(R.id.etMessage)
        val btnEnviar: ImageButton = findViewById(R.id.btnSend)
        val imgPerfil = findViewById<ImageView>(R.id.imgChatPerfil)
        val txtNombre = findViewById<TextView>(R.id.txtChatNombre)

        imgPerfil.setImageResource(contacto.fotoPerfilResId)
        txtNombre.text = contacto.nombre

        adapter = MensajesAdapter(mensajes)
        rvMensajes.layoutManager = LinearLayoutManager(this)
        rvMensajes.adapter = adapter

        btnEnviar.setOnClickListener {
            val texto = etMensaje.text.toString().trim()
            if (texto.isNotEmpty()) {
                val mensaje = Mensaje(siguienteIdMensaje++, texto, true, System.currentTimeMillis())
                contacto.agregarMensaje(mensaje) // Esto actualiza mensajes, último mensaje y hora
                adapter.notifyItemInserted(mensajes.size - 1)
                rvMensajes.scrollToPosition(mensajes.size - 1)
                etMensaje.text.clear()

                responderAutomaticamente()
            }
        }
    }

    private fun responderAutomaticamente() {
        val respuesta = Mensaje(
            siguienteIdMensaje++,
            "Respuesta automática de ${contacto.nombre}",
            false,
            System.currentTimeMillis()
        )
        contacto.agregarMensaje(respuesta)
        adapter.notifyItemInserted(mensajes.size - 1)
    }

    private fun formatearHora(timestamp: Long): String {
        val sdf = SimpleDateFormat("hh:mm a", Locale.getDefault())
        return sdf.format(Date(timestamp))
    }
}
