package com.example.proyectofinal.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.proyectofinal.R
import com.example.proyectofinal.model.Grupo

class GruposAdapter(
    private val grupos: List<Grupo>,
    private val onGrupoClick: (Grupo) -> Unit
) : RecyclerView.Adapter<GruposAdapter.GrupoViewHolder>() {

    inner class GrupoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val txtNombreGrupo: TextView = itemView.findViewById(R.id.txtNombreGrupo)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GrupoViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_grupo, parent, false)
        return GrupoViewHolder(view)
    }

    override fun onBindViewHolder(holder: GrupoViewHolder, position: Int) {
        val grupo = grupos[position]
        holder.txtNombreGrupo.text = grupo.nombre

        holder.itemView.setOnClickListener {
            onGrupoClick(grupo)
        }
    }

    override fun getItemCount() = grupos.size
}
