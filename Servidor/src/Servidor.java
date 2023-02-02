import java.awt.*;
import java.awt.event.InputEvent;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class Servidor {
    public static void main(String[] args) {
        int socket = 9999;
        ServerSocket servidor;
        BufferedReader br;
        String cordenadas;
        int cordX;
        int cordY;
        Robot control;

        try {
            control = new Robot();
        } catch (AWTException e) {
            throw new RuntimeException(e);
        }
        try {
            //Muestro por consola la ip del dispositivo y el socket para hacer la conexion
            System.out.println(InetAddress.getLocalHost());
            System.out.println("Puerto: " + socket);

            //creo ServerSocket y lo mantengo abierto
            servidor = new ServerSocket(socket);
            while (true) {
                Socket cliente = servidor.accept();
                br = new BufferedReader(new InputStreamReader(cliente.getInputStream()));
                cordenadas = br.readLine();

                //comparo si se ha hecho pulsacion en boton o en el SurfaceView
                if (cordenadas.equals("der")) {
                    control.mouseRelease(InputEvent.getMaskForButton(3));
                } else if (cordenadas.equals("izq")) {
                    control.mousePress(InputEvent.getMaskForButton(1));
                    control.mouseRelease(InputEvent.getMaskForButton(1));
                } else {
                    //si es pulsacion de SuerfaceView
                    //cojo coordenadas pasadas por cliente (app android)
                    int movimientoX = (Integer.parseInt(cordenadas.split(" ")[0])) / 100;
                    int movimientoY = (Integer.parseInt(cordenadas.split(" ")[1])) / 100;

                    //cojo la posicion actual del raton
                    cordX = MouseInfo.getPointerInfo().getLocation().x;
                    cordY = MouseInfo.getPointerInfo().getLocation().y;

                    //muevo raton sumando las cordenadas pasadas mas la posicion actual
                    control.mouseMove(movimientoX + cordX, movimientoY + cordY);

                    System.out.println(cordenadas);
                    cliente.close();
                    br.close();
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
