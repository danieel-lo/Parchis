/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package control;

import negocio.interfaces.IFabricaPartidas;
import negocio.interfaces.IPartida;

/**
 *
 * @author Jorge
 */
public class ControlConfigurarPartida {

    private final IFabricaPartidas fabrica;

    public ControlConfigurarPartida(IFabricaPartidas fabrica) {
        this.fabrica = fabrica;
    }

    public IPartida solicitarCrearPartida(int numeroFichas, int casillasSeguras, int valorSalida) {
        IPartida partida = fabrica.crearPartida(numeroFichas, casillasSeguras, valorSalida);
        return partida;
    }

    public void confirmarConfiguracion(IPartida partida) {
        partida.confirmarConfiguracion();
    }

}
