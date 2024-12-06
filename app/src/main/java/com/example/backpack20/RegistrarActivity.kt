package com.example.backpack20

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.backpack20.databinding.ActivityRegistrarBinding
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth

class RegistrarActivity : AppCompatActivity() {

    // binding
    private lateinit var binding: ActivityRegistrarBinding;
    //firebase
    private lateinit var auth: FirebaseAuth;


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityRegistrarBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        auth = Firebase.auth

        binding.btnRegistrar.setOnClickListener{
            //obtenemos correo
            val email = binding.etEmail.text.toString()
            val pass1 = binding.etPassword.text.toString()
            val pass2 = binding.etPassword2.text.toString()

            if (email.isEmpty()){
                binding.etEmail.error = "Porfavor ingrese un correo"
                return@setOnClickListener
            }
            if (pass1.isEmpty()){
                binding.etPassword.error = "Porfavor ingrese una contraseña"
                return@setOnClickListener
            }
            if (pass2.isEmpty()){
                binding.etPassword2.error = "Porfavor ingrese la contraseña nuevamente"
                return@setOnClickListener
            }
            if (pass1!=pass2){
                binding.etPassword2.error = "Las contraseñas no coinciden"
                return@setOnClickListener
            }else{
                singUp(email,pass1)
            }
        }
    }

    private fun singUp(email: String, pass1: String) {
        auth.createUserWithEmailAndPassword(email,pass1)
            .addOnCompleteListener{
                if (it.isSuccessful){
                    Toast.makeText(this,"Usuario Registrado",Toast.LENGTH_SHORT).show()
                    val intent = Intent(this,MainActivity::class.java)
                    startActivity(intent)
                }else{
                    Toast.makeText(this,"Error en el registro del usuario",Toast.LENGTH_SHORT).show()
                }
            }
    }
}