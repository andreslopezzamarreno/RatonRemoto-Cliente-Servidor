package com.example.ratonremoto

import java.io.PrintWriter
import java.net.Socket


class Hilo(private var ip: String,private var puerto: Int, private var cord: String) : Thread() {

    //PARA QUE FUNCIONE Y NO SALTE -> socket failed: eperm (operation not permitted)
    // Añadir al AndroidManifest.xml la siguiente linea:
    //<uses-permission android:name="android.permission.INTERNET"/>

    private lateinit var client: Socket
    private lateinit var out: PrintWriter

    override fun run() {
        super.run()
        //Hago conexion con servidor y envio las cordenadas/pulsacion del boton
        //Servidor y puerto los cojo de los Textview del activity_main2.xml
        client = Socket(ip, puerto)
        out = PrintWriter(client.getOutputStream(), true)
        //Envio coordenadas/pulsacion boton derecho o izquierdo --> Depende de que se pulse
        out.println(cord)
        out.close()
        client.close()
    }
}