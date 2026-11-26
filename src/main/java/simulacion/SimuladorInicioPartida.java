/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package simulacion;

import control.ControladorDado;
import control.ControladorFicha;
import negocio.Enum.Color;
import negocio.clases.Jugador;
import negocio.clases.Partida;
import negocio.interfaces.IPartida;
import presentacion.PantallaJuego;

/**
 *
 * @author Jorge
 */
public class SimuladorInicioPartida {

    public void iniciar(IPartida partida) {
        if (!(partida instanceof Partida)) {
            throw new IllegalArgumentException("El simulador requiere una Partida");
        }
        iniciar((Partida) partida);
    }

    public void iniciar(Partida partida) {

        System.out.println("--- CONFIGURACION RECIBIDA ---");
        System.out.println("Fichas: " + partida.getNumeroDeFichas());
        System.out.println("Casillas seguras: " + partida.getTablero().getCasillasSegurasPorAla());
        System.out.println("Valor de salida: " + partida.getTablero().getValorSalidaConfigurado());

        Jugador j1 = new Jugador("1", "Jugador 1", "avatar1.png", Color.AMARILLO);
        Jugador j2 = new Jugador("2", "Jugador 2", "avatar2.png", Color.ROJO);

        partida.agregarJugador(j1);
        partida.agregarJugador(j2);

        ControladorDado cDado = new ControladorDado(partida);
        ControladorFicha cFicha = new ControladorFicha(partida);

        PantallaJuego pantalla = new PantallaJuego(partida, cDado, cFicha);
        partida.agregarObservador(pantalla);

        Thread hiloJuego = new Thread(() -> pantalla.iniciar());
        hiloJuego.start();
    }
}
