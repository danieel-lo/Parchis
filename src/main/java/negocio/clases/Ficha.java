/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package negocio.clases;

import negocio.Enum.Color;
import negocio.Enum.EstadoFicha;

/**
 *
 * @author Jorge
 */
public class Ficha {

    private int idFicha;
    private Color color;
    private int posicionActual;
    private EstadoFicha estado;

    public Ficha(int idFicha, Color color) {
        this.idFicha = idFicha;
        this.color = color;
        this.posicionActual = -1;
        this.estado = EstadoFicha.EN_BASE;
    }

    public int getIdFicha() {
        return idFicha;
    }

    public Color getColor() {
        return color;
    }

    public int getPosicionActual() {
        return posicionActual;
    }

    public EstadoFicha getEstado() {
        return estado;
    }

    public void moverA(int nuevaPosicion) {
        posicionActual = nuevaPosicion;
        estado = EstadoFicha.EN_JUEGO;
    }

    public boolean regresarAlCirculo() {
        posicionActual = -1;
        estado = EstadoFicha.EN_BASE;
        return true;
    }

    public boolean estaEnBase() {
        return estado == EstadoFicha.EN_BASE;
    }

    public void llegarAMeta() {
        estado = EstadoFicha.EN_META;
    }

}
