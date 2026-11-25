package servidor.eda;

import java.util.ArrayList;
import java.util.List;
import negocio.eventos.EventosDominio;
import servidor.interfaces.IPublicadorEventos;
import servidor.interfaces.ISuscriptorEventos;

public class BusEventos implements IPublicadorEventos {

    private final List<ISuscriptorEventos> suscriptores;

    public BusEventos() {
        this.suscriptores = new ArrayList<>();
    }

    public void suscribir(ISuscriptorEventos suscriptor) {
        suscriptores.add(suscriptor);
    }

    @Override
    public void publicar(EventosDominio evento) {
        for (ISuscriptorEventos suscriptor : suscriptores) {
            suscriptor.alRecibirEvento(evento);
        }
    }
}