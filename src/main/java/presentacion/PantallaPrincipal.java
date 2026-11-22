/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package presentacion;

import java.awt.Color;
import java.awt.Font;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;

/**
 *
 * @author Jorge
 */
public class PantallaPrincipal extends JFrame {

    private PartidaCreadaListener listener;

    private final Color AZUL_OSCURO = new Color(11, 41, 72);

    public JButton btnNuevaPartida;
    public JButton btnBuscarPartida;

    public PantallaPrincipal() {
        setTitle("Menú principal");
        setSize(700, 430);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        //Panel principal
        JPanel panelFondo = new JPanel(null);
        panelFondo.setBackground(Color.WHITE);
        setContentPane(panelFondo);

        //Titulo
        JLabel lblTitulo = new JLabel("PARCHIS", SwingConstants.CENTER);
        lblTitulo.setBounds(200, 95, 300, 60);
        lblTitulo.setFont(new Font("SansSerif", Font.PLAIN, 34));
        lblTitulo.setForeground(Color.BLACK);
        panelFondo.add(lblTitulo);

        //Boton Nueva partida
        btnNuevaPartida = new JButton("Nueva Partida");
        btnNuevaPartida.setBounds(285, 185, 130, 35);
        configurarBoton(btnNuevaPartida);
        panelFondo.add(btnNuevaPartida);

        //Boton Buscar partida
        btnBuscarPartida = new JButton("Buscar Partida");
        btnBuscarPartida.setBounds(285, 235, 130, 35);
        configurarBoton(btnBuscarPartida);
        panelFondo.add(btnBuscarPartida);

        btnNuevaPartida.addActionListener(e -> {
            PantallaConfigurarPartida P2 = new PantallaConfigurarPartida();
            P2.setPartidaCreadaListener(listener);
            P2.setVisible(true);
            dispose();
        });
    }

    private void configurarBoton(JButton boton) {
        boton.setBackground(AZUL_OSCURO);
        boton.setForeground(Color.WHITE);
        boton.setFont(new Font("SansSerif", Font.PLAIN, 12));
        boton.setFocusPainted(false);
        boton.setBorder(new LineBorder(AZUL_OSCURO, 1));
    }

    public void setPartidaCreadaListener(PartidaCreadaListener listener) {
        this.listener = listener;
    }

}
