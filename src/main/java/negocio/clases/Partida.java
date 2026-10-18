/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package negocio.clases;

import java.util.ArrayList;
import java.util.List;
import negocio.Enum.EstadoFicha;
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
        //inicializamos las listas y las variables de control de la partida
        this.listaJugadores = new ArrayList<>();
        this.jugadorActual = null;
        this.tablero = tablero;
        this.dado = new Dado();
        this.estadoPartida = EstadoPartida.EN_CURSO;
        this.estadoTurno = EstadoTurno.ESPERANDO_TIRO;
        this.observadores = new ArrayList<>();
        this.bonificacionPendiente = 0;
        this.mensajeTurno = "Partida iniciada. Esperando lanzamiento de dado.";
        this.numeroDeFichas = numeroDeFichas;
    }

    public void agregarJugador(Jugador jugador) {
        //agregamos el jugador a la lista de la partida
        listaJugadores.add(jugador);
        //generamos las fichas correspondientes para este jugador segun lo configurado
        for (int i = 1; i <= numeroDeFichas; i++) {
            jugador.getFichas().add(new Ficha(i, jugador.getColor()));
        }
        //si es el primer jugador que agregamos, lo asignamos como el jugador actual
        if (jugadorActual == null) {
            jugadorActual = jugador;
            mensajeTurno = "Turno inicial de: " + jugadorActual.getNombre();
        }
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
        //si la partida ya termino no permitimos lanzar el dado
        if (estadoPartida == EstadoPartida.FINALIZAR) {
            return 0;
        }

        //lanzamos el dado y obtenemos el resultado
        int resultado = dado.lanzarDado();
        if (jugadorActual != null) {
            //registramos el tiro en la entidad del jugador
            jugadorActual.registrarTiro(resultado);

            //comprobamos la regla de sacar tres 6 consecutivos
            if (jugadorActual.tienenTresSeises()) {
                //obtenemos la ultima ficha movida para penalizarla
                Ficha ultima = jugadorActual.getUltimaFichaMovida();
                //si la ficha existe y no estaba en base la regresamos a la base
                if (ultima != null && !ultima.estaEnBase()) {
                    penalizarFicha(ultima);
                }
                estadoTurno = EstadoTurno.TURNO_PENALIZADO;
                String nombreAnterior = jugadorActual.getNombre();
                //pasamos el turno al siguiente jugador
                finalizarTurno();
                mensajeTurno = nombreAnterior + " saco tres 6 seguidos! Ficha penalizada. Turno de " + jugadorActual.getNombre();
                //notificamos a la vista los cambios
                notificarObservadores();
                return resultado;
            }

            //evaluamos si el jugador tiene fichas que pueda mover con este resultado
            List<Ficha> movibles = evaluarMovimiento(resultado);
            //si no tiene fichas movibles
            if (movibles.isEmpty()) {
                //si no saco un 6 pierde el turno y pasa al siguiente
                if (resultado != 6) {
                    String nombreAnterior = jugadorActual.getNombre();
                    finalizarTurno();
                    mensajeTurno = nombreAnterior + " saco un " + resultado + " (sin movimientos). Turno de " + jugadorActual.getNombre();
                } else {
                    //si saco un 6 pero no tiene movimientos vuelve a tirar
                    mensajeTurno = jugadorActual.getNombre() + " saco un 6 pero no tiene movimientos. Vuelve a tirar!";
                    estadoTurno = EstadoTurno.ESPERANDO_TIRO;
                }
            } else {
                //si tiene fichas movibles cambiamos el estado para esperar que seleccione una
                estadoTurno = EstadoTurno.ESPERANDO_SELECCION_FICHA;
                mensajeTurno = jugadorActual.getNombre() + " saco un " + resultado + ". Selecciona la ficha que deseas mover.";
            }
        }
        //notificamos a los observadores para actualizar la interfaz
        notificarObservadores();
        return resultado;
    }

    public List<Ficha> evaluarMovimiento(int resultadoDado) {
        //creamos la lista donde guardaremos las fichas que se pueden mover
        List<Ficha> movibles = new ArrayList<>();
        if (jugadorActual == null) return movibles;

        //determinamos si vamos a mover por bonus pendiente o por el tiro del dado
        int pasos = (bonificacionPendiente > 0) ? bonificacionPendiente : resultadoDado;

        //recorremos todas las fichas del jugador actual
        for (Ficha ficha : jugadorActual.getFichas()) {
            //si la ficha ya llego a la meta la ignoramos
            if (ficha.getEstado() == EstadoFicha.EN_META) {
                continue;
            }
            int posOrigen = ficha.getPosicionActual();
            
            //comprobamos si la ficha esta en la base
            if (ficha.estaEnBase()) {
                //solo se puede mover si los pasos equivalen al valor configurado de salida
                if (pasos == tablero.getValorSalidaConfigurado()) {
                    movibles.add(ficha);
                }
                continue;
            }

            //si la ficha esta en juego verificamos que no haya barreras en su recorrido
            if (!tablero.hayBarreraEnTrayecto(posOrigen, pasos, ficha.getColor())) {
                //calculamos la casilla de destino
                Casilla destino = tablero.calcularCasillaDestino(posOrigen, pasos, ficha.getColor());
                //si hay una casilla de destino valida agregamos la ficha a la lista
                if (destino != null) {
                    movibles.add(ficha);
                }
            }
        }
        //regresamos la lista con las fichas movibles
        return movibles;
    }

    public boolean procesarMovimiento(Ficha ficha) {
       if (jugadorActual == null || ficha == null) return false;

        //obtenemos los pasos a avanzar usando el bonus o el valor del dado
        int pasos = (bonificacionPendiente > 0) ? bonificacionPendiente : dado.getValocActual();
        //reiniciamos la bonificacion pendiente porque ya se consumio
        bonificacionPendiente = 0; 

        int posOrigen = ficha.getPosicionActual();
        //calculamos a que casilla debe llegar la ficha
        Casilla destino = tablero.calcularCasillaDestino(posOrigen, pasos, ficha.getColor());

        //si no hay casilla valida cancelamos el movimiento
        if (destino == null) {
            mensajeTurno = "Movimiento invalido o bloqueado por barrera.";
            notificarObservadores();
            return false;
        }

        //removemos la ficha de su casilla de origen si no estaba en la base
        if (posOrigen != -1) {
            Casilla origen = tablero.obtenerCasilla(posOrigen);
            if (origen != null) {
                origen.removerFicha(ficha);
            }
        }

        //comprobamos si la casilla de destino es la meta
        if (destino.isEsMeta()) {
            ficha.llegarAMeta();
            procesarEntradaMeta(ficha, 0);
        } 
        //comprobamos si la casilla esta ocupada por un rival para hacer una captura
        else if (destino.estaOcupadaPorRival(ficha.getColor()) && !destino.isEsSeguro()) {
            Ficha rival = destino.obtenerFichaRival(ficha.getColor());
            ficha.moverA(destino.getNumero());
            destino.agregarFicha(ficha);
            if (rival != null) {
                procesarCaptura(ficha, rival);
            }
        } 
        //si es un movimiento normal o casilla segura simplemente movemos la ficha
        else {
            ficha.moverA(destino.getNumero());
            destino.agregarFicha(ficha);
            mensajeTurno = "Ficha #" + ficha.getIdFicha() + " movida a casilla " + destino.getNumero();
        }

        //guardamos el registro de la jugada realizada
        Casilla origenCasilla = (posOrigen != -1) ? tablero.obtenerCasilla(posOrigen) : null;
        jugadorActual.registrarJugada(new Jugada(pasos, ficha, origenCasilla, destino));

        //comprobamos si con este movimiento el jugador gano la partida
        if (verificarFinPartida()) {
            estadoPartida = EstadoPartida.FINALIZAR;
            estadoTurno = EstadoTurno.VICTORIA;
            mensajeTurno = "Felicidades! " + jugadorActual.getNombre() + " ha ganado la partida.";
        } else if (bonificacionPendiente > 0) {
            //si le quedo un bonus pendiente cambiamos el estado para que vuelva a mover
            estadoTurno = EstadoTurno.BONUS_DISPONIBLE;
        } else if (dado.getValocActual() == 6) {
            //si saco un 6 conserva el turno para volver a tirar
            estadoTurno = EstadoTurno.ESPERANDO_TIRO;
            mensajeTurno += " Sacaste un 6! Vuelves a tirar.";
        } else {
            //si no hubo bonus ni 6 finalizamos el turno
            finalizarTurno();
        }

        //notificamos los cambios a la interfaz
        notificarObservadores();
        return true;
    }

    public void penalizarFicha(Ficha ficha) {
        //validamos que la ficha no sea nula y no este en la base
        if (ficha != null && !ficha.estaEnBase()) {
            //obtenemos su casilla actual y la removemos de ahi
            Casilla c = tablero.obtenerCasilla(ficha.getPosicionActual());
            if (c != null) {
                c.removerFicha(ficha);
            }
            //regresamos la ficha a la base
            ficha.regresarAlCirculo();
        }
    }

    public void procesarCaptura(Ficha atacante, Ficha rival) {
        if (rival != null) {
            //removemos la ficha rival de la casilla donde fue capturada
            Casilla c = tablero.obtenerCasilla(rival.getPosicionActual());
            if (c != null) {
                c.removerFicha(rival);
            }
            //enviamos la ficha rival de vuelta a su base
            rival.regresarAlCirculo();
            //otorgamos la bonificacion de 20 casillas
            otorgarBonificacion(20);
            mensajeTurno = "Capturaste una ficha rival! Obtienes +20 casillas de bonus.";
        }
    }

    public void procesarEntradaMeta(Ficha ficha, int pasosSobrantes) {
        //al meter una ficha a la meta se otorgan 10 casillas de bonificacion
        otorgarBonificacion(10);
        mensajeTurno = "Ficha #" + ficha.getIdFicha() + " ha llegado a la META! Obtienes +10 casillas de bonus.";
    }

    public void otorgarBonificacion(int casillas) {
        //guardamos la cantidad de casillas pendientes por avanzar de bonus
        this.bonificacionPendiente = casillas;
    }

    public boolean verificarFinPartida() {
        if (jugadorActual == null) return false;
        //comprobamos si todas las fichas del jugador actual estan en la meta
        return jugadorActual.getFichas().stream()
                .allMatch(f -> f.getEstado() == EstadoFicha.EN_META);
    }

    public void finalizarTurno() {
        if (listaJugadores.isEmpty()) return;
        //reiniciamos el contador de seises del jugador que termina
        jugadorActual.reiniciarContadorSeises();
        //obtenemos el indice del siguiente jugador de forma ciclica
        int idx = listaJugadores.indexOf(jugadorActual);
        jugadorActual = listaJugadores.get((idx + 1) % listaJugadores.size());
        //restablecemos el estado del turno para esperar el siguiente tiro
        estadoTurno = EstadoTurno.ESPERANDO_TIRO;
        mensajeTurno = "Turno de " + jugadorActual.getNombre();
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
        observadores.forEach(obs -> obs.actualizar(this));
    }

}
