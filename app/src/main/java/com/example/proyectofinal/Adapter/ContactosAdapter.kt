package com.example.proyectofinal.adapter

import Contacto
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.proyectofinal.R

class ContactosAdapter(
    private val contactos: List<Contacto>,
    private val onClick: (Contacto) -> Unit,
    private val onFotoClick: (Contacto) -> Unit
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

    override fun onBindViewHolder(holder: ContactoViewHolder, position: Int) {
        val contacto = contactos[position]

        // Carga la foto
        holder.ivPerfil.setImageResource(contacto.fotoPerfilResId)

        // MUY IMPORTANTE: Mostrar nombre y último mensaje
        holder.tvNombre.text = contacto.nombre
        holder.tvUltimoMensaje.text = contacto.ultimoMensaje
        holder.tvHora.text = contacto.horaUltimoMensaje

        holder.ivPerfil.setOnClickListener {
            onFotoClick(contacto)
        }

        holder.itemView.setOnClickListener {
            onClick(contacto)
        }
    }



    override fun getItemCount() = contactos.size
}
