package com.example.backpack20

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.backpack20.Vistas.AvanzadoFragment
import com.example.backpack20.Vistas.PerfilFragment
import com.example.backpack20.Vistas.SalirFragment

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

        binding.btnDispositivosGuardados.setOnClickListener {
            //variable para llamar otra pantalla
            val intent = Intent(this, MainActivity2::class.java)
            startActivity(intent)
        }

        // Configuración de navegación con el BottomNavigationView
        binding.bootomNavigation.setOnItemSelectedListener {
            when (it.itemId){
                R.id.item_1 -> {
                    supportFragmentManager.beginTransaction().replace(R.id.fragment_container, PerfilFragment()).commit()
                    true
                }
                R.id.item_2 -> {
                    supportFragmentManager.beginTransaction().replace(R.id.fragment_container, SalirFragment()).commit()
                    true
                }
                R.id.item_3 -> {
                    supportFragmentManager.beginTransaction().replace(R.id.fragment_container, AvanzadoFragment()).commit()
                    true
                }
                else -> false
            }
        }

        // Evitar recargar los fragmentos cuando se re-selecciona un ítem
        binding.bootomNavigation.setOnItemReselectedListener {
            when (it.itemId){
                R.id.item_1 -> {
                    true
                }
                R.id.item_2 -> {
                    true
                }
                R.id.item_3 -> {
                    true
                }
                else -> false
            }
        }
    }




    private fun signOut() {
        Firebase.auth.signOut()
        finish()
    }
}
