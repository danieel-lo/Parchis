/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package negocio.interfaces;

/**
 *
 * @author Jorge
 */
public interface SujetoPartida {

    public void agregarObservador(ObservadorPartida obs);

    public void removerObservador(ObservadorPartida obs);

    public void notificarObservadores();
}
