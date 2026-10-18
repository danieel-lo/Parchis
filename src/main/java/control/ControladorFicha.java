/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package control;

import negocio.clases.Ficha;
import negocio.clases.Partida;

/**
 *
 * @author Jorge
 */
public class ControladorFicha {

    private Partida partida;

    public ControladorFicha(Partida partida) {
        this.partida = partida;
    }

    public boolean seleccionarFicha(Ficha ficha) {
        return partida.procesarMovimiento(ficha);
    }
}
