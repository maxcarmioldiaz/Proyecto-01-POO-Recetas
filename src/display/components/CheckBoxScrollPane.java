package display.components;

import javax.swing.*;
import java.awt.*;
import display.behaviours.ElementoBehaviour;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

public class CheckBoxScrollPane extends JPanel {

    private JPanel panel;
    private JPanel filaSeleccionada;
    private String elementoSeleccionado;

    private Color BASE;
    private Color TEXTO;
    private Font GENERAL;

    private final ElementoBehaviour behaviour;

    public CheckBoxScrollPane(String titulo, ElementoBehaviour behaviour, Color BASE,
            Color TEXTO, Font GENERAL, Font BOTONES) {
        this.BASE = BASE;
        this.TEXTO = TEXTO;
        this.GENERAL = GENERAL;
        this.behaviour = behaviour;

        panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        actualizarElementos();

        JScrollPane scrollPane = new JScrollPane(panel);
        setLayout(new BorderLayout());

        JLabel tituloLabel = new JLabel(titulo, SwingConstants.CENTER);

        tituloLabel.setBackground(BASE);
        tituloLabel.setFont(GENERAL);
        tituloLabel.setForeground(TEXTO);

        add(scrollPane, BorderLayout.CENTER);
        add(tituloLabel, BorderLayout.NORTH);

        JPanel botones = new JPanel();
        botones.setBackground(BASE);

        botones.setLayout(new FlowLayout());

        JButton agregar = new JButton("Agregar");
        JButton eliminar = new JButton("Eliminar");
        JButton editar = new JButton("Editar");

        agregar.setForeground(TEXTO);
        agregar.setBackground(BASE);
        agregar.setFont(BOTONES);

        eliminar.setForeground(TEXTO);
        eliminar.setBackground(BASE);
        eliminar.setFont(BOTONES);

        editar.setForeground(TEXTO);
        editar.setBackground(BASE);
        editar.setFont(BOTONES);

        setBackground(BASE);
        panel.setBackground(BASE);

        tituloLabel.setOpaque(true);

        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        agregar.addActionListener(e -> {
            Window ventana = SwingUtilities.getWindowAncestor(this);
            this.behaviour.agregar(ventana);
            actualizarElementos();
        });

        eliminar.addActionListener(e -> {
            if (hayElementoSeleccionado()) {
                Window ventana = SwingUtilities.getWindowAncestor(this);
                this.behaviour.eliminar(elementoSeleccionado, ventana);
                actualizarElementos();
            }
        });

        editar.addActionListener(e -> {
            if (hayElementoSeleccionado()) {
                Window ventana = SwingUtilities.getWindowAncestor(this);
                this.behaviour.editar(elementoSeleccionado, ventana);
                actualizarElementos();
            }
        });

        botones.add(agregar);
        botones.add(eliminar);
        botones.add(editar);

        add(botones, BorderLayout.SOUTH);


    }

    public String getElementoSeleccionado() {
        return elementoSeleccionado;
    }

    public boolean hayElementoSeleccionado() {
        return elementoSeleccionado != null;
    }

    private void actualizarElementos() {
        List<String> elements = behaviour.getElementos();

        panel.removeAll();

        filaSeleccionada = null;
        elementoSeleccionado = null;

        for (String element : elements) {
            JPanel fila = new JPanel(new BorderLayout());
            JLabel nombre = new JLabel(element);
            JCheckBox checkBox = new JCheckBox();

            fila.setBackground(BASE);
            nombre.setFont(GENERAL);
            nombre.setForeground(TEXTO);

            //A este metodo le estoy pasando String element
            //y String criterio, con criterio va a definir si va a buscar
            //en los ingredientes o en los utensilios es decir esa comparacion debe
            //existir dentro del metodo isDisponible
            checkBox.setSelected(behaviour.isDisponible(element));

            checkBox.addActionListener(e -> {
                behaviour.cambiarDisponibilidad(
                        element, checkBox.isSelected()
                );
            });

            MouseAdapter seleccion = new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {

                    if (filaSeleccionada != null) {
                        filaSeleccionada.setBackground(BASE);
                    }

                    filaSeleccionada = fila;
                    elementoSeleccionado = element;

                    filaSeleccionada.setBackground(BASE.darker());
                }
            };

            fila.addMouseListener(seleccion);
            nombre.addMouseListener(seleccion);

            fila.setPreferredSize(new Dimension(200, 45));
            fila.setMaximumSize(new Dimension(Integer.MAX_VALUE, 45));

            fila.add(nombre, BorderLayout.CENTER);
            fila.add(checkBox, BorderLayout.EAST);
            fila.setAlignmentX(Component.LEFT_ALIGNMENT);

            checkBox.setOpaque(false);

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
Este checkboxscrollpane es un panel en el que se tiene el titulo del panel arriba
un panel con bastantes elementos estos tienen que tener un checkbox y el checkbox
al estar activo tiene que marcar como disponible al elemento, el reto que me estoy planteando es
no quiero hacer 2 clases diferentes 1 para el panel de ingredientes y otra para el panel de utensilios
si no que quiero sin necesidad de utilizar comparaciones (porque si utilizo comparaciones y escribo
el mismo codigo con solo la diferenciacion del objeto en bloques diferentes es lo mismo que escribir
2 clases aparte dentro de una misma, mas bien eso seria una practica incorrecta), por lo que llego a este
dilema, una de dos, o no me complico y acelero el proceso de produccion facilito la usabilidad creando 2 clases
aparte una de la otra para que simplemente sea usar una especifca que hace las cosas especificas de su clase
o puedo intentar aumentar la complejidad intentando usar poliformismo al maximo hasta lograr que apartir de esta misma
clase se puedan utilizar diferentes elementos y crear objetos que van a utilizar objetos diferentes uno con listas
de ingredientes y otro con listas de utensilios, lo que estoy pensando para hacer esto es abusar de la
controladora, como?, bueno por ejemplo, vamos a decir que quiero revisar ingredientes, entonces vamos a tener
un metodo en la controladora llamado isDisponible(), que va a recibir isDisponible?, isDisponible recibiria
el string que se utilizaria para buscar en el map que tenemos en la controladora de cada uno de los elementos
y recibiria en donde vamos a buscar, en este caso delegariamos el trabajo de las comprobaciones a la controladora
esto en cierta parte hace que pierda un poco la gracia de intentar hacer que apartir de esta unica clase podamos hacer
cosas diferentes ya que ahora dependeria de que alguien mas le haga el trabajo de estas comprobaciones con la diferencia
de que ya no habria que cambiar toda la clase para poder reutilizar este codigo si no que en caso de que en otra ocasion
se fuera a necesitar hacer algo igual o al menos muy parecido solamente habria que cambiar el bloque de codigo que hace
uso de la controladora por lo que con esto lograriamos el objetivo de hacer que especificamente esta clase se pueda
reutilizar con facilidad en otros proyectos futuros de ser necesario.
Entonces la idea final seria:
El constructor de esta clase va a recibir, el titulo : String, List<String> elements (que seria la
lista de nombres de los elementos a agregar) y un criterio : String, este criterio va a ser lo que vamos a
mandar como parametro a la controladora en el metodo isDisponible() para que defina en donde va a ir a buscar
esto junto con la String que seria la llave del elemento a buscar en el Map de la controladora
 */
