package servidor.interfaces;

/**
 * Contrato provisto por AplicacionPartida y requerido por SocketServidor
 * para procesar mensajes entrantes desde la red.
 */
public interface IProcesadorSolicitudes {
    void procesarSolicitud(String mensajeCrudo, String idConexionCliente);
}