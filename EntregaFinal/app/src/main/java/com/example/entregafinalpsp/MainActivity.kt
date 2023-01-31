package com.example.entregafinalpsp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.entregafinalpsp.databinding.ActivityMainBinding
import com.example.entregafinalpsp.dialogs.DialogoSesion
import java.io.BufferedReader
import java.io.InputStreamReader
import javax.crypto.Cipher
import javax.crypto.spec.SecretKeySpec

class MainActivity : AppCompatActivity(), DialogoSesion.OnDialogoInterfaz {

    private lateinit var binding: ActivityMainBinding
    private lateinit var usuarioPasado: Usuario

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //controlo pulsacion del boton para abrir dialogo de login (DialogoSesion)
        binding.inicioSesion.setOnClickListener {
            DialogoSesion().show(supportFragmentManager, "")
        }
    }

    override fun pasarUsuario(usuario: Usuario) {
        //llega usuario del dialogo DialogoSesion y cifro contraseña
        usuarioPasado = usuario
        cifrar(usuarioPasado.pass)

        //si hace login accede al raton
        if (login(usuarioPasado)) {
            val intent = Intent(applicationContext, MainActivity2::class.java)
            startActivity(intent)
        } else {
            Toast.makeText(this, "Usuario o contraseña Incorrecta", Toast.LENGTH_SHORT).show()
        }
    }

    private fun cifrar(contrasenia: String) {
        //cifrar
        val byteSecretKey: ByteArray = "aG9sYXF1ZXRsYQ==".toByteArray()
        val secretKeySpec = SecretKeySpec(byteSecretKey, "AES")
        val cipher = Cipher.getInstance("AES")
        cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec)
        val mensajeEncriptado: ByteArray = cipher.doFinal(contrasenia.toByteArray())
        val passEncriptada = String(mensajeEncriptado)
        //asigno contraseña cifrada a usuario pasado
        usuarioPasado.pass = passEncriptada

        /*
        //descifrado
        cipher.init(Cipher.DECRYPT_MODE,secretKeySpec)
        val decipherBytes: ByteArray = cipher.doFinal(mensajeEncriptado)
        val texto2 = String(decipherBytes)
        println(texto2)
         */
    }

    private fun login(usuarioLogin: Usuario): Boolean {
        //leo archivo usuarioregistro en la carpera res/raw
        //si algun usuario coincide con el usuario que esta intentando accedes hace login
        val lectura: String?
        val br: BufferedReader
        val usuarioLeido = usuarioLogin.usuario
        val passLeido = usuarioLogin.pass
        val filename = "usuarioregistro"
        val inputStream = resources.openRawResource(
            resources.getIdentifier(
                filename,
                "raw", packageName
            )
        )
        br = BufferedReader(InputStreamReader(inputStream))
        do {
            lectura = br.readLine()
            println(lectura.split(" ")[0])
            return usuarioLeido == lectura.split(" ")[0] && passLeido == lectura.split(" ")[1]
        } while (lectura != null)
    }

}
