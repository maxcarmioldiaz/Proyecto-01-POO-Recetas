package display.behaviours;

import control.Controladora;
import display.dialogs.*;

import java.awt.Window;
import java.util.List;

public class ScrollPaneUtensilioBehaviour implements ScrollPaneBehaviour {

    private final Controladora c;

    public ScrollPaneUtensilioBehaviour() {
        c = Controladora.getInstance();
    }

    @Override
    public List<String> getElementos() {
        return c.getUtensilios();
    }

    @Override
    public List<String> getElementosReceta() {
        return c.getUtensiliosReceta();
    }

    @Override
    public void agregar(Window ventana) {
        DialogCrear crear = new DialogCrear("utensilio", ventana);
        crear.setVisible(true);
    }

    @Override
    public void agregarAReceta(String element) {
        c.agregarUtensilioAReceta(element);
    }

    @Override
    public void eliminarDeReceta(String element) {
        c.eliminarUtensilioDeReceta(element);
    }

    @Override
    public boolean tieneCheckBox() {
        return true;
    }

    @Override
    public boolean isDisponible(String element) {
        return c.isUtensilioDisponible(element);
    }

    @Override
    public void cambiarDisponibilidad(String element, boolean disponible) {
        c.cambiarDisponibilidadUtensilio(element, disponible);
    }
}