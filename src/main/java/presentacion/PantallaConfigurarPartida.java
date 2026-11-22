/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package presentacion;

import control.ControlConfigurarPartida;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JToggleButton;
import javax.swing.border.LineBorder;
import negocio.clases.Partida;
import negocio.interfaces.IPartida;
import negocio.interfaces.ObservadorPartida;

/**
 *
 * @author Jorge
 */
public class PantallaConfigurarPartida extends JFrame implements ObservadorPartida {

    private ControlConfigurarPartida controlador;
    private PartidaCreadaListener listener;

    private final Color AZUL_OSCURO = new Color(11, 41, 72);
    private final Color FONDO_TARJETA = new Color(242, 246, 252);
    private final Color GRIS_BOTON = new Color(224, 232, 241);
    private final Color BORDE_SUAVE = new Color(195, 208, 224);

    public JToggleButton btn3, btn4, btn5, btn6;
    public JComboBox<Integer> comboCasillas;
    public JComboBox<Integer> comboDado;
    public JButton btnVolver;
    public JButton btnCrear;

    public PantallaConfigurarPartida() {
        controlador = new ControlConfigurarPartida();
        setTitle("Configurar partida");
        setSize(760, 520);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        JPanel panelFondo = new JPanel(null);
        panelFondo.setBackground(Color.WHITE);
        setContentPane(panelFondo);

        //Titulo superior
        JPanel panelTitulo = new JPanel(new GridBagLayout());
        panelTitulo.setBackground(AZUL_OSCURO);
        panelTitulo.setBounds(180, 20, 400, 60);
        JLabel lblTitulo = new JLabel("Parchis");
        lblTitulo.setFont(new Font("SansSerif", Font.BOLD, 34));
        lblTitulo.setForeground(Color.WHITE);
        panelTitulo.add(lblTitulo);
        panelFondo.add(panelTitulo);

        //Subtítulo
        JPanel panelSubtitulo = new JPanel(new GridBagLayout());
        panelSubtitulo.setBackground(AZUL_OSCURO);
        panelSubtitulo.setBounds(230, 95, 300, 38);
        JLabel lblSubtitulo = new JLabel("Configurar partida");
        lblSubtitulo.setFont(new Font("SansSerif", Font.BOLD, 18));
        lblSubtitulo.setForeground(Color.WHITE);
        panelSubtitulo.add(lblSubtitulo);
        panelFondo.add(panelSubtitulo);

        //Contenedor principal
        JPanel tarjeta = new JPanel(null);
        tarjeta.setBackground(FONDO_TARJETA);
        tarjeta.setBorder(new LineBorder(AZUL_OSCURO, 2));
        tarjeta.setBounds(65, 115, 630, 340);
        panelFondo.add(tarjeta);
        panelFondo.setComponentZOrder(panelSubtitulo, 0);

        //Fichas por jugador
        JPanel fila1 = crearFila(40);
        fila1.add(crearEtiqueta("Número de fichas por jugador:"));

        btn3 = crearBotonNumero("3", 360, false);
        btn4 = crearBotonNumero("4", 410, false);
        btn5 = crearBotonNumero("5", 460, false);
        btn6 = crearBotonNumero("6", 510, false);

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

        //Casillas seguras
        JPanel fila2 = crearFila(115);
        fila2.add(crearEtiqueta("Número de casillas seguras:"));
        comboCasillas = crearCombo(new Integer[]{1, 2, 3, 4, 5}, 1);
        comboCasillas.setBounds(415, 12, 145, 36);
        fila2.add(comboCasillas);
        tarjeta.add(fila2);

        //Dado para sacar ficha
        JPanel fila3 = crearFila(190);
        fila3.add(crearEtiqueta("Número del dado para sacar ficha:"));
        comboDado = crearCombo(new Integer[]{1, 2, 3, 4, 5, 6}, 1);
        comboDado.setBounds(415, 12, 145, 36);
        fila3.add(comboDado);
        tarjeta.add(fila3);

        //Botones inferiores
        btnVolver = new JButton("Volver");
        btnVolver.setBounds(115, 275, 175, 42);
        btnVolver.setFont(new Font("SansSerif", Font.BOLD, 16));
        btnVolver.setBackground(GRIS_BOTON);
        btnVolver.setForeground(AZUL_OSCURO);
        btnVolver.setBorder(new LineBorder(AZUL_OSCURO, 1));
        btnVolver.setFocusPainted(false);
        tarjeta.add(btnVolver);

        btnCrear = new JButton("Crear partida");
        btnCrear.setBounds(320, 275, 205, 42);
        btnCrear.setFont(new Font("SansSerif", Font.BOLD, 16));
        btnCrear.setBackground(AZUL_OSCURO);
        btnCrear.setForeground(Color.WHITE);
        btnCrear.setBorder(new LineBorder(AZUL_OSCURO, 1));
        btnCrear.setFocusPainted(false);
        tarjeta.add(btnCrear);

        btnCrear.addActionListener(e -> {
            int numeroFichas = obtenerNumeroSeleccionado();
            if (numeroFichas == -1) {
                JOptionPane.showMessageDialog(this, "Debes Seleccionar el numero de fichas por jugador",
                        "Configuracion invalida",
                        JOptionPane.WARNING_MESSAGE);
                return;
            }
            try {
                Partida partida=controlador.solicitarCrearPartida(numeroFichas, obtenerCasillasSeguras(), obtenerValorSalida(), this);
                partida.removerObservador(this);
                if(listener != null){
                    listener.partidaCreada(partida);
                }
                dispose();
            } catch (IllegalArgumentException   ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(),
                        "Configuracion invalida",
                        JOptionPane.ERROR_MESSAGE);
            }
        });
        
