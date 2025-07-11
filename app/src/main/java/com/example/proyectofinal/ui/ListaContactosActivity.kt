package com.example.proyectofinal.ui

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.proyectofinal.R
import com.example.proyectofinal.adapter.ContactosListaAdapter
import com.example.proyectofinal.databinding.ActivityListaContactosBinding
import com.example.proyectofinal.model.listaGlobalContactos

class ListaContactosActivity : AppCompatActivity() {

    private lateinit var binding: ActivityListaContactosBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityListaContactosBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Configurar toolbar
        val toolbar = binding.topAppBarContactos
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        toolbar.setNavigationOnClickListener { finish() }

        // Inflar y añadir título personalizado
        val customTitleView = layoutInflater.inflate(R.layout.toolbar_title_with_subtitle, null)
        val tvSubtitle = customTitleView.findViewById<TextView>(R.id.tvSubtitle)
        tvSubtitle.text = "${listaGlobalContactos.size} contactos"
        toolbar.addView(customTitleView)

        // Ordenar y mostrar contactos
        val contactosOrdenados = listaGlobalContactos.sortedBy { it.nombre }

        val adapter = ContactosListaAdapter(
            contactosOrdenados,
            onClick = { contacto ->
                if (contacto.mensajes.isNotEmpty()) {
                    val intent = Intent(this, ChatActivity::class.java)
                    intent.putExtra("contactoId", contacto.id)
                    startActivity(intent)
                } else {
                    Toast.makeText(this, "Sin conversaciones previas", Toast.LENGTH_SHORT).show()
                }
            },
            onFotoClick = { contacto ->
                val intent = Intent(this, VerFotoActivity::class.java)
                intent.putExtra("fotoResId", contacto.fotoPerfilResId)
                startActivity(intent)
            }
        )

        binding.rvContactos.layoutManager = LinearLayoutManager(this)
        binding.rvContactos.adapter = adapter
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_contactos, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_search -> {
                Toast.makeText(this, "Buscar clickeado", Toast.LENGTH_SHORT).show()
                true
            }

            R.id.action_more -> {
                Toast.makeText(this, "Más opciones clickeado", Toast.LENGTH_SHORT).show()
                true
            }

            android.R.id.home -> {
                finish()
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }
}
