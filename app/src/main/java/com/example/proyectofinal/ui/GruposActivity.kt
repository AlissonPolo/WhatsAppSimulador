package com.example.proyectofinal.ui

import Contacto
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.proyectofinal.adapter.GruposAdapter
import com.example.proyectofinal.databinding.ActivityGruposBinding
import com.example.proyectofinal.model.Grupo

class GruposActivity : AppCompatActivity() {

    private lateinit var binding: ActivityGruposBinding

    private val grupos = listOf(
        Grupo(1, "Amigos", listOf(Contacto(1, "Alisson"), Contacto(2, "Juan"))),
        Grupo(2, "Estudio", listOf(Contacto(3, "María"), Contacto(4, "Pedro")))
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGruposBinding.inflate(layoutInflater)
        setContentView(binding.root)

        title = "Grupos"

        binding.rvGrupos.layoutManager = LinearLayoutManager(this)
        binding.rvGrupos.adapter = GruposAdapter(grupos) { grupo ->
            // Aquí puedes manejar el click en un grupo
        }
    }
}
