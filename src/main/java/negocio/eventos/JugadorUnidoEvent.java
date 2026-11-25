package negocio.eventos;

import java.util.ArrayList;
import java.util.List;
import negocio.clases.Jugador;

public class JugadorUnidoEvent extends EventosDominio {

    private final Jugador jugador;
    private final List<Jugador> listaJugadores;

    public JugadorUnidoEvent(Jugador jugador, List<Jugador> listaJugadores) {
        super();
        this.jugador = jugador;
        this.listaJugadores = new ArrayList<>(listaJugadores);
    }

    public Jugador getJugador() {
        return jugador;
    }

    public List<Jugador> getListaJugadores() {
        return listaJugadores;
    }
}