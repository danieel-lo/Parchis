package servidor.aplicacion;

import negocio.Enum.Color;
import negocio.clases.Partida;
import negocio.eventos.JugadorUnidoEvent;
import servidor.interfaces.IEnviadorNotificaciones;
import servidor.interfaces.IProcesadorSolicitudes;
import servidor.interfaces.IPublicadorEventos;

public class AplicacionPartida implements IProcesadorSolicitudes {

    private final Partida partida;
    private final IPublicadorEventos publicadorEventos;
    private final IEnviadorNotificaciones enviadorNotificaciones;

    public AplicacionPartida(Partida partida, IPublicadorEventos publicadorEventos, IEnviadorNotificaciones enviadorNotificaciones) {
        this.partida = partida;
        this.publicadorEventos = publicadorEventos;
        this.enviadorNotificaciones = enviadorNotificaciones;
    }

    @Override
    public void procesarSolicitud(String mensajeCrudo, String idConexionCliente) {
        
        if (mensajeCrudo == null || mensajeCrudo.isEmpty()) return;

        String[] partes = mensajeCrudo.split("\\|");
        String comando = partes[0];

        if ("UNIRSE".equals(comando) && partes.length >= 4) {
            procesarSolicitudUnion(partes[1], partes[2], partes[3], idConexionCliente);
        }
    }

    private void procesarSolicitudUnion(String nombre, String avatar, String colorStr, String idConexionCliente) {
        try {
            Color color = Color.valueOf(colorStr.toUpperCase());
            
            //Ejecutar lógica en el Aggregate Root 
            JugadorUnidoEvent evento = partida.unirseAPartida(nombre, avatar, color);

            // Publicar el evento retornado en el Bus de Eventos 
            publicadorEventos.publicar(evento);

        } catch (IllegalArgumentException | IllegalStateException e) {
            // Flujo alternativo: Sala llena, partida en curso o color ocupado
            enviadorNotificaciones.enviarNotificacionACliente(idConexionCliente, "ERROR_UNION|" + e.getMessage());
        } catch (Exception e) {
            enviadorNotificaciones.enviarNotificacionACliente(idConexionCliente, "ERROR_UNION|Datos de unión inválidos.");
        }
    }
}