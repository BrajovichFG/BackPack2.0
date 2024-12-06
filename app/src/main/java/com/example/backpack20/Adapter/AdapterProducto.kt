package com.example.backpack20.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.view.menu.MenuView.ItemView
import androidx.recyclerview.widget.RecyclerView
import com.example.backpack20.Models.Producto
import com.example.backpack20.R

class AdapterProducto(private var productos: ArrayList<Producto>):


    RecyclerView.Adapter<AdapterProducto.ViewHolder>(){

    class ViewHolder (itemView: View): RecyclerView.ViewHolder(itemView){
        val nombre: TextView = itemView.findViewById(R.id.tvNombre)
        val descripcion: TextView = itemView.findViewById(R.id.tvDescripcion)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdapterProducto.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_productos,parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val producto = productos[position]

        holder.nombre.text = producto.nombre
        holder.descripcion.text = producto.descripcion
    }

    override fun getItemCount(): Int {
        return productos.size
    }
}