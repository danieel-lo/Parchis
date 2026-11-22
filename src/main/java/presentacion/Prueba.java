/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package presentacion;

import simulacion.SimuladorInicioPartida;

/**
 *
 * @author Jorge
 */
public class Prueba {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        PantallaPrincipal pantalla= new PantallaPrincipal();
        
       pantalla.setPartidaCreadaListener(partida->{
           SimuladorInicioPartida simulador=new SimuladorInicioPartida();
           simulador.iniciar(partida);
       });
        
        pantalla.setVisible(true);
    }
    
}
