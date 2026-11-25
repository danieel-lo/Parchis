package cliente.aplicacion;

import cliente.control.ControlUnirsePartida;
import cliente.red.SocketCliente;
import java.util.ArrayList;
import java.util.List;
import negocio.Enum.Color;
import negocio.clases.Jugador;

public class AplicacionCliente {

    private final SocketCliente socketCliente;
    private ControlUnirsePartida control;

    public AplicacionCliente(SocketCliente socketCliente) {
        this.socketCliente = socketCliente;
    }

    public void setControl(ControlUnirsePartida control) {
        this.control = control;
    }

    public void enviarSolicitudUnion(String nombre, String avatar, Color color) {
        // Formato: UNIRSE|nombre|avatar|COLOR
        String payload = "UNIRSE|" + nombre + "|" + avatar + "|" + color.name();
        socketCliente.enviarMensaje(payload);
    }

    public void procesarNotificacion(String mensajeCrudo) {
        if (mensajeCrudo == null || mensajeCrudo.isEmpty() || control == null) {
            return;
        }

        String[] partes = mensajeCrudo.split("\\|", 3);
        String comando = partes[0];

        if ("JUGADOR_UNIDO".equals(comando) && partes.length == 3) {
            String nombreJugadorUnido = partes[1];
            List<Jugador> listaActualizada = deserializarListaJugadores(partes[2]);
            control.recibirNotificacionUnion(nombreJugadorUnido, listaActualizada);

        } else if ("ERROR_UNION".equals(comando) && partes.length >= 2) {
            control.notificarErrorUnion(partes[1]);
        }
    }

    private List<Jugador> deserializarListaJugadores(String datosLista) {
        List<Jugador> jugadores = new ArrayList<>();
        if (datosLista == null || datosLista.isEmpty()) {
            return jugadores;
        }

        String[] registros = datosLista.split(";");
        for (String reg : registros) {
            String[] campos = reg.split(":");
            if (campos.length == 4) {
                String id = campos[0];
                String nombre = campos[1];
                String avatar = campos[2];
                Color color = Color.valueOf(campos[3]);
                jugadores.add(new Jugador(id, nombre, avatar, color));
            }
        }
        return jugadores;
    }
}