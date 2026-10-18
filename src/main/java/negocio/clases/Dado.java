/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package negocio.clases;

/**
 *
 * @author Jorge
 */
public class Dado {

    private int valocActual;

    public int lanzarDado() {
        valocActual = (int) (Math.random() * 6) + 1;
        return valocActual;
    }

    public int getValocActual() {
        return valocActual;
    }

}
