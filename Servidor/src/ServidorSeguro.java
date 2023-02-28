import java.io.*;
import java.net.InetAddress;
import javax.net.ssl.*;

public class ServidorSeguro {
    public static void main(String[] arg) throws IOException {

        //intento de ServerSocket seguro, el cliente(android no lee el certificado)
        //utilizar Servidor.java
        int puerto = 9999;
        SSLSocket clienteConectado;
        DataInputStream flujoEntrada ;


        System.setProperty("javax.net.ssl.trustStore", "src/AlmacenSrv");
        System.setProperty("javax.net.ssl.trustStorePassword", "1234567");

        System.out.println(InetAddress.getLocalHost());
        System.out.println("Puerto: " + puerto);

        SSLServerSocketFactory sfact = (SSLServerSocketFactory) SSLServerSocketFactory
                .getDefault();
        SSLServerSocket servidorSSL = (SSLServerSocket) sfact
                .createServerSocket(puerto);

        clienteConectado = (SSLSocket) servidorSSL.accept();
        flujoEntrada = new DataInputStream(clienteConectado.getInputStream());
        System.out.println(flujoEntrada.readUTF());

        flujoEntrada.close();
        clienteConectado.close();
        servidorSSL.close();

    }// main
}