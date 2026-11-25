package cliente.control;

import cliente.aplicacion.AplicacionCliente;
import cliente.modelo.ModeloCliente;
import java.util.List;
import negocio.Enum.Color;
import negocio.clases.Jugador;

public class ControlUnirsePartida {

    private final AplicacionCliente appCliente;
    private final ModeloCliente modelo;
    private String nombreJugadorLocal;

    public ControlUnirsePartida(AplicacionCliente appCliente, ModeloCliente modelo) {
        this.appCliente = appCliente;
        this.modelo = modelo;
    }

    public void solicitarUnirse(String nombre, String avatar, Color color) {
        this.nombreJugadorLocal = nombre;
        appCliente.enviarSolicitudUnion(nombre, avatar, color);
    }

    public void recibirNotificacionUnion(String nombreJugadorUnido, List<Jugador> listaActualizada) {
        boolean esEsteJugador = nombreJugadorLocal != null && nombreJugadorLocal.equalsIgnoreCase(nombreJugadorUnido);
        modelo.actualizarSala(listaActualizada, esEsteJugador);
    }

    public void notificarErrorUnion(String mensajeError) {
        modelo.registrarError(mensajeError);
    }

    public ModeloCliente getModelo() {
        return modelo;
    }
}