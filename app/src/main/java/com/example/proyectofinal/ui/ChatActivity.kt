package com.example.proyectofinal.ui
import Contacto
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.widget.EditText
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.proyectofinal.R
import com.example.proyectofinal.adapter.MensajesAdapter
import com.example.proyectofinal.model.Mensaje
import com.example.proyectofinal.model.listaGlobalContactos
import java.text.SimpleDateFormat
import java.util.*
import android.Manifest
import android.os.Build
import android.widget.VideoView
import java.io.File
import java.io.FileOutputStream

class ChatActivity : AppCompatActivity() {

    private lateinit var adapter: MensajesAdapter
    private lateinit var contacto: Contacto
    private lateinit var mensajes: MutableList<Mensaje>
    private var siguienteIdMensaje = 1
    private val REQUEST_IMAGE_CAPTURE_OR_PICK = 1
    private val REQUEST_FILE_PICK = 2
    private val REQUEST_CODE_CAMERA = 100
    private val REQUEST_CODE_STORAGE = 101
    private val REQUEST_IMAGE_OR_VIDEO = 1
    private lateinit var db: AppDatabase
    private lateinit var mensajeDao: MensajeDao




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)

        val contactoId = intent.getIntExtra("contactoId", -1)
        contacto = listaGlobalContactos.first { it.id == contactoId }
        mensajes = contacto.mensajes
        title = contacto.nombre

        db = AppDatabase.obtenerInstancia(this)
        mensajeDao = db.mensajeDao()


        val rvMensajes: RecyclerView = findViewById(R.id.recyclerView)
        val etMensaje: EditText = findViewById(R.id.etMessage)
        val btnEnviar: ImageButton = findViewById(R.id.btnSend)
        val imgPerfil = findViewById<ImageView>(R.id.imgChatPerfil)
        val txtNombre = findViewById<TextView>(R.id.txtChatNombre)
        val btnCamara = findViewById<ImageButton>(R.id.btnCamara)
        val btnAdjuntar = findViewById<ImageButton>(R.id.btnAdjuntar)



        imgPerfil.setImageResource(contacto.fotoPerfilResId)
        txtNombre.text = contacto.nombre

        adapter = MensajesAdapter(mensajes)
        rvMensajes.layoutManager = LinearLayoutManager(this)
        rvMensajes.adapter = adapter

        Thread {
            val mensajesGuardados = mensajeDao.getMensajesPorContacto(contacto.id)
            runOnUiThread {
                if (mensajesGuardados.isNotEmpty()) {
                    mensajes.clear()
                    mensajes.addAll(mensajesGuardados)
                    adapter.notifyDataSetChanged()
                    siguienteIdMensaje = (mensajes.maxOfOrNull { it.id } ?: 0) + 1
                }
            }
        }.start()
        btnEnviar.setOnClickListener {
            val texto = etMensaje.text.toString().trim()
            if (texto.isNotEmpty()) {
                val mensaje = Mensaje(
                    contactoId = contacto.id,
                    texto = texto,
                    esEmisorUsuario = true,
                    timestamp = System.currentTimeMillis(),
                    archivoUriString = null
                )
                guardarMensaje(mensaje)
                etMensaje.text.clear()
                responderAutomaticamente()
            }
        }
        btnCamara.setOnClickListener {
            if (checkAndRequestCameraPermission()) abrirCamaraOGaleria()
        }
        btnAdjuntar.setOnClickListener {
            if (checkAndRequestStoragePermission()) abrirSelectorArchivos()
        }
    }
    private fun guardarMensaje(mensaje: Mensaje) {
        contacto.agregarMensaje(mensaje)
        adapter.notifyItemInserted(mensajes.size - 1)
        findViewById<RecyclerView>(R.id.recyclerView).scrollToPosition(mensajes.size - 1)

        Thread {
            mensajeDao.insertarMensaje(mensaje)
        }.start()
    }

    private fun responderAutomaticamente() {
        val respuesta = Mensaje(
            contactoId = contacto.id,
            texto = "Respuesta automática de ${contacto.nombre}",
            esEmisorUsuario = false,
            timestamp = System.currentTimeMillis(),
            archivoUriString = null
        )
        guardarMensaje(respuesta)
    }

    private fun formatearHora(timestamp: Long): String {
        val sdf = SimpleDateFormat("hh:mm a", Locale.getDefault())
        return sdf.format(Date(timestamp))
    }
    private fun abrirCamaraOGaleria() {
        val opciones = arrayOf("Tomar Foto", "Elegir Foto de la Galería", "Elegir Video de la Galería")

        val builder = android.app.AlertDialog.Builder(this)
        builder.setTitle("Selecciona una opción")
        builder.setItems(opciones) { _, which ->
            when (which) {
                0 -> {
                    // Cámara
                    val camaraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                    if (camaraIntent.resolveActivity(packageManager) != null) {
                        startActivityForResult(camaraIntent, REQUEST_IMAGE_OR_VIDEO)
                    }
                }
                1 -> {
                    // Galería de imágenes (uso de ACTION_OPEN_DOCUMENT para acceso persistente)
                    val intent = Intent(Intent.ACTION_OPEN_DOCUMENT).apply {
                        addCategory(Intent.CATEGORY_OPENABLE)
                        type = "image/*"
                    }
                    startActivityForResult(intent, REQUEST_IMAGE_OR_VIDEO)
                }
                2 -> {
                    // Galería de videos
                    val intent = Intent(Intent.ACTION_OPEN_DOCUMENT).apply {
                        addCategory(Intent.CATEGORY_OPENABLE)
                        type = "video/*"
                    }
                    startActivityForResult(intent, REQUEST_IMAGE_OR_VIDEO)
                }
            }
        }
        builder.show()
    }

    private fun abrirSelectorArchivos() {
        val intent = Intent(Intent.ACTION_OPEN_DOCUMENT) // Cambiar acción
        intent.addFlags(Intent.FLAG_GRANT_PERSISTABLE_URI_PERMISSION)  // Pedir permiso persistente
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)         // Permiso lectura
        intent.type = "*/*"
        startActivityForResult(Intent.createChooser(intent, "Selecciona archivo"), REQUEST_FILE_PICK)
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == Activity.RESULT_OK) {
            when (requestCode) {
                REQUEST_IMAGE_OR_VIDEO -> {
                    val bitmap = data?.extras?.get("data") as? Bitmap
                    if (bitmap != null) {
                        // Foto tomada con cámara
                        enviarMensajeConImagen(bitmap)
                    } else {
                        val uri = data?.data
                        if (uri != null) {
                            try {
                                contentResolver.takePersistableUriPermission(
                                    uri,
                                    Intent.FLAG_GRANT_READ_URI_PERMISSION
                                )
                            } catch (e: SecurityException) {
                                e.printStackTrace()
                            }

                            val mimeType = contentResolver.getType(uri)
                            if (mimeType != null && mimeType.startsWith("video")) {
                                enviarMensajeConVideoUri(uri)
                            } else {
                                enviarMensajeConArchivoUri(uri)
                            }
                        }
                    }
                }
                REQUEST_FILE_PICK -> {
                    val uri = data?.data
                    if (uri != null) {
                        try {
                            contentResolver.takePersistableUriPermission(
                                uri,
                                Intent.FLAG_GRANT_READ_URI_PERMISSION
                            )
                        } catch (e: SecurityException) {
                            e.printStackTrace()
                        }

                        val mimeType = contentResolver.getType(uri)
                        if (mimeType != null && mimeType.startsWith("video")) {
                            enviarMensajeConVideoUri(uri)
                        } else {
                            enviarMensajeConArchivoUri(uri)
                        }
                    }
                }
            }
        }
    }
    private fun guardarBitmapComoArchivo(bitmap: Bitmap): Uri? {
        return try {
            val nombreArchivo = "foto_${System.currentTimeMillis()}.jpg"
            val archivo = File(filesDir, nombreArchivo)
            val outputStream = FileOutputStream(archivo)
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream)
            outputStream.flush()
            outputStream.close()
            Uri.fromFile(archivo)
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }
    private fun enviarMensajeConImagen(bitmap: Bitmap) {
        val uri = guardarBitmapComoArchivo(bitmap)
        if (uri != null) {
            val mensaje = Mensaje(
                contactoId = contacto.id,
                texto = null,
                esEmisorUsuario = true,
                timestamp = System.currentTimeMillis(),
                archivoUriString = uri.toString()
            )
            guardarMensaje(mensaje)
            Toast.makeText(this, "Foto enviada", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this, "Error al guardar la imagen", Toast.LENGTH_SHORT).show()
        }
    }


    private fun enviarMensajeConArchivoUri(uri: Uri) {
        val mensaje = Mensaje(
            contactoId = contacto.id,
            texto = null,
            esEmisorUsuario = true,
            timestamp = System.currentTimeMillis(),
            archivoUriString = uri.toString()
        )
        guardarMensaje(mensaje)
        Toast.makeText(this, "Archivo enviado", Toast.LENGTH_SHORT).show()
    }
    private fun enviarMensajeConVideoUri(uri: Uri) {
        if (uri.scheme == "content") {
            try {
                contentResolver.takePersistableUriPermission(uri, Intent.FLAG_GRANT_READ_URI_PERMISSION)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
        val mensaje = Mensaje(
            contactoId = contacto.id,
            texto = null,
            esEmisorUsuario = true,
            timestamp = System.currentTimeMillis(),
            archivoUriString = uri.toString()
        )
        guardarMensaje(mensaje)
        Toast.makeText(this, "🎥 Video enviado", Toast.LENGTH_SHORT).show()
    }

    private fun copiarVideoAPrivado(uri: Uri): Uri? {
        return try {
            val inputStream = contentResolver.openInputStream(uri)
            val nombreArchivo = "video_${System.currentTimeMillis()}.mp4"
            val archivoDestino = File(filesDir, nombreArchivo)

            val outputStream = FileOutputStream(archivoDestino)
            inputStream?.copyTo(outputStream)

            inputStream?.close()
            outputStream.close()

            // Devuelve Uri del archivo copiado
            Uri.fromFile(archivoDestino)
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }



    private fun checkAndRequestCameraPermission(): Boolean {
        return if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.CAMERA), REQUEST_CODE_CAMERA)
            false
        } else {
            true
        }
    }

    private fun checkAndRequestStoragePermission(): Boolean {
        val permission = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            Manifest.permission.READ_MEDIA_IMAGES
        } else {
            Manifest.permission.READ_EXTERNAL_STORAGE
        }

        return if (ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(permission), REQUEST_CODE_STORAGE)
            false
        } else {
            true
        }
    }


    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_CODE_CAMERA && grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            abrirCamaraOGaleria()
        }
        if (requestCode == REQUEST_CODE_STORAGE && grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            abrirSelectorArchivos()
        }
    }
}


