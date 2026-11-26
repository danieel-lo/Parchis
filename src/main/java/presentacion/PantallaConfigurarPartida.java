/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package presentacion;

import control.ControlConfigurarPartida;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JToggleButton;
import negocio.interfaces.IPartida;
import negocio.interfaces.ObservadorPartida;
import presentacion.fabricas.FabricaComponentesUI;

/**
 *
 * @author Jorge
 */
public class PantallaConfigurarPartida extends JFrame implements ObservadorPartida {

    private ControlConfigurarPartida controlador;
    private PartidaCreadaListener listener;

    // Fábrica de componentes gráficos
    private final FabricaComponentesUI fabricaUI = new FabricaComponentesUI();

    public JToggleButton btn3, btn4, btn5, btn6;
    public JComboBox<Integer> comboCasillas;
    public JComboBox<Integer> comboDado;
    public JButton btnVolver;
    public JButton btnCrear;

    public PantallaConfigurarPartida(ControlConfigurarPartida controlador) {
        this.controlador = controlador;

        setTitle("Configurar partida");
        setSize(760, 520);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        //panel de fondo
        JPanel panelFondo = fabricaUI.crearPanelFondo();
        setContentPane(panelFondo);

        //titulo superior
        JPanel panelTitulo = fabricaUI.crearPanelEncabezado("Parchis", 180, 20, 400, 60, 34);
        panelFondo.add(panelTitulo);
        
        //subtitulo
        JPanel panelSubtitulo = fabricaUI.crearPanelEncabezado("Configurar partida", 230, 95, 300, 38, 18);
        panelFondo.add(panelSubtitulo);
        
        //contenedor principal
        JPanel tarjeta = fabricaUI.crearTarjeta();
        panelFondo.add(tarjeta);
        panelFondo.setComponentZOrder(panelSubtitulo, 0);
        
        JPanel fila1 = fabricaUI.crearFila(40);
        fila1.add(fabricaUI.crearEtiqueta("Número de fichas por jugador:"));
        btn3 = fabricaUI.crearBotonNumero("3", 360, false);
        btn4 = fabricaUI.crearBotonNumero("4", 410, false);
        btn5 = fabricaUI.crearBotonNumero("5", 460, false);
        btn6 = fabricaUI.crearBotonNumero("6", 510, false);
        ButtonGroup grupo = new ButtonGroup();
        grupo.add(btn3);
        grupo.add(btn4);
        grupo.add(btn5);
        grupo.add(btn6);
        btn3.addActionListener(e -> actualizarEstiloFichas());
        btn4.addActionListener(e -> actualizarEstiloFichas());
        btn5.addActionListener(e -> actualizarEstiloFichas());
        btn6.addActionListener(e -> actualizarEstiloFichas());
        fila1.add(btn3);
        fila1.add(btn4);
        fila1.add(btn5);
        fila1.add(btn6);
        tarjeta.add(fila1);
        JPanel fila2 = fabricaUI.crearFila(115);
        fila2.add(fabricaUI.crearEtiqueta("Número de casillas seguras:"));
        comboCasillas = fabricaUI.crearCombo(new Integer[]{1, 2, 3, 4, 5}, 1);
        comboCasillas.setBounds(415, 12, 145, 36);
        fila2.add(comboCasillas);
        tarjeta.add(fila2);
        JPanel fila3 = fabricaUI.crearFila(190);
        fila3.add(fabricaUI.crearEtiqueta("Número del dado para sacar ficha:"));
        comboDado = fabricaUI.crearCombo(new Integer[]{1, 2, 3, 4, 5, 6}, 5);
        comboDado.setBounds(415, 12, 145, 36);
        fila3.add(comboDado);
        tarjeta.add(fila3);
        
        btnVolver = fabricaUI.crearBoton("Volver", 115, 275, 175, 42, false);
        tarjeta.add(btnVolver);
        
        btnCrear = fabricaUI.crearBoton("Crear partida", 320, 275, 205, 42, true);
        tarjeta.add(btnCrear);

        //evento para crear la partida
        btnCrear.addActionListener(e -> {
            int numeroFichas = obtenerNumeroSeleccionado();
            if (numeroFichas == -1) {
                JOptionPane.showMessageDialog(
                        this,
                        "Debes Seleccionar el numero de fichas por jugador",
                        "Configuracion invalida",
                        JOptionPane.WARNING_MESSAGE
                );
                return;
            }
            try {
                IPartida partida = controlador.solicitarCrearPartida(numeroFichas, obtenerCasillasSeguras(), obtenerValorSalida());
                //suscribimos a la pantalla como observador
                partida.agregarObservador(this);
                //el dominio confirma y notifica el cambio
                controlador.confirmarConfiguracion(partida);
                //la pantalla deja de observar esta partida
                partida.removerObservador(this);
                //avisar al simulador que la partida fue creada
                if (listener != null) {
                    listener.partidaCreada(partida);
                }
                dispose();
            } catch (IllegalArgumentException ex) {
                JOptionPane.showMessageDialog(
                        this,
                        ex.getMessage(),
                        "Configuracion invalida",
                        JOptionPane.ERROR_MESSAGE
                );
            }
        });
        
        //evento para regresar al menu principal
        btnVolver.addActionListener(e -> {
            PantallaPrincipal menu = new PantallaPrincipal(controlador);
            menu.setPartidaCreadaListener(listener);
            menu.setVisible(true);
            dispose();
        });
    }

    //actualizar el estilo de los botones seleccionados
    private void actualizarEstiloFichas() {
        JToggleButton[] botones = {btn3, btn4, btn5, btn6};
        for (JToggleButton boton : botones) {
            fabricaUI.aplicarEstiloBotonNumero(boton);
        }
    }

    //obtener el numero de fichas seleccionado
    private int obtenerNumeroSeleccionado() {
        if (btn3.isSelected()) {
            return 3;
        }
        if (btn4.isSelected()) {
            return 4;
        }
        if (btn5.isSelected()) {
            return 5;
        }
        if (btn6.isSelected()) {
            return 6;
        }
        return -1;
    }

    //obtener numero de casillas seguras
    private int obtenerCasillasSeguras() {
        return (int) comboCasillas.getSelectedItem();
    }
    //obtener valor del dado para sacar la ficha
    private int obtenerValorSalida() {
        return (int) comboDado.getSelectedItem();
    }

    //metodo del patron Observer
    @Override
    public void actualizar(IPartida iPartida) {
        JOptionPane.showMessageDialog(
                this,
                "La partida se ha creado correctamente",
                "Partida creada",
                JOptionPane.INFORMATION_MESSAGE
        );
    }

    //conectar la pantalla con el simulador
    public void setPartidaCreadaListener(PartidaCreadaListener listener) {
        this.listener = listener;
    }

}
