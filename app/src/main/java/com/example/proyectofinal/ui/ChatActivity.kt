package com.example.proyectofinal.ui

import android.content.Intent
import android.os.Bundle
import android.widget.EditText
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.proyectofinal.R
import com.example.proyectofinal.adapter.MensajesAdapter
import com.example.proyectofinal.model.Mensaje
import java.text.SimpleDateFormat
import java.util.*

class ChatActivity : AppCompatActivity() {

    private val mensajes = mutableListOf<Mensaje>()
    private var siguienteIdMensaje = 1
    private lateinit var adapter: MensajesAdapter
    private var contactoId = -1
    private var contactoNombre = "Chat"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)

        contactoId = intent.getIntExtra("contactoId", -1)
        contactoNombre = intent.getStringExtra("contactoNombre") ?: "Chat"
        title = contactoNombre

        val rvMensajes: RecyclerView = findViewById(R.id.recyclerView)
        val etMensaje: EditText = findViewById(R.id.etMessage)
        val btnEnviar: ImageButton = findViewById(R.id.btnSend)

        adapter = MensajesAdapter(mensajes)
        rvMensajes.layoutManager = LinearLayoutManager(this)
        rvMensajes.adapter = adapter

        btnEnviar.setOnClickListener {
            val texto = etMensaje.text.toString().trim()
            if (texto.isNotEmpty()) {
                val mensaje = Mensaje(siguienteIdMensaje++, texto, true, System.currentTimeMillis())
                mensajes.add(mensaje)
                adapter.notifyItemInserted(mensajes.size - 1)
                rvMensajes.scrollToPosition(mensajes.size - 1)
                etMensaje.text.clear()

                // Actualizamos el resultado para MainActivity
                enviarResultado(mensaje)

                responderAutomaticamente(contactoNombre)
            }
        }
    }

    private fun enviarResultado(mensaje: Mensaje) {
        val intent = Intent()
        intent.putExtra("contactoId", contactoId)
        intent.putExtra("ultimoMensaje", mensaje.texto)
        intent.putExtra("horaMensaje", formatearHora(mensaje.timestamp))
        setResult(RESULT_OK, intent)
    }

    private fun formatearHora(timestamp: Long): String {
        val sdf = SimpleDateFormat("hh:mm a", Locale.getDefault())
        return sdf.format(Date(timestamp))
    }

    private fun responderAutomaticamente(nombre: String) {
        val respuesta = Mensaje(
            siguienteIdMensaje++,
            "Respuesta automática de $nombre",
            false,
            System.currentTimeMillis()
        )
        mensajes.add(respuesta)
        adapter.notifyItemInserted(mensajes.size - 1)
    }
}
