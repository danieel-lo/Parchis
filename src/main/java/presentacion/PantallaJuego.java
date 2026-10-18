/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package presentacion;

import control.ControladorDado;
import control.ControladorFicha;
import negocio.interfaces.IPartida;
import negocio.interfaces.ObservadorPartida;

/**
 *
 * @author Jorge
 */
public class PantallaJuego implements ObservadorPartida{

    private IPartida partida;
    private ControladorDado controladorDado;
    private ControladorFicha controladorFicha;

    public PantallaJuego(IPartida partida, ControladorDado controladorDado, ControladorFicha controladorFicha) {
        this.partida = partida;
        this.controladorDado = controladorDado;
        this.controladorFicha = controladorFicha;
    }

    @Override
    public void actualizar(IPartida iPartida) {

    }
}
