package com.example.proyectofinal.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.proyectofinal.adapter.EstadosAdapter
import com.example.proyectofinal.databinding.ActivityEstadosBinding
import com.example.proyectofinal.model.Estado

class EstadosActivity : AppCompatActivity() {

    private lateinit var binding: ActivityEstadosBinding

    private val estados = listOf(
        Estado(1, "Alisson Polo", "Estoy aprendiendo Kotlin", System.currentTimeMillis()),
        Estado(2, "Juan", "En vacaciones", System.currentTimeMillis())
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEstadosBinding.inflate(layoutInflater)
        setContentView(binding.root)

        title = "Estados"

        binding.rvEstados.layoutManager = LinearLayoutManager(this)
        binding.rvEstados.adapter = EstadosAdapter(estados)
    }
}
