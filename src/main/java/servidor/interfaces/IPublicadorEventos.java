package servidor.interfaces;

import negocio.eventos.EventosDominio;

/**
 * Contrato provisto por BusEventos y requerido por AplicacionPartida
 * para publicar eventos emitidos por el dominio.
 */
public interface IPublicadorEventos {
    void publicar(EventosDominio evento);
}