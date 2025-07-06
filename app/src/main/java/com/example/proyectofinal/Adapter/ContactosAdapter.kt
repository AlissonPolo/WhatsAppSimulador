package com.example.proyectofinal.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.proyectofinal.R
import com.example.proyectofinal.model.Contacto

class ContactosAdapter(
    private val contactos: List<Contacto>,
    private val onClick: (Contacto) -> Unit
) : RecyclerView.Adapter<ContactosAdapter.ContactoViewHolder>() {

    inner class ContactoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val ivPerfil: ImageView = itemView.findViewById(R.id.ivPerfil)
        val tvNombre: TextView = itemView.findViewById(R.id.tvNombre)
        val tvUltimoMensaje: TextView = itemView.findViewById(R.id.tvUltimoMensaje)
        val tvHora: TextView = itemView.findViewById(R.id.tvHora)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactoViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_contacto, parent, false)
        return ContactoViewHolder(view)
    }

    // En ContactosAdapter.kt
    override fun onBindViewHolder(holder: ContactoViewHolder, position: Int) {
        val contacto = contactos[position]
        holder.tvNombre.text = contacto.nombre
        holder.tvUltimoMensaje.text = contacto.ultimoMensaje
        holder.tvHora.text = contacto.horaUltimoMensaje
        holder.ivPerfil.setImageResource(contacto.fotoPerfilResId)
        Log.d("ContactosAdapter", "onBind: ${contacto.nombre} - ${contacto.ultimoMensaje} @ ${contacto.horaUltimoMensaje}")

        holder.itemView.setOnClickListener {
            onClick(contacto)
        }
    }


    override fun getItemCount() = contactos.size
}
