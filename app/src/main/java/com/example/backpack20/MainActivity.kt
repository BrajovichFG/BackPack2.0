package com.example.backpack20

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.backpack20.databinding.ActivityMainBinding
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth

class MainActivity : AppCompatActivity() {

    //Se configura ViewBinding
    private lateinit var binding: ActivityMainBinding;
    //Se configura FireBase
    private lateinit var auth: FirebaseAuth;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        //se inicializa Binding
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        //inicializacion de firebase
        auth = Firebase.auth

        //configuracion para boton login
        binding.btnLogin.setOnClickListener{
            val email = binding.etEmail.text.toString()
            val password = binding.etPassword.text.toString()

            if (email.isEmpty()) {
                binding.etEmail.error = "Email es requerido"
                return@setOnClickListener
            }
            if (password.isEmpty()){
                binding.etPassword.error = "Contraseña Requerida"
                return@setOnClickListener
            }
            //iniciar sesion
            singUp(email, password)
        }

        //enlace para registro
        binding.tvRegistrar.setOnClickListener {
            try {
                val intent = Intent(this,RegistrarActivity::class.java)
                startActivity(intent)
            }catch (e: Exception){
                Toast.makeText(this,e.toString(),Toast.LENGTH_SHORT).show()
            }
        }
    }

    //se crea funcion para registrar que toma como parametros correo y contraseña
    private fun singUp(email: String, password: String) {
        auth.signInWithEmailAndPassword(email,password)
            .addOnCompleteListener{
                if(it.isSuccessful){
                    Toast.makeText(this, "Inicio de sesión correcto", Toast.LENGTH_SHORT).show()
                    val intent = Intent(this,PostLogin::class.java)
                    startActivity(intent)
                }else{
                    Toast.makeText(this, "Error al iniciar sesión", Toast.LENGTH_SHORT).show()
                }
            }
    }



}