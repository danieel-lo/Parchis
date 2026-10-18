/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package negocio.clases;

import java.util.ArrayList;
import java.util.List;
import negocio.Enum.EstadoPartida;
import negocio.Enum.EstadoTurno;
import negocio.interfaces.IPartida;
import negocio.interfaces.ObservadorPartida;
import negocio.interfaces.SujetoPartida;

/**
 *
 * @author Jorge
 */
public class Partida implements IPartida, SujetoPartida {

    private List<Jugador> listaJugadores;
    private Jugador jugadorActual;
    private Tablero tablero;
    private Dado dado;
    private EstadoPartida estadoPartida;
    private EstadoTurno estadoTurno;
    private List<ObservadorPartida> observadores;
    private int bonificacionPendiente;
    private String mensajeTurno;
    private int numeroDeFichas;

    public Partida(Tablero tablero, int numeroDeFichas) {
        this.listaJugadores = new ArrayList<>();
        this.jugadorActual = null;
        this.tablero = tablero;
        this.dado = new Dado();
        this.estadoPartida = EstadoPartida.EN_CURSO;
        this.estadoTurno = EstadoTurno.ESPERANDO_TIRO;
        this.observadores = new ArrayList<>();
        this.bonificacionPendiente = 0;
        this.mensajeTurno = "";
        this.numeroDeFichas = numeroDeFichas;
    }

    public List<Jugador> getListaJugadores() {
        return listaJugadores;
    }

    public int getNumeroDeFichas() {
        return numeroDeFichas;
    }

    @Override
    public Jugador getJugadorActual() {
        return jugadorActual;
    }

    @Override
    public EstadoTurno getEstadoTurno() {
        return estadoTurno;
    }

    @Override
    public Tablero getTablero() {
        return tablero;
    }

    @Override
    public Dado getDado() {
        return dado;
    }

    @Override
    public String getMensajeTurno() {
        return mensajeTurno;
    }

    @Override
    public EstadoPartida getEstadoPartida() {
        return estadoPartida;
    }

    @Override
    public int getBonificacionPendiente() {
        return bonificacionPendiente;
    }

    public int lanzarDado() {
        return 0;
    }

    public boolean validarTresSeises(int resultado) {
        return false;
    }

    public void penalizarFicha(Ficha ficha) {

    }

    public List<Ficha> evaluarMovimiento(int resultadoDado) {
        return null;
    }

    public boolean procesarMovimiento(Ficha ficha) {
        return false;
    }

    public void evaluarCasillaDestino(Ficha ficha, Casilla destino) {

    }

    public void procesarCaptura(Ficha atacante, Ficha rival) {

    }

    public void procesarEntradaMeta(Ficha ficha, int pasosSobrantes) {

    }

    public int calcularRebote(int posicionActual, int pasos) {
        return 0;
    }

    public void otorgarBonificacion(int casillas) {

    }

    public boolean verificarFinPartida() {
        return false;
    }

    public List<Jugador> calcularPosicionesFinales() {
        return null;
    }

    public void finalizarTurno() {

    }

    @Override
    public void agregarObservador(ObservadorPartida obs) {
        observadores.add(obs);
    }

    @Override
    public void removerObservador(ObservadorPartida obs) {
        observadores.remove(obs);
    }

    @Override
    public void notificarObservadores() {
        observadores.forEach(p->p.actualizar(this));
    }

}
