/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package negocio.clases;

/**
 *
 * @author Jorge
 */
public class Jugada {

    private int valorDado;
    private Ficha ficha;
    private Casilla casillaOrigen;
    private Casilla casillaFinal;

    public Jugada(int valorDado, Ficha ficha,
            Casilla casillaOrigen, Casilla casillaFinal) {
        this.valorDado = valorDado;
        this.ficha = ficha;
        this.casillaOrigen = casillaOrigen;
        this.casillaFinal = casillaFinal;
    }

    public int getValorDado() {
        return valorDado;
    }

    public Ficha getFicha() {
        return ficha;
    }

    public Casilla getCasillaOrigen() {
        return casillaOrigen;
    }

    public Casilla getCasillaFinal() {
        return casillaFinal;
    }
}
