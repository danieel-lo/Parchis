package servidor;

import negocio.clases.Partida;
import negocio.clases.Tablero;
import servidor.aplicacion.AplicacionPartida;
import servidor.eda.BusEventos;
import servidor.eda.ManejadorNotificaciones;
import servidor.red.SocketServidor;

public class MainServidor {

    public static void main(String[] args) {
        // Instanciar Dominio
        Tablero tablero = new Tablero(5, 3);
        Partida partida = new Partida(tablero, 4);

        // Instanciar Red e Infraestructura EDA
        SocketServidor socketServidor = new SocketServidor(5000);
        BusEventos busEventos = new BusEventos();
        ManejadorNotificaciones manejadorNotificaciones = new ManejadorNotificaciones(socketServidor);

        // Conectar suscriptor al Bus
        busEventos.suscribir(manejadorNotificaciones);

        // Instanciar Capa de Aplicación e inyectar contratos
        AplicacionPartida aplicacionPartida = new AplicacionPartida(partida, busEventos, socketServidor);
        socketServidor.setProcesadorSolicitudes(aplicacionPartida);

        // Arrancar servidor
        socketServidor.iniciar();
    }
}