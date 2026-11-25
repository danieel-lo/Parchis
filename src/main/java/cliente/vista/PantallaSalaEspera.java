package cliente.vista;

import cliente.interfaces.IObservadorVista;
import cliente.modelo.ModeloCliente;
import java.util.List;
import java.util.Scanner;
import negocio.clases.Jugador;

public class PantallaSalaEspera implements IObservadorVista {

    private final ModeloCliente modelo;

    public PantallaSalaEspera(ModeloCliente modelo) {
        this.modelo = modelo;
        this.modelo.agregarObservador(this);
    }

    public void iniciar() {
        System.out.println("\n>>> ¡Te has unido exitosamente a la partida! <<<");
        actualizarListaParticipantes();

        // Mantiene la consola abierta escuchando notificaciones en tiempo real
        Scanner scanner = new Scanner(System.in);
        System.out.println("(Escribe 'salir' en cualquier momento para cerrar este cliente)");
        while (true) {
            String comando = scanner.nextLine();
            if ("salir".equalsIgnoreCase(comando.trim())) {
                System.out.println("Saliendo de la sala...");
                System.exit(0);
            }
        }
    }

    public void actualizarListaParticipantes() {
        List<Jugador> jugadores = modelo.getListaJugadores();
        System.out.println("\n-----------------------------------------");
        System.out.println("   SALA DE ESPERA (" + jugadores.size() + "/4 jugadores)");
        System.out.println("-----------------------------------------");
        for (int i = 0; i < jugadores.size(); i++) {
            Jugador j = jugadores.get(i);
            System.out.println(" " + (i + 1) + ". " + j.getNombre()
                    + " | Color: " + j.getColor()
                    + " | Avatar: " + j.getAvatar());
        }
        System.out.println("-----------------------------------------");
        System.out.println("Esperando a los demás jugadores...");
    }

    @Override
    public void actualizar() {
        actualizarListaParticipantes();
    }
}