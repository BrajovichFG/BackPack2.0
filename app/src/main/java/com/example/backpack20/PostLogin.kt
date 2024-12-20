package com.example.backpack20

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import com.example.backpack20.Vistas.DispositivosFragment
import com.example.backpack20.Vistas.InicioFragment
import com.example.backpack20.Vistas.MapaFragment
import com.example.backpack20.Vistas.PerfilFragment
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

        // Inicializar Firebase
        auth = Firebase.auth

        // Mostrar el correo del usuario
        val user = auth.currentUser
        user?.let {
            binding.tvUserEmail.text = "Ingresaste como ${it.email}"
        }
        binding.btnSalir.setOnClickListener {
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

        // Configurar el menú de navegación inferior
        binding.bootomNavigation.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.item_1 -> {
                    loadFragment(PerfilFragment())
                    true
                }
                R.id.item_2 -> {
                    loadFragment(InicioFragment())
                    true
                }
                R.id.item_3 -> {
                    loadFragment(MapaFragment())
                    true
                }
                R.id.item_4 -> {
                    loadFragment(DispositivosFragment())
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

    // Función para cargar fragmentos
    private fun loadFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .commit()
    }
}
