/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package presentacion;

import control.ControladorDado;
import control.ControladorFicha;
import negocio.Enum.Color;
import negocio.clases.Jugador;
import negocio.clases.Partida;
import negocio.clases.Tablero;

/**
 *
 * @author Jorge
 */
public class Parchis {

    public static void main(String[] args) {
            // Crear Tablero 
            Tablero tablero = new Tablero(5, 2);

            // Crear Partida con 4 fichas por jugador
            Partida partida = new Partida(tablero, 4);

            // Crear Jugadores
            Jugador j1 = new Jugador("1", "Jorge", "avatar1.png", Color.AMARILLO);
            Jugador j2 = new Jugador("2", "Emily", "avatar2.png", Color.ROJO);
            partida.agregarJugador(j1);
            partida.agregarJugador(j2);

            // Crear Controladores
            ControladorDado cDado = new ControladorDado(partida);
            ControladorFicha cFicha = new ControladorFicha(partida);

            // Crear la Pantalla de Consola y suscribirla como Observador
            PantallaJuego pantalla = new PantallaJuego(partida, cDado, cFicha);
            partida.agregarObservador(pantalla);

            
            pantalla.iniciar();
        }
    }

