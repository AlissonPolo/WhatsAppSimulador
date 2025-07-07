package com.example.proyectofinal.ui

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import androidx.appcompat.app.AppCompatActivity
import com.example.proyectofinal.R
import com.google.android.material.appbar.MaterialToolbar
import android.text.Editable
import android.text.TextWatcher
import android.widget.*
import java.io.File
import java.io.FileOutputStream

class PerfilActivity : AppCompatActivity() {

    private lateinit var ivFotoPerfil: ImageView
    private lateinit var btnCambiarFoto: Button
    private lateinit var etNombre: EditText
    private lateinit var etInfo: EditText
    private lateinit var tvTelefono: TextView
    private lateinit var toolbar: MaterialToolbar

    private val prefs by lazy { getSharedPreferences("perfil", Context.MODE_PRIVATE) }

    private val seleccionarImagenLauncher = registerForActivityResult(
        androidx.activity.result.contract.ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        uri?.let {
            val bitmap = getBitmapFromUri(it)
            bitmap?.let { bmp ->
                ivFotoPerfil.setImageBitmap(bmp)
                guardarImagenLocal(bmp)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_perfi)

        // Toolbar
        toolbar = findViewById(R.id.topAppBarPerfil)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowTitleEnabled(true)
        supportActionBar?.title = "Perfil"

        toolbar.setNavigationOnClickListener {
            finish() // cerrar actividad
        }

        // Views
        ivFotoPerfil = findViewById(R.id.ivFotoPerfil)
        btnCambiarFoto = findViewById(R.id.btnCambiarFoto)
        etNombre = findViewById(R.id.etNombre)
        etInfo = findViewById(R.id.etInfo)
        tvTelefono = findViewById(R.id.tvTelefono)

        ivFotoPerfil.setOnClickListener {
            val file = File(filesDir, "foto_perfil.png")
            if (file.exists()) {
                val intent = Intent(this, VerFotoActivity::class.java)
                intent.putExtra("fotoPath", file.absolutePath)
                startActivity(intent)
            }
        }



        // Cargar imagen guardada
        val file = File(filesDir, "foto_perfil.png")
        if (file.exists()) {
            val bitmap = MediaStore.Images.Media.getBitmap(contentResolver, Uri.fromFile(file))
            ivFotoPerfil.setImageBitmap(bitmap)
        }

        // Cargar texto guardado
        val nombre = prefs.getString("nombre", "")
        val info = prefs.getString("info", "No puedo hablar, solo WhatsApp")
        val telefono = prefs.getString("telefono", "+57 300 123 4567")

        etNombre.setText(nombre)
        etInfo.setText(info)
        tvTelefono.text = "Teléfono: $telefono"

        btnCambiarFoto.setOnClickListener {
            seleccionarImagenLauncher.launch("image/*")
        }

        // Guardar al escribir (mejor que onFocusChangeListener)
        etNombre.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                prefs.edit().putString("nombre", s.toString()).apply()
            }
            override fun afterTextChanged(s: Editable?) {}
        })

        etInfo.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                prefs.edit().putString("info", s.toString()).apply()
            }
            override fun afterTextChanged(s: Editable?) {}
        })
    }

    private fun getBitmapFromUri(uri: Uri): Bitmap? {
        return try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                val source = ImageDecoder.createSource(contentResolver, uri)
                ImageDecoder.decodeBitmap(source)
            } else {
                MediaStore.Images.Media.getBitmap(contentResolver, uri)
            }
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    private fun guardarImagenLocal(bitmap: Bitmap) {
        try {
            val file = File(filesDir, "foto_perfil.png")
            val fos = FileOutputStream(file)
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos)
            fos.flush()
            fos.close()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}
