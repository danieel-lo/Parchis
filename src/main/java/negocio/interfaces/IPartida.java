/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package negocio.interfaces;

import negocio.Enum.EstadoPartida;
import negocio.Enum.EstadoTurno;
import negocio.clases.Dado;
import negocio.clases.Jugador;
import negocio.clases.Tablero;

/**
 *
 * @author Jorge
 */
public interface IPartida {

    public Jugador getJugadorActual();

    public EstadoTurno getEstadoTurno();

    public Tablero getTablero();

    public Dado getDado();

    public String getMensajeTurno();

    public EstadoPartida getEstadoPartida();

    public int getBonificacionPendiente();
}