        btnVolver.addActionListener(e->{
            PantallaPrincipal menu =new PantallaPrincipal();
            menu.setPartidaCreadaListener(listener);
            menu.setVisible(true);
            dispose();
        });

    }

    private JPanel crearFila(int y) {
        JPanel fila = new JPanel(null);
        fila.setBackground(Color.WHITE);
        fila.setBorder(new LineBorder(BORDE_SUAVE, 1));
        fila.setBounds(25, y, 580, 60);
        return fila;
    }

    private JLabel crearEtiqueta(String texto) {
        JLabel lbl = new JLabel(texto);
        lbl.setFont(new Font("SansSerif", Font.BOLD, 16));
        lbl.setForeground(AZUL_OSCURO);
        lbl.setBounds(20, 0, 340, 60);
        return lbl;
    }

    private JToggleButton crearBotonNumero(String texto, int x, boolean seleccionado) {
        JToggleButton btn = new JToggleButton(texto, seleccionado);
        btn.setBounds(x, 12, 42, 36);
        btn.setFont(new Font("SansSerif", Font.BOLD, 15));
        btn.setBackground(seleccionado ? AZUL_OSCURO : Color.WHITE);
        btn.setForeground(seleccionado ? Color.WHITE : AZUL_OSCURO);
        btn.setBorder(new LineBorder(AZUL_OSCURO, 1));
        btn.setFocusPainted(false);
        return btn;
    }

    private JComboBox<Integer> crearCombo(Integer[] valores, int seleccion) {
        JComboBox<Integer> combo = new JComboBox<>(valores);
        combo.setSelectedItem(seleccion);
        combo.setFont(new Font("SansSerif", Font.BOLD, 15));
        combo.setBackground(Color.WHITE);
        combo.setForeground(AZUL_OSCURO);
        combo.setBorder(new LineBorder(AZUL_OSCURO, 1));
        combo.setFocusable(false);
        return combo;
    }

    private void actualizarEstiloFichas() {
        JToggleButton[] botones = {btn3, btn4, btn5, btn6};

        for (JToggleButton boton : botones) {
            if (boton.isSelected()) {
                boton.setBackground(AZUL_OSCURO);
                boton.setForeground(Color.WHITE);
            } else {
                boton.setBackground(Color.WHITE);
                boton.setForeground(AZUL_OSCURO);
            }
        }
    }

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

    private int obtenerCasillasSeguras() {
        return (int) comboCasillas.getSelectedItem();
    }

    private int obtenerValorSalida() {
        return (int) comboDado.getSelectedItem();
    }

    @Override
    public void actualizar(IPartida iPartida) {
        JOptionPane.showMessageDialog(
                this,
                "La partida se ha creado correctamente",
                "Partida creada",
                JOptionPane.INFORMATION_MESSAGE
        );
    }
    
    public void setPartidaCreadaListener(PartidaCreadaListener listener){
        this.listener=listener;
    }

}
