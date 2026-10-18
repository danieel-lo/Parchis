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
public class Jugador {

    private String idJugador;
    private String nombre;
    private String avatar;
    private Color color;
    private List<Ficha> fichas;
    private int contadorSeisesSeguidos;
    private Ficha ultimaFichaMovida;
    private List<Jugada> jugadas;
    private boolean administrador;
    private boolean listo;

    public Jugador(String idJugador, String nombre, String avatar, Color color) {
        this.idJugador = idJugador;
        this.nombre = nombre;
        this.avatar = avatar;
        this.color = color;
        this.fichas = new ArrayList<>();
        this.contadorSeisesSeguidos = 0;
        this.ultimaFichaMovida = null;
        this.jugadas = new ArrayList<>();
        this.administrador = false;
        this.listo = false;
    }

    public String getIdJugador() {
        return idJugador;
    }

    public String getNombre() {
        return nombre;
    }

    public String getAvatar() {
        return avatar;
    }

    public Color getColor() {
        return color;
    }

    public List<Jugada> getJugadas() {
        return jugadas;
    }

    public List<Ficha> getFichas() {
        return fichas;
    }

    public boolean isAdministrador() {
        return administrador;
    }

    public boolean isListo() {
        return listo;
    }

    public int getContadorSeisesSeguidos() {
        return contadorSeisesSeguidos;
    }

    public Ficha getUltimaFichaMovida() {
        return ultimaFichaMovida;
    }

    public void registrarTiro(int valor) {
        if (valor == 6) {
            contadorSeisesSeguidos++;
        } else {
            reiniciarContadorSeises();
        }
    }

    public void reiniciarContadorSeises() {
        contadorSeisesSeguidos = 0;
    }

    public boolean tienenTresSeises() {
        return contadorSeisesSeguidos == 3;
    }

    public void registrarJugada(Jugada jugada) {
        jugadas.add(jugada);
        ultimaFichaMovida = jugada.getFicha();
    }

}
