/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package presentacion.fabricas;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JToggleButton;
import javax.swing.border.LineBorder;

/**
 *
 * @author Usuario
 */
public class FabricaComponentesUI {
      // Colores de la interfaz
    private final Color AZUL_OSCURO = new Color(11, 41, 72);
    private final Color FONDO_TARJETA = new Color(242, 246, 252);
    private final Color GRIS_BOTON = new Color(224, 232, 241);
    private final Color BORDE_SUAVE = new Color(195, 208, 224);

    // Crear panel de fondo
    public JPanel crearPanelFondo() {
        JPanel panel = new JPanel(null);
        panel.setBackground(Color.WHITE);
        return panel;
    }

    // Crear panel de título o subtítulo
    public JPanel crearPanelEncabezado(String texto, int x, int y,int ancho, int alto, int tamanoFuente) {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(AZUL_OSCURO);
        panel.setBounds(x, y, ancho, alto);
        JLabel etiqueta = new JLabel(texto);
        etiqueta.setFont(new Font("SansSerif", Font.BOLD, tamanoFuente));
        etiqueta.setForeground(Color.WHITE);
        panel.add(etiqueta);
        return panel;
    }

    // Crear tarjeta principal
    public JPanel crearTarjeta() {
        JPanel tarjeta = new JPanel(null);
        tarjeta.setBackground(FONDO_TARJETA);
        tarjeta.setBorder(new LineBorder(AZUL_OSCURO, 2));
        tarjeta.setBounds(65, 115, 630, 340);
        return tarjeta;
    }

    // Crear fila para las opciones de configuración
    public JPanel crearFila(int y) {
        JPanel fila = new JPanel(null);
        fila.setBackground(Color.WHITE);
        fila.setBorder(new LineBorder(BORDE_SUAVE, 1));
        fila.setBounds(25, y, 580, 60);
        return fila;
    }

    // Crear etiqueta
    public JLabel crearEtiqueta(String texto) {
        JLabel lbl = new JLabel(texto);
        lbl.setFont(new Font("SansSerif", Font.BOLD, 16));
        lbl.setForeground(AZUL_OSCURO);
        lbl.setBounds(20, 0, 340, 60);
        return lbl;
    }

    // Crear botón para seleccionar número de fichas
    public JToggleButton crearBotonNumero(String texto, int x, boolean seleccionado) {
        JToggleButton btn = new JToggleButton(texto, seleccionado);
        btn.setBounds(x, 12, 42, 36);
        btn.setFont(new Font("SansSerif", Font.BOLD, 15));
        btn.setBorder(new LineBorder(AZUL_OSCURO, 1));
        btn.setFocusPainted(false);
        aplicarEstiloBotonNumero(btn);
        return btn;
    }

    // Aplicar estilo según la selección del botón
    public void aplicarEstiloBotonNumero(JToggleButton btn) {
        if (btn.isSelected()) {
            btn.setBackground(AZUL_OSCURO);
            btn.setForeground(Color.WHITE);
        } else {
            btn.setBackground(Color.WHITE);
            btn.setForeground(AZUL_OSCURO);
        }
    }

    // Crear lista desplegable
    public JComboBox<Integer> crearCombo(Integer[] valores, int seleccion) {
        JComboBox<Integer> combo = new JComboBox<>(valores);
        combo.setSelectedItem(seleccion);
        combo.setFont(new Font("SansSerif", Font.BOLD, 15));
        combo.setBackground(Color.WHITE);
        combo.setForeground(AZUL_OSCURO);
        combo.setBorder(new LineBorder(AZUL_OSCURO, 1));
        combo.setFocusable(false);
        return combo;
    }

    // Crear botones inferiores
    public JButton crearBoton(String texto, int x, int y, int ancho, int alto, boolean primario) {
        JButton btn = new JButton(texto);
        btn.setBounds(x, y, ancho, alto);
        btn.setFont(new Font("SansSerif", Font.BOLD, 16));
        btn.setBackground(primario ? AZUL_OSCURO : GRIS_BOTON);
        btn.setForeground(primario ? Color.WHITE : AZUL_OSCURO);
        btn.setBorder(new LineBorder(AZUL_OSCURO, 1));
        btn.setFocusPainted(false);
        return btn;
    }
    
    
}
