package display.behaviours;

import control.Controladora;
import display.dialogs.*;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class ScrollPaneIngredienteBehaviour implements ScrollPaneBehaviour {

    private final Controladora c;

    public ScrollPaneIngredienteBehaviour() {
        c = Controladora.getInstance();
    }

    @Override
    public List<String> getElementos() {
        return c.getIngredientes();
    }

    @Override
    public List<String> getElementosReceta() {
        return c.getIngredientesReceta();
    }

    @Override
    public void agregar(Window ventana) {
        DialogCrear crear = new DialogCrear("ingrediente", ventana);
        crear.setVisible(true);
    }

    @Override
    public void agregarAReceta(String element) {
        c.agregarIngredienteAReceta(element);
    }

    @Override
    public void eliminarDeReceta(String element) {
        c.eliminarIngredienteDeReceta(element);
    }

    @Override
    public boolean tieneCheckBox() {
        return true;
    }

    @Override
    public boolean isDisponible(String element) {
        return c.isIngredienteDisponible(element);
    }

    @Override
    public void cambiarDisponibilidad(String element, boolean disponible) {
        c.cambiarDisponibilidadIngrediente(element, disponible);
    }

    @Override
    public JComponent crearFila(String element) {
        JPanel contenido = new JPanel(new GridLayout(1, 2));

        JLabel nombre = new JLabel(element);

        JLabel cantidad = new JLabel(
                c.getCantidadIngredienteReceta(element),
                SwingConstants.CENTER
        );

        contenido.setOpaque(false);

        contenido.add(nombre);
        contenido.add(cantidad);

        return contenido;
    }
}