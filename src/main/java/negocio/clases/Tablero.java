/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package negocio.clases;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import negocio.Enum.Color;

/**
 *
 * @author Jorge
 */
public class Tablero {

    private List<Casilla> casillas;
    private int valorSalidaConfigurado;
    private int casillasSegurasPorAla;
    private Map<Color, List<Casilla>> pasillosFinales;

    public Tablero(int valorSalidaConfigurado, int casillasSegurasPorAla) {
        //primero inicializamos los atributos y las listas
        this.casillas = new ArrayList<>();
        this.pasillosFinales = new EnumMap<>(Color.class);
        //validamos el numero de salida configurado 
        if(valorSalidaConfigurado < 1 || valorSalidaConfigurado > 6){
            throw new IllegalArgumentException("El valor de la salida debe de estar entre 1 y 6");
        }
        this.valorSalidaConfigurado = valorSalidaConfigurado;
        this.casillasSegurasPorAla = casillasSegurasPorAla;
        //validamos el numero de casillas seguras por ala, el min es de 1 y max es de 5
        if (casillasSegurasPorAla < 1 || casillasSegurasPorAla > 5) {
            //si hay error lanza esta IllegalArgumentException
            throw new IllegalArgumentException("Debe haber entre 1 y 5 casillas seguras por ala");
        }
        //seteamos las posibles posiciones seguras de cada ala
        int[] posicionesSeguras = {5, 12, 17, 8, 14};
        //este for crea las 68 casillas del tablero
        for (int i = 1; i <= 68; i++) {
            //el color default por decirlo asi es null
            Color colorSalida = null;
            //si i coincide con uno de los case esa casilla se convierte en la casilla de salida de ese color
            switch (i) {
                case 5:
                    colorSalida = Color.AMARILLO;
                    break;
                case 22:
                    colorSalida = Color.AZUL;
                    break;
                case 39:
                    colorSalida = Color.ROJO;
                    break;
                case 56:
                    colorSalida = Color.VERDE;
                    break;
            }
            //comprobamos si la casilla que estamos creando es una casilla de salida
            boolean esSalida = colorSalida != null;
            //identificamos que posicion ocupa una casilla dentro de su ala del tablero
            int posicionEnAla = ((i - 1) % 17) + 1;
            //por default la casilla no es segura
            boolean esSeguro = false;
            //recorremos las posiciones seguras dependiendo de cuantas se configuraron por ala
            for (int j = 0; j < casillasSegurasPorAla; j++) {
                //si la posicion coincide con una segura cambiamos esSeguro a true
                if (posicionEnAla == posicionesSeguras[j]) {
                    esSeguro = true;
                    break;
                }
            }
            //creamos la casilla con sus datos y la agregamos a la lista
            casillas.add(new Casilla(i, esSeguro, esSalida, false, colorSalida));
        }
        //las casillas de los pasillos finales empiezan despues de las 68 casillas normales
        int numeroCasillaFinal = 69;
        //recorremos los colores para crear un pasillo final para cada uno
        for (Color color : Color.values()) {
            //creamos la lista donde se guardaran las casillas del pasillo de ese color
            List<Casilla> pasillo = new ArrayList<>();
            //creamos las 7 casillas del pasillo final y las agregamos a la lista
            for (int i = 1; i <= 7; i++) {
                pasillo.add(new Casilla(numeroCasillaFinal++, false, false, false, color));
            }
            //agregamos la casilla de meta y la marcamos como true
            pasillo.add(new Casilla(numeroCasillaFinal++, false, false, true, color));
            //guardamos el pasillo final en el mapa con su color correspondiente
            pasillosFinales.put(color, pasillo);
        }
    }

    public List<Casilla> getCasillas() {
        return casillas;
    }

    public int getValorSalidaConfigurado() {
        return valorSalidaConfigurado;
    }

    public int getCasillasSegurasPorAla() {
        return casillasSegurasPorAla;
    }

