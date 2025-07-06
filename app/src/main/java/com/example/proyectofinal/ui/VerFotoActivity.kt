package com.example.proyectofinal.ui

import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.example.proyectofinal.R

class VerFotoActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ver_foto)

        val ivFotoAmpliada: ImageView = findViewById(R.id.imgFotoAmpliada)

        val fotoResId = intent.getIntExtra("fotoResId", R.drawable.ic_person)
        ivFotoAmpliada.setImageResource(fotoResId)

        // Cerrar al hacer clic
        ivFotoAmpliada.setOnClickListener {
            finish()
        }
    }
}
