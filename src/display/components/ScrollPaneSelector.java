package display.components;

import display.behaviours.ScrollPaneBehaviour;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

public class ScrollPaneSelector extends JPanel {

    private JPanel panel;

    private Color BASE;
    private Color TEXTO;
    private Font GENERAL;

    private final ScrollPaneBehaviour behaviour;

    public ScrollPaneSelector(String titulo, ScrollPaneBehaviour behaviour,
                              Color BASE, Color TEXTO, Font GENERAL, Font BOTONES) {

        this.BASE = BASE;
        this.TEXTO = TEXTO;
        this.GENERAL = GENERAL;
        this.behaviour = behaviour;

        setLayout(new BorderLayout());
        setBackground(BASE);

        panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(BASE);

        JScrollPane scrollPane = new JScrollPane(panel);

        scrollPane.setHorizontalScrollBarPolicy(
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER
        );

        JLabel tituloLabel = new JLabel(titulo, SwingConstants.CENTER);

        tituloLabel.setBackground(BASE);
        tituloLabel.setFont(GENERAL);
        tituloLabel.setForeground(TEXTO);
        tituloLabel.setOpaque(true);

        add(tituloLabel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);

        JPanel botones = new JPanel(new FlowLayout());
        botones.setBackground(BASE);

        JButton agregar = new JButton("Agregar");

        agregar.setForeground(TEXTO);
        agregar.setBackground(BASE);
        agregar.setFont(BOTONES);

        agregar.addActionListener(e -> {

            Window ventana = SwingUtilities.getWindowAncestor(this);

            this.behaviour.agregar(ventana);

            actualizarElementos();
        });

        botones.add(agregar);

        add(botones, BorderLayout.SOUTH);

        actualizarElementos();
    }

    private void actualizarElementos() {

        List<String> elements = behaviour.getElementos();

        panel.removeAll();

        for (String element : elements) {

            JPanel fila = new JPanel(new BorderLayout());
            JLabel nombre = new JLabel(element);

            fila.setBackground(BASE);

            nombre.setFont(GENERAL);
            nombre.setForeground(TEXTO);

            if (behaviour.tieneCheckBox()) {

                JCheckBox checkBox = new JCheckBox();

                checkBox.setSelected(
                        behaviour.isDisponible(element)
                );

                checkBox.setOpaque(false);

                checkBox.addActionListener(e -> {

                    behaviour.cambiarDisponibilidad(
                            element, checkBox.isSelected()
                    );
                });

                fila.add(checkBox, BorderLayout.EAST);
            }

            MouseAdapter seleccion = new MouseAdapter() {

                @Override
                public void mouseClicked(MouseEvent e) {

                    if (SwingUtilities.isLeftMouseButton(e)) {

                        behaviour.agregarAReceta(element);

                        Window ventana = SwingUtilities.getWindowAncestor(
                                ScrollPaneSelector.this
                        );

                        if (ventana instanceof JDialog) {
                            ventana.dispose();
                        }
                    }
                }
            };

            fila.addMouseListener(seleccion);
            nombre.addMouseListener(seleccion);

            fila.setCursor(
                    Cursor.getPredefinedCursor(Cursor.HAND_CURSOR)
            );

            fila.setPreferredSize(new Dimension(200, 45));
            fila.setMaximumSize(new Dimension(Integer.MAX_VALUE, 45));

            fila.add(nombre, BorderLayout.CENTER);

            fila.setAlignmentX(Component.LEFT_ALIGNMENT);

            fila.setBorder(
                    BorderFactory.createMatteBorder(0, 0, 1, 0, TEXTO)
            );

            panel.add(fila);
        }

        panel.revalidate();
        panel.repaint();
    }
}

/*
Para este caso se me ocurre hacer algo similar si no es que igual al
checkboxscrollpane con la diferencia de que este no usa checkboxes
si no que solo la lista de elementos y se pueden seleccionar para hacer cambios.
*/