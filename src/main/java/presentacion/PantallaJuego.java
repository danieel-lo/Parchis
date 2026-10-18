/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package presentacion;

import control.ControladorDado;
import control.ControladorFicha;
import java.util.List;
import java.util.Scanner;
import negocio.Enum.EstadoPartida;
import negocio.Enum.EstadoTurno;
import negocio.clases.Ficha;
import negocio.clases.Partida;
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
    private Scanner scanner;

    public PantallaJuego(IPartida partida, ControladorDado controladorDado, ControladorFicha controladorFicha) {
        this.partida = partida;
        this.controladorDado = controladorDado;
        this.controladorFicha = controladorFicha;
        this.scanner = new Scanner(System.in);
    }

    @Override
    public void actualizar(IPartida iPartida) {
        System.out.println("\n------------------------------------------------");
        System.out.println(">>> Mensaje: " + iPartida.getMensajeTurno());
        System.out.println("--------------------------------------------------");
        if (iPartida.getJugadorActual() != null) {
            mostrarResumenTurno(iPartida);
        }
    }

    public void iniciar() {
        System.out.println("----------------------------------");
        System.out.println("      INICIANDO PARCHIS!          ");
        System.out.println("----------------------------------");

        while (partida.getEstadoPartida() != EstadoPartida.FINALIZAR) {
            EstadoTurno estado = partida.getEstadoTurno();

            if (estado == EstadoTurno.ESPERANDO_TIRO) {
                System.out.println("\nPresiona [ENTER] para tirar el dado (" + partida.getJugadorActual().getNombre() + "):");
                scanner.nextLine();
                controladorDado.lanzarDado();
            } 
            else if (estado == EstadoTurno.ESPERANDO_SELECCION_FICHA || estado == EstadoTurno.BONUS_DISPONIBLE) {
            int valorPasos = (partida.getBonificacionPendiente() > 0) ? partida.getBonificacionPendiente() : partida.getDado().getValocActual();

            // Obtenemos unicamente las fichas que SI tienen movimiento legal
            List<Ficha> opcionesValidas = ((Partida)partida).evaluarMovimiento(valorPasos);

            if (opcionesValidas.isEmpty()) {
                System.out.println("No hay opciones de movimiento disponibles.");
                continue;
            }

            System.out.println("\nFichas que puedes mover:");
            for (int i = 0; i < opcionesValidas.size(); i++) {
                Ficha f = opcionesValidas.get(i);
                String posStr = f.estaEnBase() ? "EN BASE (Sale a casilla de salida)" : "Casilla " + f.getPosicionActual();
                System.out.println(" " + (i + 1) + ") Ficha #" + f.getIdFicha() + " [" + posStr + "]");
            }

            System.out.print("Ingresa la opcion deseada (1-" + opcionesValidas.size() + "): ");
            int opcion = -1;
            try {
                opcion = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Entrada invalida. Ingresa un numero.");
                continue;
            }

            if (opcion >= 1 && opcion <= opcionesValidas.size()) {
                Ficha seleccionada = opcionesValidas.get(opcion - 1);
                controladorFicha.seleccionarFicha(seleccionada);
            } else {
                System.out.println("Opcion fuera de rango.");
            }
        }
            else if (estado == EstadoTurno.VICTORIA) {
                System.out.println("\n---------------------------------------------------");
                System.out.println("¡FIN DE LA PARTIDA! " + partida.getJugadorActual().getNombre() + " HA GANADO.");
                break;
            }
        }
    }

    private void mostrarResumenTurno(IPartida iPartida) {
        System.out.println("Jugador: " + iPartida.getJugadorActual().getNombre() + " (" + iPartida.getJugadorActual().getColor() + ")");
        System.out.println("Estado Turno: " + iPartida.getEstadoTurno());
        if (iPartida.getBonificacionPendiente() > 0) {
            System.out.println("Bonus pendiente: +" + iPartida.getBonificacionPendiente() + " casillas");
        }
    }
}
