/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package presentacion;

import control.ControlConfigurarPartida;
import negocio.fabricas.FabricaPartidas;
import negocio.interfaces.IFabricaPartidas;
import simulacion.SimuladorInicioPartida;

/**
 *
 * @author Jorge
 */
public class Prueba {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        IFabricaPartidas fabrica = new FabricaPartidas();
        ControlConfigurarPartida controlador = new ControlConfigurarPartida(fabrica);
        PantallaPrincipal pantalla = new PantallaPrincipal(controlador);

        pantalla.setPartidaCreadaListener(partida -> {
            SimuladorInicioPartida simulador = new SimuladorInicioPartida();
            simulador.iniciar(partida);
        });
        pantalla.setVisible(true);
    }

}
