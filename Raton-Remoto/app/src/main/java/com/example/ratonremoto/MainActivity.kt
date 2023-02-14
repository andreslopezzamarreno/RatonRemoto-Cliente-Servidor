package com.example.ratonremoto

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.ratonremoto.databinding.ActivityMainBinding
import com.example.ratonremoto.dialogs.DialogoSesion
import java.io.BufferedReader
import java.io.InputStreamReader
import javax.crypto.Cipher
import javax.crypto.spec.SecretKeySpec

class MainActivity : AppCompatActivity(), DialogoSesion.OnDialogoInterfaz {

    //En esta clase esta el dialogo,cifrado y login
    //Parte grafia de esta clase --> activity_main.xml
    private lateinit var binding: ActivityMainBinding
    private lateinit var usuarioPasado: Usuario

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        //Controlo pulsacion del boton para abrir dialogo de login (DialogoSesion.kt)
        //Devuelve un Usuario en metodo PasarUsuario (Justo abajo) por interfaz implementada en DialogoSesion.kt
        binding.inicioSesion.setOnClickListener {
            //Parte grafica dialogo --> dialogo_inicio_sesion.xml
            DialogoSesion().show(supportFragmentManager, "")
        }
    }

    override fun pasarUsuario(usuario: Usuario) {
        //Llega usuario del dialogo DialogoSesion.kt y cifro su contraseña
        cifrar(usuario)
        if (login()) {
            //Si hace login --> accede al raton (MainActivity2.kt)
            val intent = Intent(applicationContext, MainActivity2::class.java)
            startActivity(intent)
        } else {
            //Si no hace login --> Toast indicando el fallo
            Toast.makeText(this, "Usuario o contraseña Incorrecta", Toast.LENGTH_SHORT).show()
        }
    }

    private fun cifrar(usuario: Usuario) {
        //Cifrar
        //Creo clave de cifrado eh sistema AES desde base64
        val byteSecretKey: ByteArray = "aG9sYXF1ZXRsYQ==".toByteArray()
        val secretKeySpec = SecretKeySpec(byteSecretKey, "AES")
        val cipher = Cipher.getInstance("AES")
        //Inico cifrado con clave secreta
        cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec)
        //Cifro la contraseña del usuario pasado y lo convierto a String
        val mensajeEncriptado: ByteArray = cipher.doFinal(usuario.pass.toByteArray())
        val passEncriptada = String(mensajeEncriptado)
        //Asigno contraseña cifrada a usuario
        usuario.pass = passEncriptada
        //Asigno usuario con contraseña cifrada a usuarioPasado
        usuarioPasado = usuario

    /*
        //Descifrado
        cipher.init(Cipher.DECRYPT_MODE,secretKeySpec)
        val decipherBytes: ByteArray = cipher.doFinal(mensajeEncriptado)
        val texto2 = String(decipherBytes)
        println(texto2)
    */
    }

    private fun login(): Boolean {
        //Leo archivo usuarioregistro.txt en la carpera res/raw
        //Si algun usuario coincide con el usuario que esta intentando acceder --> hace login
        val lectura: String?
        val br: BufferedReader
        val filename = "usuarioregistro"
        //Abro inputStream para poder leer el documento usuarioregistro.txt
        val inputStream = resources.openRawResource(
            resources.getIdentifier(
                filename,
                "raw", packageName
            )
        )
        //Leo archivo y compruebo linea por linea
        br = BufferedReader(InputStreamReader(inputStream))
        do {
            lectura = br.readLine()
            println(lectura.split(" ")[0])
            return usuarioPasado.usuario == lectura.split(" ")[0] && usuarioPasado.pass == lectura.split(" ")[1]
        } while (lectura != null)
    }

}
