package negocio.eventos;

import java.time.Instant;

public abstract class EventosDominio {

    private final Instant fechaOcurrencia;

    public EventosDominio() {
        this.fechaOcurrencia = Instant.now();
    }

    public Instant getFechaOcurrencia() {
        return fechaOcurrencia;
    }
}