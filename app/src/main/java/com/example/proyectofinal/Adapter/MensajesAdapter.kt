package com.example.proyectofinal.adapter

import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
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
        holder.txtMensaje.text = mensaje.texto

        // Fondo diferente para emisor/receptor
        val background = if (mensaje.esEmisorUsuario) {
            R.drawable.message_background_user
        } else {
            R.drawable.message_background
        }
        holder.txtMensaje.setBackgroundResource(background)

        // Alineación usando layout_gravity de FrameLayout.LayoutParams
        val layoutParams = holder.txtMensaje.layoutParams as FrameLayout.LayoutParams
        layoutParams.gravity = if (mensaje.esEmisorUsuario) Gravity.END else Gravity.START
        holder.txtMensaje.layoutParams = layoutParams
    }

    override fun getItemCount(): Int = mensajes.size

    class MensajeViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val txtMensaje: TextView = view.findViewById(R.id.txtMensaje)
    }
}