    public List<Casilla> consultarCasillas(int posOrigen, int pasos, Color color) {
        //creamos la lista donde guardaremos las casillas del recorrido
        List<Casilla> recorrido = new ArrayList<>();
        //validamos que el color no sea nulo y que los pasos sean mayores a 0
        if (color == null || pasos <= 0) {
            return recorrido;
        }
        //comprobamos si la ficha esta en su base
        if (posOrigen == -1) {
            //si el dado no tiene el valor configurado para salir regresamos la lista vacia
            if (pasos != valorSalidaConfigurado) {
                return recorrido;
            }
            //buscamos la casilla de salida que corresponde al color de la ficha
            for (Casilla casilla : casillas) {
                if (casilla.isEsSalida() && casilla.getColor() == color) {
                    //agregamos la casilla de salida al recorrido
                    recorrido.add(casilla);
                    break;
                }
            }
            return recorrido;
        }
        //guardamos la posicion actual de la ficha
        int posicion = posOrigen;
        int entradaPasillo = 0;
        //identificamos la ultima casilla comun antes del pasillo final de cada color
        switch (color) {
            case AMARILLO:
                entradaPasillo = 4;
                break;
            case AZUL:
                entradaPasillo = 21;
                break;
            case ROJO:
                entradaPasillo = 38;
                break;
            case VERDE:
                entradaPasillo = 55;
                break;
        }
        //obtenemos el pasillo final que corresponde al color de la ficha
        List<Casilla> pasillo = pasillosFinales.get(color);
        //comprobamos si la ficha ya se encuentra dentro de su pasillo final
        boolean estaEnPasillo = pasillo.stream().anyMatch(p -> p.getNumero() == posOrigen);
        //validamos que la posicion exista en el tablero o en el pasillo final
        if ((posOrigen < 1 || posOrigen > 68) && !estaEnPasillo) {
            return recorrido;
        }
        //inicializamos el indice en -1 porque aun no sabemos su posicion en el pasillo
        int indicePasillo = -1;
        //si la ficha esta en el pasillo buscamos su posicion dentro de la lista
        if (estaEnPasillo) {
            for (int j = 0; j < pasillo.size(); j++) {
                if (pasillo.get(j).getNumero() == posOrigen) {
                    indicePasillo = j;
                    break;
                }
            }
        }
        //si la ficha ya llego a meta no permitimos que siga avanzando
        if (estaEnPasillo && pasillo.get(indicePasillo).isEsMeta()) {
            return recorrido;
        }
        //la direccion empieza en 1 porque la ficha avanza hacia la meta
        int direccionPasillo = 1;
        //recorremos las casillas dependiendo de los pasos del dado
        for (int i = 0; i < pasos; i++) {
            //si la ficha esta en el pasillo final calculamos su siguiente posicion
            if (estaEnPasillo) {
                //si intenta salir de los limites del pasillo cambiamos la direccion para hacer el rebote
                if (indicePasillo + direccionPasillo >= pasillo.size() || indicePasillo + direccionPasillo < 0) {
                    direccionPasillo *= -1;
                }
                //actualizamos el indice y agregamos la casilla recorrida
                indicePasillo += direccionPasillo;
                recorrido.add(pasillo.get(indicePasillo));
                //si llegamos a la entrada del pasillo comenzamos a recorrerlo
            } else if (posicion == entradaPasillo) {
                estaEnPasillo = true;
                indicePasillo = 0;
                recorrido.add(pasillo.get(indicePasillo));
            } else {
                //si la ficha sigue en el recorrido comun avanzamos una casilla
                posicion++;
                //si superamos la casilla 68 continuamos desde la casilla 1
                if (posicion > 68) {
                    posicion = 1;
                }
                //agregamos la casilla recorrida a la lista
                recorrido.add(casillas.get(posicion - 1));
            }
        }
        //regresamos la lista con todas las casillas que recorrio la ficha
        return recorrido;
    }

    public Casilla calcularCasillaDestino(int posOrigen, int pasos, Color color) {
        List<Casilla> recorrido = consultarCasillas(posOrigen, pasos, color);
        if (recorrido.isEmpty()) {
            return null;
        }
        return recorrido.get(recorrido.size() - 1);
    }

    public boolean hayBarreraEnTrayecto(int posOrigen, int pasos, Color color) {
        List<Casilla> recorrido = consultarCasillas(posOrigen, pasos, color);
        return recorrido.stream().anyMatch(p -> p.tieneBarreras());
    }

    public boolean esBarreraPropia(int posicion, Color color) {
        if (color == null) {
            return false;
        }
        Casilla casilla = null;
        if (posicion >= 1 && posicion <= 68) {
            casilla = casillas.get(posicion - 1);
        } else {
            for (Casilla p : pasillosFinales.get(color)) {
                if (p.getNumero() == posicion) {
                    casilla = p;
                    break;
                }
            }
        }
        if (casilla == null) {
            return false;
        }
        return casilla.getFichasPresentes().size() == 2 && casilla.getFichasPresentes().stream().allMatch(p -> p.getColor() == color);
    }

    public Casilla obtenerCasilla(int numero) {
        if (numero >= 1 && numero <= 68) {
            return casillas.get(numero - 1);
        }
        for (List<Casilla> pasillo : pasillosFinales.values()) {
            Casilla encontrada= pasillo.stream().filter(p->p.getNumero()==numero).findFirst().orElse(null);
            if(encontrada!=null){
                return encontrada;
            }
        }
        return null;
    }

}
