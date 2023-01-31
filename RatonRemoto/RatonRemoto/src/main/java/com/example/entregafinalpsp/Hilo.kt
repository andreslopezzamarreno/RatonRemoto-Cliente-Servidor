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
        //hago conexion con servido y envio las cordenadas o la pulsacion del boton
        client = Socket(ip, puerto)
        out = PrintWriter(client.getOutputStream(), true)
        out.println(cord)
        out.close()
        client.close()
    }
}