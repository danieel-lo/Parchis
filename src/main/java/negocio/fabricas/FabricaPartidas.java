/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package negocio.fabricas;

import negocio.clases.Dado;
import negocio.clases.Partida;
import negocio.clases.Tablero;
import negocio.interfaces.IFabricaPartidas;
import negocio.interfaces.IPartida;

/**
 *
 * @author Jorge
 */
public class FabricaPartidas implements IFabricaPartidas {

    @Override
    public IPartida crearPartida(int numeroFichas, int casillasSeguras, int valorSalida) {
        Tablero tablero = new Tablero(valorSalida, casillasSeguras);
        Dado dado = new Dado();
        Partida partida = new Partida(tablero, numeroFichas, dado);
        return partida;
    }

}
