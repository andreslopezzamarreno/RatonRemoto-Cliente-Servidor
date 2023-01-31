package com.example.entregafinalpsp.dialogs

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import com.example.entregafinalpsp.R
import com.example.entregafinalpsp.Usuario

class DialogoSesion : DialogFragment() {
    //Funcionalida del dialogo de inicio de sesion
    private lateinit var vista: View
    private lateinit var usuario: EditText
    private lateinit var contrasenia: EditText
    private lateinit var botonInicio: Button
    private lateinit var interfaz: OnDialogoInterfaz

    override fun onAttach(context: Context) {
        super.onAttach(context)
        interfaz = context as OnDialogoInterfaz
    }

    //Creacion del dialogo
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(requireContext())
        vista = LayoutInflater.from(requireContext()).inflate(R.layout.dialogo_inicio_sesion, null)
        builder.setView(vista)
        return builder.create()
    }

    override fun onStart() {
        super.onStart()
        instancias()
    }

    private fun instancias() {
        usuario = vista.findViewById(R.id.edit_user)
        contrasenia = vista.findViewById(R.id.edit_password)
        usuario = vista.findViewById(R.id.edit_user)
        botonInicio = vista.findViewById(R.id.boton_inicio)
    }

    override fun onResume() {
        super.onResume()
        //si no esta relleno los datos --> Toast
        //si relleno --> paso Usuario a main
        botonInicio.setOnClickListener {
            if (usuario.text.toString().isEmpty() || contrasenia.text.toString().isEmpty()) {
                Toast.makeText(requireContext(), "Algun dato vacio", Toast.LENGTH_SHORT).show()
            } else {
                interfaz.pasarUsuario(Usuario(usuario.text.toString(), contrasenia.text.toString()))
                dismiss()
            }
        }
    }

    //interzar para pasar datos dialogo-ActivityMain
    interface OnDialogoInterfaz {
        fun pasarUsuario(usuario: Usuario)
    }
}