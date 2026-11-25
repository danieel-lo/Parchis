package servidor.interfaces;

/**
 * Contrato provisto por SocketServidor y requerido por ManejadorNotificaciones
 * para enviar eventos serializados de vuelta a los clientes.
 */
public interface IEnviadorNotificaciones {
    void enviarNotificacionATodos(String mensajeSerializado);
    void enviarNotificacionACliente(String idConexionCliente, String mensajeSerializado);
}