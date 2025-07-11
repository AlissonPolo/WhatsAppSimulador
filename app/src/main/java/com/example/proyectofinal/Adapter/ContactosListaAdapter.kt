package com.example.proyectofinal.adapter

import Contacto
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.proyectofinal.R

class ContactosListaAdapter(
    private val contactos: List<Contacto>,
    private val onClick: (Contacto) -> Unit,
    private val onFotoClick: (Contacto) -> Unit
) : RecyclerView.Adapter<ContactosListaAdapter.ContactoViewHolder>() {

    inner class ContactoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val ivPerfil: ImageView = itemView.findViewById(R.id.ivPerfil)
        val tvNombre: TextView = itemView.findViewById(R.id.tvNombre)
        val tvEstado: TextView = itemView.findViewById(R.id.tvEstado)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactoViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_contacto_lista, parent, false)
        return ContactoViewHolder(view)
    }

    override fun onBindViewHolder(holder: ContactoViewHolder, position: Int) {
        val contacto = contactos[position]

        holder.ivPerfil.setImageResource(contacto.fotoPerfilResId)
        holder.tvNombre.text = contacto.nombre
        holder.tvEstado.text = contacto.mensajes.firstOrNull()?.texto ?: "Sin estado"


        holder.tvEstado.text = " No estoy disponible!!"

        holder.ivPerfil.setOnClickListener {
            onFotoClick(contacto)
        }

        holder.itemView.setOnClickListener {
            onClick(contacto)
        }
    }

    override fun getItemCount() = contactos.size
}
