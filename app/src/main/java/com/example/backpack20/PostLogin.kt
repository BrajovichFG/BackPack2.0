package com.example.backpack20

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

import com.example.backpack20.databinding.ActivityPostLoginBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class PostLogin : AppCompatActivity() {

    // Configuración de viewBinding
    private lateinit var binding: ActivityPostLoginBinding

    // Configuración de Firebase
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityPostLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Inicializar firebase
        auth = Firebase.auth

        // Mostrar el correo del usuario
        val user = auth.currentUser
        user?.let {
            binding.tvUserEmail.text = "Ingresaste como ${it.email}"
        }

        // Programar el botón de logout
        binding.btnLogout.setOnClickListener {
            MaterialAlertDialogBuilder(this)
                .setTitle("Cerrar sesión")
                .setMessage("¿Estás seguro de que deseas cerrar sesión?")
                .setNeutralButton("Cancelar") { dialog, which ->

                }
                .setPositiveButton("Aceptar") { dialog, which ->
                    signOut()
                }
                .show()

        }

    }

    private fun signOut() {
        Firebase.auth.signOut()
        finish()
    }
}
