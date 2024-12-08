package com.example.backpack20.Vistas

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.backpack20.R
import com.example.backpack20.Models.Producto
import com.example.backpack20.VerProductosActivity
import com.example.backpack20.databinding.FragmentDispositivosBinding
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [DispositivosFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class DispositivosFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    // View binding
    private lateinit var binding: FragmentDispositivosBinding

    // Firebase RealTime Database
    private lateinit var database: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment using view binding
        binding = FragmentDispositivosBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Inicialización de base de datos
        database = FirebaseDatabase.getInstance().getReference("Dispositivos")

        binding.btnGuardar.setOnClickListener {
            val nombre = binding.etNombreProducto.text.toString()
            val descripcion = binding.etDescripcionProducto.text.toString()

            val id = database.child("Dispositivos").push().key

            if (nombre.isEmpty()) {
                binding.etNombreProducto.error = "Por favor ingrese nombre"
                return@setOnClickListener
            }
            if (descripcion.isEmpty()) {
                binding.etDescripcionProducto.error = "Por favor ingrese descripción"
                return@setOnClickListener
            }

            val dispositivo = Producto(id, nombre, descripcion)

            database.child(id!!).setValue(dispositivo)
                .addOnCompleteListener {
                    binding.etNombreProducto.setText("")
                    binding.etDescripcionProducto.setText("")
                    Snackbar.make(binding.root, "Dispositivo agregado", Snackbar.LENGTH_SHORT)
                        .show()
                }
        }

        binding.btnVer.setOnClickListener {
            val intent = Intent(requireContext(), VerProductosActivity::class.java)
            startActivity(intent)
        }
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment AvanzadoFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            AvanzadoFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}