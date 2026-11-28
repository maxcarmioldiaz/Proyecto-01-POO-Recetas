package display.dialogs;

import javax.swing.*;
import java.awt.*;

public abstract class DialogCrear extends JDialog {

    private String nombreCreado;

    public DialogCrear(Color azul, Color beige, Color rojo, Font font, String titulo, String textoAyuda, Window propietario) {
        super(propietario, titulo, ModalityType.APPLICATION_MODAL);

        setSize(380, 420);
        setLocationRelativeTo(propietario);
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        setResizable(false);

        JPanel contenido = new JPanel();
        contenido.setLayout(new BoxLayout(contenido, BoxLayout.Y_AXIS));
        contenido.setBackground(azul);
        contenido.setBorder(BorderFactory.createEmptyBorder(10, 10, 30, 10));

        JButton botonVolver = new JButton("\u2190");
        JButton botonAgregar = new JButton("Agregar");

        for (JButton boton : new JButton[]{botonVolver, botonAgregar}) {
            boton.setFont(font);
            boton.setBackground(beige);
            boton.setForeground(rojo);
            boton.setFocusPainted(false);
        }

        botonAgregar.setMaximumSize(new Dimension(230, 55));

        JPanel contenedorVolver = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        contenedorVolver.setOpaque(false);
        contenedorVolver.setMaximumSize(new Dimension(Integer.MAX_VALUE, 50));
        contenedorVolver.add(botonVolver);

        JPanel imagen = new JPanel();
        imagen.setBackground(Color.WHITE);
        imagen.setMaximumSize(new Dimension(120, 100));

        JTextField campoNombre = new JTextField() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                if (getText().isEmpty()) {
                    g.setColor(Color.GRAY);
                    g.setFont(getFont());
                    FontMetrics medidas = g.getFontMetrics();
                    int x = (getWidth() - medidas.stringWidth(textoAyuda)) / 2;
                    int y = (getHeight() - medidas.getHeight()) / 2 + medidas.getAscent();
                    g.drawString(textoAyuda, x, y);
                }
            }
        };

        campoNombre.setFont(font);
        campoNombre.setForeground(beige);
        campoNombre.setCaretColor(beige);
        campoNombre.setOpaque(false);
        campoNombre.setHorizontalAlignment(JTextField.CENTER);
        campoNombre.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, beige));
        campoNombre.setMaximumSize(new Dimension(320, 45));

        botonVolver.addActionListener(e -> dispose());

        botonAgregar.addActionListener(e -> {
            String nombre = campoNombre.getText().trim();
            try {
                crear(nombre);
                nombreCreado = nombre;
                dispose();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        for (JComponent componente : new JComponent[]{contenedorVolver, imagen, campoNombre, botonAgregar}) {
            componente.setAlignmentX(Component.CENTER_ALIGNMENT);
        }

        contenido.add(contenedorVolver);
        contenido.add(imagen);
        contenido.add(Box.createVerticalStrut(40));
        contenido.add(campoNombre);
        contenido.add(Box.createVerticalStrut(35));
        contenido.add(botonAgregar);

        setContentPane(contenido);
        getRootPane().setDefaultButton(botonAgregar);
    }

    protected abstract void crear(String nombre) throws Exception;

    public String getNombreCreado() {
        return nombreCreado;
    }
}