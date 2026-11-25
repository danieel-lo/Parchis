package servidor.interfaces;

import negocio.eventos.EventosDominio;

public interface ISuscriptorEventos {
    void alRecibirEvento(EventosDominio evento);
}