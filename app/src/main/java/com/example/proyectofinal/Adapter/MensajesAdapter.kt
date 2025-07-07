package com.example.proyectofinal.adapter

import android.content.Context
import android.content.Intent
import android.database.Cursor
import android.net.Uri
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.MediaController
import android.widget.RelativeLayout
import android.widget.TextView
import android.widget.Toast
import android.widget.VideoView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.proyectofinal.R
import com.example.proyectofinal.model.Mensaje

class MensajesAdapter(private val mensajes: List<Mensaje>) :
    RecyclerView.Adapter<MensajesAdapter.MensajeViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MensajeViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_message, parent, false)
        return MensajeViewHolder(view)
    }

    override fun onBindViewHolder(holder: MensajeViewHolder, position: Int) {
        val mensaje = mensajes[position]
        val context = holder.itemView.context

        // Ocultar todos los componentes por defecto
        holder.txtMensaje.visibility = View.GONE
        holder.imageContainer.visibility = View.GONE
        holder.videoContainer.visibility = View.GONE
        holder.videoMensaje.pause()

        when {
            // Mostrar imagen en memoria (Bitmap capturado con cámara)
            mensaje.imagen != null -> {
                holder.imageContainer.visibility = View.VISIBLE
                holder.imgMensaje.setImageBitmap(mensaje.imagen)
                holder.imageContainer.setBackgroundColor(android.graphics.Color.TRANSPARENT)
                setGravity(holder.imageContainer, if (mensaje.esEmisorUsuario) Gravity.END else Gravity.START)
            }

            // Mostrar archivo desde URI
            mensaje.archivoUriString != null -> {
                val archivoUri = Uri.parse(mensaje.archivoUriString)
                val mimeType = context.contentResolver.getType(archivoUri)
                val uriStr = archivoUri.toString().lowercase()

                when {
                    // Imágenes (mime o extensión)
                    mimeType?.startsWith("image/") == true ||
                            uriStr.endsWith(".jpg") || uriStr.endsWith(".jpeg") || uriStr.endsWith(".png") -> {
                        holder.imageContainer.visibility = View.VISIBLE
                        Glide.with(context)
                            .load(archivoUri)
                            .into(holder.imgMensaje)
                        holder.imageContainer.setBackgroundColor(android.graphics.Color.TRANSPARENT)
                        setGravity(holder.imageContainer, if (mensaje.esEmisorUsuario) Gravity.END else Gravity.START)
                    }

                    // Videos (mime o extensión)
                    mimeType?.startsWith("video/") == true ||
                            uriStr.endsWith(".mp4") || uriStr.endsWith(".3gp") || uriStr.endsWith(".mkv") -> {
                        holder.videoContainer.visibility = View.VISIBLE
                        holder.videoMensaje.setVideoURI(archivoUri)
                        holder.videoMensaje.seekTo(1)
                        holder.videoContainer.setBackgroundColor(android.graphics.Color.TRANSPARENT)
                        setGravity(holder.videoContainer, if (mensaje.esEmisorUsuario) Gravity.END else Gravity.START)

                        val mediaController = MediaController(context)
                        mediaController.setAnchorView(holder.videoMensaje)
                        holder.videoMensaje.setMediaController(mediaController)

                        holder.videoMensaje.setOnClickListener {
                            if (holder.videoMensaje.isPlaying) holder.videoMensaje.pause()
                            else holder.videoMensaje.start()
                        }
                    }

                    // Otros archivos
                    else -> {
                        mostrarTextoAdjunto(holder, mensaje)
                    }
                }
            }

            // Texto normal
            !mensaje.texto.isNullOrEmpty() -> {
                holder.txtMensaje.visibility = View.VISIBLE
                holder.txtMensaje.text = mensaje.texto

                val bg = if (mensaje.esEmisorUsuario)
                    R.drawable.message_background_user else R.drawable.message_background
                holder.txtMensaje.setBackgroundResource(bg)

                setGravity(holder.txtMensaje, if (mensaje.esEmisorUsuario) Gravity.END else Gravity.START)
            }

            // Caso de mensaje vacío o no reconocido
            else -> {
                holder.txtMensaje.visibility = View.VISIBLE
                holder.txtMensaje.text = "⚠️ Mensaje no soportado"
                setGravity(holder.txtMensaje, if (mensaje.esEmisorUsuario) Gravity.END else Gravity.START)
            }
        }
    }
    override fun getItemCount(): Int = mensajes.size

    private fun obtenerNombreArchivo(uri: Uri, context: Context): String {
        var nombreArchivo = "Archivo adjunto"
        val cursor = context.contentResolver.query(uri, null, null, null, null)
        cursor?.use {
            if (it.moveToFirst()) {
                val index = it.getColumnIndexOpenableColumnsDisplayName()
                if (index >= 0) {
                    nombreArchivo = it.getString(index)
                }
            }
        }
        return nombreArchivo
    }

    // Helper para evitar código repetido
    private fun Cursor.getColumnIndexOpenableColumnsDisplayName(): Int {
        return getColumnIndex("_display_name").takeIf { it != -1 } ?: getColumnIndex("name")
    }


    private fun mostrarTextoAdjunto(holder: MensajeViewHolder, mensaje: Mensaje) {
        val context = holder.itemView.context
        var nombreArchivo = "Archivo adjunto"
        val archivoUri = mensaje.archivoUriString?.let { Uri.parse(it) }

        archivoUri?.let { uri ->
            val cursor = context.contentResolver.query(uri, null, null, null, null)
            cursor?.use { c ->
                if (c.moveToFirst()) {
                    val nameIndex = c.getColumnIndex("_display_name")
                    if (nameIndex != -1) {
                        nombreArchivo = c.getString(nameIndex)
                    }
                }
            }
        }

        holder.txtMensaje.visibility = View.VISIBLE
        holder.txtMensaje.text = "📄 $nombreArchivo"
        holder.txtMensaje.isClickable = true
        holder.txtMensaje.isFocusable = true

        // Listener para abrir archivo
        holder.txtMensaje.setOnClickListener {
            if (archivoUri != null) {
                try {
                    val intent = Intent(Intent.ACTION_VIEW)
                    intent.setDataAndType(archivoUri, context.contentResolver.getType(archivoUri))
                    intent.flags = Intent.FLAG_GRANT_READ_URI_PERMISSION or Intent.FLAG_ACTIVITY_NEW_TASK
                    context.startActivity(intent)
                } catch (e: Exception) {
                    Toast.makeText(context, "No se puede abrir el archivo", Toast.LENGTH_SHORT).show()
                }
            }
        }

        val bg = if (mensaje.esEmisorUsuario)
            R.drawable.message_background_user else R.drawable.message_background
        holder.txtMensaje.setBackgroundResource(bg)

        setGravity(holder.txtMensaje, if (mensaje.esEmisorUsuario) Gravity.END else Gravity.START)
    }

    private fun setGravity(view: View, gravity: Int) {
        val lp = view.layoutParams
        when (lp) {
            is FrameLayout.LayoutParams -> {
                lp.gravity = gravity
                view.layoutParams = lp
            }
            is LinearLayout.LayoutParams -> {
                lp.gravity = gravity
                view.layoutParams = lp
            }
            is RelativeLayout.LayoutParams -> {
                // RelativeLayout no tiene gravity, para alinear usa reglas:
                // Puedes agregar reglas aquí si es necesario.
            }
            else -> {
                // Otro tipo de LayoutParams, opcional manejarlo
            }
        }
    }

    class MensajeViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val txtMensaje: TextView = view.findViewById(R.id.txtMensaje)
        val imageContainer: FrameLayout = view.findViewById(R.id.imageContainer)
        val imgMensaje: ImageView = view.findViewById(R.id.imgMensaje)
        val videoContainer: FrameLayout = view.findViewById(R.id.videoContainer)
        val videoMensaje: VideoView = view.findViewById(R.id.videoMensaje)
    }
}
