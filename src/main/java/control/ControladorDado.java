/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package control;

import negocio.clases.Partida;

/**
 *
 * @author Jorge
 */
public class ControladorDado {

    private Partida partida;

    public ControladorDado(Partida partida) {
        this.partida = partida;
    }

    public int lanzarDado() {
        return partida.lanzarDado();
    }
}
