package com.example.proyectofinal.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.proyectofinal.R
import com.example.proyectofinal.model.Estado

class EstadosAdapter(private val estados: List<Estado>) :
    RecyclerView.Adapter<EstadosAdapter.EstadoViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EstadoViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_estado, parent, false)
        return EstadoViewHolder(view)
    }

    override fun onBindViewHolder(holder: EstadoViewHolder, position: Int) {
        val estado = estados[position]
        holder.txtContactoEstado.text = estado.contactoNombre
        holder.txtTextoEstado.text = estado.texto
    }

    override fun getItemCount(): Int = estados.size

    class EstadoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val txtContactoEstado: TextView = itemView.findViewById(R.id.txtContactoEstado)
        val txtTextoEstado: TextView = itemView.findViewById(R.id.txtTextoEstado)
    }
}
