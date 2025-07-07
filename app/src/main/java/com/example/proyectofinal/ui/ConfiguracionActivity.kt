package com.example.proyectofinal.ui

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.proyectofinal.databinding.ActivityConfiguracionBinding

class ConfiguracionActivity : AppCompatActivity() {

    private lateinit var binding: ActivityConfiguracionBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityConfiguracionBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Toolbar
        val toolbar = binding.topAppBarConfiguracion
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Ajustes"

        toolbar.setNavigationOnClickListener {
            finish()
        }

        // Listeners para cada opción
        binding.llCuenta.setOnClickListener {
            Toast.makeText(this, "Cuenta clickeado", Toast.LENGTH_SHORT).show()
        }
        binding.llPrivacidad.setOnClickListener {
            Toast.makeText(this, "Privacidad clickeado", Toast.LENGTH_SHORT).show()
        }
        binding.llAvatar.setOnClickListener {
            Toast.makeText(this, "Avatar clickeado", Toast.LENGTH_SHORT).show()
        }
        binding.llChats.setOnClickListener {
            Toast.makeText(this, "Chats clickeado", Toast.LENGTH_SHORT).show()
        }
        binding.llNotificaciones.setOnClickListener {
            Toast.makeText(this, "Notificaciones clickeado", Toast.LENGTH_SHORT).show()
        }
        binding.llAlmacenamiento.setOnClickListener {
            Toast.makeText(this, "Almacenamiento y datos clickeado", Toast.LENGTH_SHORT).show()
        }
        binding.llAccesibilidad.setOnClickListener {
            Toast.makeText(this, "Accesibilidad clickeado", Toast.LENGTH_SHORT).show()
        }
        binding.llIdioma.setOnClickListener {
            Toast.makeText(this, "Idioma clickeado", Toast.LENGTH_SHORT).show()
        }
        binding.llAyuda.setOnClickListener {
            Toast.makeText(this, "Ayuda clickeado", Toast.LENGTH_SHORT).show()
        }
        binding.llInvitarAmigos.setOnClickListener {
            Toast.makeText(this, "Invitar amigos clickeado", Toast.LENGTH_SHORT).show()
        }
        binding.llActualizar.setOnClickListener {
            Toast.makeText(this, "Actualizar aplicación clickeado", Toast.LENGTH_SHORT).show()
        }
    }
}
