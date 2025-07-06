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
import com.example.proyectofinal.model.listaGlobalContactos
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity() {

    private lateinit var contactosAdapter: ContactosAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val toolbar = findViewById<MaterialToolbar>(R.id.topAppBar)
        setSupportActionBar(toolbar)

        val fabAgregar = findViewById<FloatingActionButton>(R.id.fabAgregar)
        fabAgregar.setOnClickListener {
            startActivity(Intent(this, ListaContactosActivity::class.java))
        }

        val rvContactos = findViewById<RecyclerView>(R.id.rvContactos)
        rvContactos.layoutManager = LinearLayoutManager(this)

        contactosAdapter = ContactosAdapter(
            contactos = listaGlobalContactos,
            onClick = { contacto ->
                val intent = Intent(this, ChatActivity::class.java)
                intent.putExtra("contactoId", contacto.id)
                intent.putExtra("contactoNombre", contacto.nombre)
                startActivity(intent)
            },
            onFotoClick = { contacto ->
                val intent = Intent(this, VerFotoActivity::class.java)
                intent.putExtra("fotoResId", contacto.fotoPerfilResId)
                startActivity(intent)
            }
        )

        rvContactos.adapter = contactosAdapter

        findViewById<ImageButton>(R.id.btnEstados).setOnClickListener {
            startActivity(Intent(this, EstadosActivity::class.java))
        }

        findViewById<ImageButton>(R.id.btnGrupos).setOnClickListener {
            startActivity(Intent(this, GruposActivity::class.java))
        }

        findViewById<ImageButton>(R.id.btnChats).setOnClickListener {
            contactosAdapter.notifyDataSetChanged()
            Toast.makeText(this, "Lista de chats actualizada", Toast.LENGTH_SHORT).show()
        }

        findViewById<ImageButton>(R.id.btnLlamadas).setOnClickListener {
            startActivity(Intent(this, LlamadasActivity::class.java))
        }
    }

    override fun onResume() {
        super.onResume()
        // Refrescar la lista cuando vuelvas del ChatActivity
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
