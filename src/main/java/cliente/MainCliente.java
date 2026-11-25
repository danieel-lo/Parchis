package cliente;

import cliente.aplicacion.AplicacionCliente;
import cliente.control.ControlUnirsePartida;
import cliente.modelo.ModeloCliente;
import cliente.red.SocketCliente;
import cliente.vista.PantallaUnirsePartida;
import java.io.IOException;

public class MainCliente {

    public static void main(String[] args) {
        try {
            // Conectar SocketCliente al servidor
            SocketCliente socketCliente = new SocketCliente("localhost", 5000);
            AplicacionCliente appCliente = new AplicacionCliente(socketCliente);
            socketCliente.setAplicacionCliente(appCliente);
            socketCliente.conectar();

            // Ensamblar MVC
            ModeloCliente modelo = new ModeloCliente();
            ControlUnirsePartida control = new ControlUnirsePartida(appCliente, modelo);
            appCliente.setControl(control);

            // Iniciar Vista en Consola
            PantallaUnirsePartida vista = new PantallaUnirsePartida(control);
            vista.iniciar();

        } catch (IOException e) {
            System.err.println("No se pudo conectar al servidor en localhost:5000.");
            System.err.println("Asegúrate de ejecutar primero MainServidor.");
        }
    }
}