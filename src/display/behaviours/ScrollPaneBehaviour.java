package display.behaviours;

import javax.swing.*;
import java.awt.Window;
import java.util.List;

public interface ScrollPaneBehaviour {

    List<String> getElementos();

    List<String> getElementosReceta();

    void agregar(Window ventana);

    void agregarAReceta(String element);

    void eliminarDeReceta(String element);

    boolean tieneCheckBox();

    default boolean isDisponible(String element) {
        throw new UnsupportedOperationException();
    }

    default void cambiarDisponibilidad(String element, boolean disponible) {
        throw new UnsupportedOperationException();
    }

    default JComponent crearFila(String element) {
        return new JLabel(element);
    }
}