package servidor.eda;

import java.util.stream.Collectors;
import negocio.clases.Jugador;
import negocio.eventos.EventosDominio;
import negocio.eventos.JugadorUnidoEvent;
import servidor.interfaces.IEnviadorNotificaciones;
import servidor.interfaces.ISuscriptorEventos;

public class ManejadorNotificaciones implements ISuscriptorEventos {

    private final IEnviadorNotificaciones enviador;

    public ManejadorNotificaciones(IEnviadorNotificaciones enviador) {
        this.enviador = enviador;
    }

    @Override
    public void alRecibirEvento(EventosDominio evento) {
        if (evento instanceof JugadorUnidoEvent) {
            manejarJugadorUnido((JugadorUnidoEvent) evento);
        }
    }

    private void manejarJugadorUnido(JugadorUnidoEvent evento) {
        // Serializamos la lista completa de jugadores para sincronizar a todos los clientes
        String listaSerializada = evento.getListaJugadores().stream()
                .map(j -> j.getIdJugador() + ":" + j.getNombre() + ":" + j.getAvatar() + ":" + j.getColor().name())
                .collect(Collectors.joining(";"));

        String payload = "JUGADOR_UNIDO|" + evento.getJugador().getNombre() + "|" + listaSerializada;

        // Broadcast a todos los clientes conectados
        enviador.enviarNotificacionATodos(payload);
    }
}