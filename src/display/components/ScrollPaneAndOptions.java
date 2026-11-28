package display.components;

import display.behaviours.ScrollPaneBehaviour;
import display.dialogs.DialogSelector;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

public class ScrollPaneAndOptions extends JPanel {

    private JPanel panel;
    private JPanel filaSeleccionada;
    private String elementoSeleccionado;

    private Color BASE;
    private Color TEXTO;
    private Font GENERAL;

    private final ScrollPaneBehaviour behaviour;

    public ScrollPaneAndOptions(String titulo, ScrollPaneBehaviour behaviour,
                                Color BASE, Color TEXTO, Font GENERAL, Font BOTONES) {

        this.BASE = BASE;
        this.TEXTO = TEXTO;
        this.GENERAL = GENERAL;
        this.behaviour = behaviour;

        setLayout(new BorderLayout());
        setBackground(BASE);

        JPanel encabezado = new JPanel(new BorderLayout());
        encabezado.setBackground(BASE);

        JLabel tituloLabel = new JLabel(titulo);

        tituloLabel.setFont(GENERAL);
        tituloLabel.setForeground(TEXTO);

        JPanel botones = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 2));
        botones.setBackground(BASE);

        JButton agregar = new JButton("+");
        JButton eliminar = new JButton("-");

        agregar.setFont(BOTONES);
        agregar.setForeground(TEXTO);
        agregar.setBackground(BASE);

        eliminar.setFont(BOTONES);
        eliminar.setForeground(TEXTO);
        eliminar.setBackground(BASE);

        agregar.addActionListener(e -> {

            Window ventana = SwingUtilities.getWindowAncestor(this);

            DialogSelector selector = new DialogSelector(
                    ventana,
                    titulo,
                    this.behaviour,
                    BASE,
                    TEXTO,
                    GENERAL,
                    BOTONES
            );

            selector.setVisible(true);

            actualizarElementos();
        });

        eliminar.addActionListener(e -> {

            if (hayElementoSeleccionado()) {

                this.behaviour.eliminarDeReceta(
                        elementoSeleccionado
                );

                actualizarElementos();
            }
        });

        botones.add(agregar);
        botones.add(eliminar);

        encabezado.add(tituloLabel, BorderLayout.CENTER);
        encabezado.add(botones, BorderLayout.EAST);

        encabezado.setBorder(
                BorderFactory.createMatteBorder(0, 0, 1, 0, TEXTO)
        );

        add(encabezado, BorderLayout.NORTH);

        panel = new JPanel();

        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(BASE);

        JScrollPane scrollPane = new JScrollPane(panel);

        scrollPane.setHorizontalScrollBarPolicy(
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER
        );

        scrollPane.setBorder(null);

        add(scrollPane, BorderLayout.CENTER);

        actualizarElementos();
    }

    public String getElementoSeleccionado() {
        return elementoSeleccionado;
    }

    public boolean hayElementoSeleccionado() {
        return elementoSeleccionado != null;
    }

    private void actualizarElementos() {

        List<String> elements = behaviour.getElementosReceta();

        panel.removeAll();

        filaSeleccionada = null;
        elementoSeleccionado = null;

        for (String element : elements) {

            JPanel fila = new JPanel(new BorderLayout());

            fila.setBackground(BASE);

            JComponent contenido = behaviour.crearFila(element);

            MouseAdapter seleccion = new MouseAdapter() {

                @Override
                public void mouseClicked(MouseEvent e) {

                    if (SwingUtilities.isLeftMouseButton(e)) {

                        if (filaSeleccionada != null) {
                            filaSeleccionada.setBackground(BASE);
                        }

                        filaSeleccionada = fila;
                        elementoSeleccionado = element;

                        filaSeleccionada.setBackground(BASE.darker());
                    }
                }
            };

            fila.addMouseListener(seleccion);

            configurarComponente(contenido, seleccion);

            fila.add(contenido, BorderLayout.CENTER);

            fila.setPreferredSize(new Dimension(200, 35));
            fila.setMaximumSize(new Dimension(Integer.MAX_VALUE, 35));

            fila.setAlignmentX(Component.LEFT_ALIGNMENT);

            fila.setBorder(
                    BorderFactory.createMatteBorder(0, 0, 1, 0, TEXTO)
            );

            panel.add(fila);
        }

        panel.revalidate();
        panel.repaint();
    }

    private void configurarComponente(Component componente,
                                      MouseAdapter seleccion) {

        componente.setForeground(TEXTO);
        componente.setFont(GENERAL);

        componente.addMouseListener(seleccion);

        if (componente instanceof JComponent) {
            ((JComponent) componente).setOpaque(false);
        }

        if (componente instanceof Container) {

            for (Component hijo : ((Container) componente).getComponents()) {

                configurarComponente(hijo, seleccion);
            }
        }
    }
}
