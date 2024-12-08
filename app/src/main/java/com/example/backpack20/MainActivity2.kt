package com.example.backpack20

import android.content.Intent
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
    private lateinit var binding: ActivityMain2Binding;
    //firebase RealTime
    private lateinit var database: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        //inicializamos binding
        binding = ActivityMain2Binding.inflate(layoutInflater)
        //para obtener todas las id de manera automatica
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
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

            //condicionales para no tener campos vacios

            if (nombre.isEmpty()){
                binding.etNombreProducto.error = "Porfavor ingresar nombre"
                return@setOnClickListener
            }
            if (descripcion.isEmpty()){
                binding.etDescripcionProducto.error = "Porfavor ingrese algo"
            }

            //definimos constructor
            val producto = Producto(id,nombre,descripcion)

            //funcion para la insercion,
            database.child(id!!).setValue(producto)
                .addOnCompleteListener{
                    //reinicia el los campos para seguir ingresando
                    binding.etNombreProducto.setText("")
                    binding.etDescripcionProducto.setText("")
                    Snackbar.make(binding.root,"Producto Agregado",Snackbar.LENGTH_SHORT).show()
                }
        }
        binding.btnVer.setOnClickListener{
            val intent = Intent(this,VerProductosActivity::class.java)
            startActivity(intent)
        }
    }
}
