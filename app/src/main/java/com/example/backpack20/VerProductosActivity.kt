package com.example.backpack20

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.backpack20.Adapter.AdapterProducto
import com.example.backpack20.Models.Producto
import com.example.backpack20.databinding.ActivityVerProductosBinding
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class VerProductosActivity : AppCompatActivity() {
    //binding
    private lateinit var binding: ActivityVerProductosBinding;
    //database
    private lateinit var database: DatabaseReference;
    //lista
    private lateinit var productosList: ArrayList<Producto>
    //adapter
    private lateinit var adapterProducto: AdapterProducto;
    //recycler
    private lateinit var productoRecyclerView: RecyclerView;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        //iniciando binding
        binding = ActivityVerProductosBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        //mostrar datos en pantalla
        productoRecyclerView = binding.rvProductos
        productoRecyclerView.layoutManager = LinearLayoutManager(this)
        productoRecyclerView.hasFixedSize()

        productosList = ArrayList<Producto> ()

        getProductos()


    }

    //funcion para obtener productos en una lista
    private fun getProductos() {
        database = FirebaseDatabase.getInstance().getReference("Productos")
        database.addValueEventListener(object: ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()){
                    for (productosSnapshot in snapshot.children){
                        val producto = productosSnapshot.getValue(Producto::class.java)
                        productosList.add(producto!!)
                    }
                    adapterProducto = AdapterProducto(productosList)
                    productoRecyclerView.adapter = adapterProducto
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
    }
}