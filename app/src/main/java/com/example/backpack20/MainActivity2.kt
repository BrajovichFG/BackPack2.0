package com.example.backpack20

import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.backpack20.Models.Producto
import com.example.backpack20.databinding.ActivityMain2Binding
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class MainActivity2 : AppCompatActivity() {

    //Activacion de viewBinding
    private lateinit var binding: ActivityMain2Binding
    //firebase RealTime
    private lateinit var database: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        //inicializamos binding
        binding = ActivityMain2Binding.inflate(layoutInflater)
        //para obtener todas las id de manera automatica
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main2)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        //Inicializacion de base de datos
        database = FirebaseDatabase.getInstance().getReference("Productos")
        binding.btnGuardar.setOnClickListener{
            // Código para guardar el producto
            val nombre = binding.etNombreProducto.text.toString()
            val descripcion = binding.etDescripcionProducto.text.toString()

            //generar el id random
            val id = database.child("Productos").push().key

            if (nombre.isEmpty()){
                binding.etNombreProducto.error = "Porfavor ingresar nombre"
                return@setOnClickListener
            }
            if (descripcion.isEmpty()){
                binding.etDescripcionProducto.error = "Porfavor ingrese algo"
            }

            val producto = Producto(id,nombre,descripcion)

            database.child(id!!).setValue(producto)
                .addOnCompleteListener{
                    binding.etNombreProducto.setText("")
                    binding.etDescripcionProducto.setText("")
                    Snackbar.make(binding.root,"Producto Agregado",Snackbar.LENGTH_SHORT).show()
                }
        }
    }
}
