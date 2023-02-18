import java.io.*;
import java.net.InetAddress;
import javax.net.ssl.*;

public class ServidorPrueba {
    public static void main(String[] arg) throws IOException {
        int puerto = 5556;
        SSLSocket clienteConectado;
        DataInputStream flujoEntrada ;

        System.setProperty("javax.net.ssl.keyStore", "src/AlmacenSrv");
        System.setProperty("javax.net.ssl.keyStorePassword", "1234567");

        System.out.println(InetAddress.getLocalHost());
        System.out.println("Puerto: " + puerto);
        SSLServerSocketFactory sfact = (SSLServerSocketFactory) SSLServerSocketFactory
                .getDefault();
        SSLServerSocket servidorSSL = (SSLServerSocket) sfact
                .createServerSocket(puerto);

        clienteConectado = (SSLSocket) servidorSSL.accept();
        flujoEntrada = new DataInputStream(clienteConectado.getInputStream());

        // EL CLIENTE ME ENVIA UN MENSAJE
        while (puerto<-1) {
            System.out.println(flujoEntrada.readUTF());
        }


        //flujoSalida = new DataOutputStream(clienteConectado.getOutputStream());

        // ENVIO UN SALUDO AL CLIENTE
        //flujoSalida.writeUTF("Saludos al cliente del servidor");

        // CERRAR STREAMS Y SOCKETS
        //flujoSalida.close();
        flujoEntrada.close();
        clienteConectado.close();
        servidorSSL.close();

    }// main
}