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

        title = "Configuración"

        binding.llNotificaciones.setOnClickListener {
            Toast.makeText(this, "Notificaciones clickeado", Toast.LENGTH_SHORT).show()
            // Aquí podrías abrir otra actividad o diálogo
        }

        binding.llPrivacidad.setOnClickListener {
            Toast.makeText(this, "Privacidad clickeado", Toast.LENGTH_SHORT).show()
        }

        binding.llIdioma.setOnClickListener {
            Toast.makeText(this, "Idioma clickeado", Toast.LENGTH_SHORT).show()
        }
    }
}
