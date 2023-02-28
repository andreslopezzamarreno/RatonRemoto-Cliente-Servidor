package com.example.ratonremoto

import android.content.Context
import kotlinx.coroutines.Dispatchers.Main
import java.io.*
import java.net.Socket
import java.security.cert.CertificateFactory
import javax.net.ssl.SSLSocket
import javax.net.ssl.SSLSocketFactory



class Hilo(var context: Context,private var ip: String,private var puerto: Int, private var cord: String) : Thread() {

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


    //Intento de hacer conexion con Socket seguro
    //No funciona leer el certificado
    /*lateinit var sfact: SSLSocketFactory
    private lateinit var cliente: SSLSocket
    private lateinit var flujoSalida: DataOutputStream
    private lateinit var flujoEntrada: DataInputStream

    override fun run() {
        super.run()
        val filename = CertificateFactory.getInstance("X.509").generateCertificate(FileInputStream("com/example/ratonremoto/AlmacenSrv"))
        println(filename)
        //System.setProperty("javax.net.ssl.trustStore", )
        System.setProperty("javax.net.ssl.trustStorePassword", "1234567")

        sfact = SSLSocketFactory.getDefault() as SSLSocketFactory
        cliente = sfact.createSocket(ip,puerto) as SSLSocket
        flujoSalida = DataOutputStream(cliente.getOutputStream())

        flujoSalida.writeUTF("klk")

        /*flujoEntrada = DataInputStream(cliente.inputStream)
        println(flujoEntrada.readUTF())
        flujoEntrada.close()*/

        cliente.close()
        flujoSalida.close()
    }*/
}