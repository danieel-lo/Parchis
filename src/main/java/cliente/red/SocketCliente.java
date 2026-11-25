package cliente.red;

import cliente.aplicacion.AplicacionCliente;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class SocketCliente {

    private final String host;
    private final int puerto;
    private Socket socket;
    private PrintWriter salida;
    private BufferedReader entrada;
    private AplicacionCliente appCliente;

    public SocketCliente(String host, int puerto) {
        this.host = host;
        this.puerto = puerto;
    }

    public void setAplicacionCliente(AplicacionCliente appCliente) {
        this.appCliente = appCliente;
    }

    public void conectar() throws IOException {
        this.socket = new Socket(host, puerto);
        this.salida = new PrintWriter(socket.getOutputStream(), true);
        this.entrada = new BufferedReader(new InputStreamReader(socket.getInputStream()));

        // Hilo dedicado a escuchar las notificaciones/broadcast del servidor
        new Thread(() -> {
            try {
                String mensajeServidor;
                while ((mensajeServidor = entrada.readLine()) != null) {
                    if (appCliente != null) {
                        appCliente.procesarNotificacion(mensajeServidor);
                    }
                }
            } catch (IOException e) {
                System.err.println("[ClienteParchis] Conexión cerrada con el servidor: " + e.getMessage());
            }
        }).start();
    }

    public void enviarMensaje(String mensajeSerializado) {
        if (salida != null) {
            salida.println(mensajeSerializado);
        }
    }
}