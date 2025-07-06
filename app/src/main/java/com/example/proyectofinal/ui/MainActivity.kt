package com.example.proyectofinal.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.proyectofinal.R
import com.example.proyectofinal.adapter.ContactosAdapter
import com.example.proyectofinal.model.Contacto
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.floatingactionbutton.FloatingActionButton
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class MainActivity : AppCompatActivity() {

    private lateinit var contactosAdapter: ContactosAdapter

    private fun getHoraActual(): String {
        val sdf = SimpleDateFormat("hh:mm a", Locale.getDefault())
        return sdf.format(Date())
    }
    private fun getTimestampActual(): Long {
        return System.currentTimeMillis()
    }
    private fun actualizarUltimoMensaje(contactoId: Int, mensaje: String) {
        val hora = getHoraActual()
        val index = contactos.indexOfFirst { it.id == contactoId }
        if (index != -1) {
            contactos[index] = contactos[index].copy(
                ultimoMensaje = mensaje,
                horaUltimoMensaje = hora
            )
            contactosAdapter.notifyItemChanged(index)
            Log.d("MainActivity", "Contacto actualizado: ${contactos[index].nombre} - $mensaje a las $hora")
        }
    }


    private var contactos = mutableListOf(
        Contacto(1, "Alisson Polo", "¿Cómo estás?", getHoraActual()),
        Contacto(2, "Juan Pérez", "Ok, nos vemos", getHoraActual()),
        Contacto(3, "María López", "Gracias!", getHoraActual())
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val toolbar = findViewById<MaterialToolbar>(R.id.topAppBar)
        setSupportActionBar(toolbar)

        val fabAgregar = findViewById<FloatingActionButton>(R.id.fabAgregar)
        fabAgregar.setOnClickListener {
            startActivity(Intent(this, ListaContactosActivity::class.java))
        }
        val btnChats = findViewById<ImageButton>(R.id.btnChats)
        btnChats.setOnClickListener {
            Toast.makeText(this, "Botón Chats pulsado", Toast.LENGTH_SHORT).show()

        }


        val rvContactos = findViewById<RecyclerView>(R.id.rvContactos)
        rvContactos.layoutManager = LinearLayoutManager(this)

        contactosAdapter = ContactosAdapter(contactos) { contacto ->
            val intent = Intent(this, ChatActivity::class.java)
            intent.putExtra("contactoId", contacto.id)
            intent.putExtra("contactoNombre", contacto.nombre)
            startActivity(intent)
        }
        rvContactos.adapter = contactosAdapter

        findViewById<ImageButton>(R.id.btnEstados).setOnClickListener {
            startActivity(Intent(this, EstadosActivity::class.java))
        }

        findViewById<ImageButton>(R.id.btnGrupos).setOnClickListener {
            startActivity(Intent(this, GruposActivity::class.java))
        }

        findViewById<ImageButton>(R.id.btnChats).setOnClickListener {
            // Actualizar lista con hora y mensaje nuevos sin crear nuevo adapter
            refrescarContactos()
            Toast.makeText(this, "Lista de chats actualizada", Toast.LENGTH_SHORT).show()
        }

        findViewById<ImageButton>(R.id.btnLlamadas).setOnClickListener {
            startActivity(Intent(this, LlamadasActivity::class.java))
        }
    }

    private fun refrescarContactos() {
        Log.d("MainActivity", "refrescarContactos llamado")
        val hora = getHoraActual()
        contactos.forEachIndexed { index, contacto ->
            contactos[index] = contacto.copy(
                ultimoMensaje = "Nuevo mensaje a las $hora",
                horaUltimoMensaje = hora
            )
            Log.d("MainActivity", "Contacto actualizado: ${contacto.nombre} - Nuevo mensaje a las $hora")
        }
        contactosAdapter.notifyDataSetChanged()
    }



    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.top_app_bar_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menu_camara -> {
                Toast.makeText(this, "Cámara clickeada", Toast.LENGTH_SHORT).show()
                true
            }
            R.id.menu_perfil -> {
                Toast.makeText(this, "Perfil clickeado", Toast.LENGTH_SHORT).show()
                true
            }
            R.id.menu_ajustes -> {
                Toast.makeText(this, "Ajustes clickeado", Toast.LENGTH_SHORT).show()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}
