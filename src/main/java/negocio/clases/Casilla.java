/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package negocio.clases;

import java.util.ArrayList;
import java.util.List;
import negocio.Enum.Color;

/**
 *
 * @author Jorge
 */
public class Casilla {

    private int numero;
    private boolean esSeguro;
    private boolean esSalida;
    private boolean esMeta;
    private Color color;
    private List<Ficha> fichasPresentes;

    public Casilla(int numero, boolean esSeguro, boolean esSalida, boolean esMeta, Color color) {
        this.numero = numero;
        this.esSeguro = esSeguro;
        this.esSalida = esSalida;
        this.esMeta = esMeta;
        this.color = color;
        this.fichasPresentes = new ArrayList<>();
    }

    public int getNumero() {
        return numero;
    }

    public boolean isEsSeguro() {
        return esSeguro;
    }

    public boolean isEsSalida() {
        return esSalida;
    }

    public boolean isEsMeta() {
        return esMeta;
    }

    public Color getColor() {
        return color;
    }

    public List<Ficha> getFichasPresentes() {
        return fichasPresentes;
    }

    public void agregarFicha(Ficha ficha) {
        fichasPresentes.add(ficha);
    }

    public void removerFicha(Ficha ficha) {
        fichasPresentes.remove(ficha);
    }

    public boolean tieneBarreras() {
        for (int i = 0; i < fichasPresentes.size(); i++) {
            for (int j = i + 1; j < fichasPresentes.size(); j++) {
                if (fichasPresentes.get(i).getColor() == fichasPresentes.get(j).getColor()) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean estaOcupadaPorRival(Color colorPropio) {
        return fichasPresentes.stream().anyMatch(p-> p.getColor() != colorPropio);
    }

    public Ficha obtenerFichaRival(Color colorPropio) {
        return fichasPresentes.stream().filter(p -> p.getColor() != colorPropio).findFirst().orElse(null);
    }

}
