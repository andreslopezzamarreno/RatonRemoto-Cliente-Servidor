package com.example.ratonremoto.dialogs

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
import com.example.ratonremoto.R
import com.example.ratonremoto.Usuario

class DialogoSesion : DialogFragment() {
    //Funcionalida del dialogo de inicio de sesion
    //Parte grafica --> dialogo_inicio_sesion.xml
    private lateinit var vista: View
    private lateinit var usuario: EditText
    private lateinit var contrasenia: EditText
    private lateinit var botonInicio: Button
    private lateinit var interfaz: OnDialogoInterfaz

    override fun onAttach(context: Context) {
        super.onAttach(context)
        //Inicializo interfaz para que le pueda llegar al Main el Usuario de registro
        interfaz = context as OnDialogoInterfaz
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        //Creo dialogo y lo retorno
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
        //Inicializo elemento graficos del dialogo_inicio_sesion.xml
        usuario = vista.findViewById(R.id.edit_user)
        contrasenia = vista.findViewById(R.id.edit_password)
        usuario = vista.findViewById(R.id.edit_user)
        botonInicio = vista.findViewById(R.id.boton_inicio)
    }

    override fun onResume() {
        super.onResume()
        botonInicio.setOnClickListener {
            if (usuario.text.toString().isEmpty() || contrasenia.text.toString().isEmpty()) {
                //Si no esta relleno los datos --> Toast
                Toast.makeText(requireContext(), "Algun dato vacio", Toast.LENGTH_SHORT).show()
            } else {
                //Si relleno --> paso Usuario a Main
                interfaz.pasarUsuario(Usuario(usuario.text.toString(), contrasenia.text.toString()))
                dismiss()
            }
        }
    }

    //Interfaz para pasar datos dialogo-ActivityMain.kt
    interface OnDialogoInterfaz {
        fun pasarUsuario(usuario: Usuario)
    }
}