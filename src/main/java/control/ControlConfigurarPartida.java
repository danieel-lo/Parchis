/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package control;

import negocio.clases.Partida;
import negocio.clases.Tablero;
import negocio.interfaces.ObservadorPartida;

/**
 *
 * @author Jorge
 */
public class ControlConfigurarPartida {
    
    public Partida solicitarCrearPartida(int numeroFichas, int casillasSeguras, int valorSalida, ObservadorPartida  observador) {
        Tablero tablero=new Tablero(valorSalida,casillasSeguras);
        Partida partida=new Partida(tablero, numeroFichas);
        partida.agregarObservador(observador);
        partida.notificarObservadores();
        return partida;
    }
    
}
