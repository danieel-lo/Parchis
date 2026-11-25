package cliente.vista;

import cliente.control.ControlUnirsePartida;
import cliente.interfaces.IObservadorVista;
import cliente.modelo.ModeloCliente;
import java.util.Scanner;
import negocio.Enum.Color;

public class PantallaUnirsePartida implements IObservadorVista {

    private final ControlUnirsePartida control;
    private final ModeloCliente modelo;
    private final Scanner scanner;
    private final Object lockRespuesta = new Object();
    private boolean esperandoRespuesta = false;

    public PantallaUnirsePartida(ControlUnirsePartida control) {
        this.control = control;
        this.modelo = control.getModelo();
        this.modelo.agregarObservador(this);
        this.scanner = new Scanner(System.in);
    }

    public void iniciar() {
        System.out.println("=========================================");
        System.out.println("      PARCHIS - UNIRSE A PARTIDA         ");
        System.out.println("=========================================");

        while (!modelo.isUnionExitosa()) {
            solicitarUnirse();

            // Esperamos a que el hilo del SocketCliente reciba la respuesta del servidor
            synchronized (lockRespuesta) {
                while (esperandoRespuesta) {
                    try {
                        lockRespuesta.wait();
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                }
            }

            if (modelo.getUltimoMensajeError() != null) {
                mostrarMensajeError(modelo.getUltimoMensajeError());
            }
        }

        abrirSalaEspera(modelo);
    }

    public void solicitarUnirse() {
        System.out.print("\nIngresa tu nombre: ");
        String nombre = scanner.nextLine().trim();

        System.out.print("Ingresa tu avatar (ej. avatar1.png): ");
        String avatar = scanner.nextLine().trim();
        if (avatar.isEmpty()) {
            avatar = "avatar_default.png";
        }

        Color colorSeleccionado = pedirColor();

        esperandoRespuesta = true;
        System.out.println("Enviando solicitud al servidor...");
        control.solicitarUnirse(nombre, avatar, colorSeleccionado);
    }

    private Color pedirColor() {
        Color[] colores = Color.values();
        while (true) {
            System.out.println("Selecciona un color:");
            for (int i = 0; i < colores.length; i++) {
                System.out.println("  " + (i + 1) + ") " + colores[i]);
            }
            System.out.print("Opción (1-" + colores.length + "): ");
            try {
                int opcion = Integer.parseInt(scanner.nextLine().trim());
                if (opcion >= 1 && opcion <= colores.length) {
                    return colores[opcion - 1];
                }
            } catch (NumberFormatException ignored) {
            }
            System.out.println("Opción inválida, intenta de nuevo.");
        }
    }

    public void mostrarMensajeError(String mensaje) {
        System.out.println("\n[!] ERROR AL UNIRSE: " + mensaje);
    }

    public void abrirSalaEspera(ModeloCliente modelo) {
        modelo.removerObservador(this);
        PantallaSalaEspera salaEspera = new PantallaSalaEspera(modelo);
        salaEspera.iniciar();
    }

    @Override
    public void actualizar() {
        synchronized (lockRespuesta) {
            esperandoRespuesta = false;
            lockRespuesta.notifyAll();
        }
    }
}