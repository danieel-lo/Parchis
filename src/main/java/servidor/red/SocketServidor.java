package servidor.red;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import servidor.interfaces.IEnviadorNotificaciones;
import servidor.interfaces.IProcesadorSolicitudes;

public class SocketServidor implements IEnviadorNotificaciones {

    private final int puerto;
    private IProcesadorSolicitudes procesadorSolicitudes;
    private final Map<String, PrintWriter> clientesConectados;
    private boolean ejecutando;

    public SocketServidor(int puerto) {
        this.puerto = puerto;
        this.clientesConectados = new ConcurrentHashMap<>();
        this.ejecutando = false;
    }

    public void setProcesadorSolicitudes(IProcesadorSolicitudes procesadorSolicitudes) {
        this.procesadorSolicitudes = procesadorSolicitudes;
    }

    public void iniciar() {
        ejecutando = true;
        new Thread(() -> {
            try (ServerSocket serverSocket = new ServerSocket(puerto)) {
                System.out.println("[ServidorParchis] Escuchando en el puerto " + puerto + "...");
                while (ejecutando) {
                    Socket socketCliente = serverSocket.accept();
                    String idConexion = UUID.randomUUID().toString();
                    atenderCliente(idConexion, socketCliente);
                }
            } catch (IOException e) {
                System.err.println("[ServidorParchis] Error en el socket servidor: " + e.getMessage());
            }
        }).start();
    }

    private void atenderCliente(String idConexion, Socket socket) {
        new Thread(() -> {
            try (
                BufferedReader entrada = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                PrintWriter salida = new PrintWriter(socket.getOutputStream(), true)
            ) {
                clientesConectados.put(idConexion, salida);
                System.out.println("[ServidorParchis] Cliente conectado: " + idConexion);

                String mensaje;
                while ((mensaje = entrada.readLine()) != null) {
                    if (procesadorSolicitudes != null) {
                        procesadorSolicitudes.procesarSolicitud(mensaje, idConexion);
                    }
                }
            } catch (IOException e) {
                System.out.println("[ServidorParchis] Cliente desconectado: " + idConexion);
            } finally {
                clientesConectados.remove(idConexion);
            }
        }).start();
    }

    @Override
    public void enviarNotificacionATodos(String mensajeSerializado) {
        for (PrintWriter out : clientesConectados.values()) {
            out.println(mensajeSerializado);
        }
    }

    @Override
    public void enviarNotificacionACliente(String idConexionCliente, String mensajeSerializado) {
        PrintWriter out = clientesConectados.get(idConexionCliente);
        if (out != null) {
            out.println(mensajeSerializado);
        }
    }
}