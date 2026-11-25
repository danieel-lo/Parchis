package cliente.modelo;

import cliente.interfaces.IObservadorVista;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import negocio.clases.Jugador;

public class ModeloCliente {

    private List<Jugador> listaJugadores;
    private boolean partidaActiva;
    private String ultimoMensajeError;
    private boolean unionExitosa;
    private final List<IObservadorVista> observadores;

    public ModeloCliente() {
        this.listaJugadores = new ArrayList<>();
        this.partidaActiva = false;
        this.ultimoMensajeError = null;
        this.unionExitosa = false;
        this.observadores = new ArrayList<>();
    }

    public void agregarObservador(IObservadorVista obs) {
        if (!observadores.contains(obs)) {
            observadores.add(obs);
        }
    }

    public void removerObservador(IObservadorVista obs) {
        observadores.remove(obs);
    }

    public void actualizarSala(List<Jugador> jugadores, boolean exitoLocal) {
        this.listaJugadores = new ArrayList<>(jugadores);
        this.ultimoMensajeError = null;
        if (exitoLocal) {
            this.unionExitosa = true;
        }
        notificarObservadores();
    }

    public void registrarError(String mensajeError) {
        this.ultimoMensajeError = mensajeError;
        notificarObservadores();
    }

    private void notificarObservadores() {
        // Copia para evitar ConcurrentModificationException cuando se abre la Sala de Espera
        for (IObservadorVista obs : new ArrayList<>(observadores)) {
            obs.actualizar();
        }
    }

    public List<Jugador> getListaJugadores() {
        return Collections.unmodifiableList(listaJugadores);
    }

    public boolean isPartidaActiva() {
        return partidaActiva;
    }

    public String getUltimoMensajeError() {
        return ultimoMensajeError;
    }

    public boolean isUnionExitosa() {
        return unionExitosa;
    }
}