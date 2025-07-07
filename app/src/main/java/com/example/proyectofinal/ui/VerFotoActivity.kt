package com.example.proyectofinal.ui

import android.graphics.BitmapFactory
import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.example.proyectofinal.R
import java.io.File

class VerFotoActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ver_foto)

        val ivFotoAmpliada: ImageView = findViewById(R.id.imgFotoAmpliada)

        val fotoPath = intent.getStringExtra("fotoPath")
        if (!fotoPath.isNullOrEmpty()) {
            val file = File(fotoPath)
            if (file.exists()) {
                val bitmap = BitmapFactory.decodeFile(file.absolutePath)
                ivFotoAmpliada.setImageBitmap(bitmap)
            } else {
                ivFotoAmpliada.setImageResource(R.drawable.ic_person)
            }
        } else {
            val fotoResId = intent.getIntExtra("fotoResId", R.drawable.ic_person)
            ivFotoAmpliada.setImageResource(fotoResId)
        }

        ivFotoAmpliada.setOnClickListener {
            finish()
        }
    }
}
