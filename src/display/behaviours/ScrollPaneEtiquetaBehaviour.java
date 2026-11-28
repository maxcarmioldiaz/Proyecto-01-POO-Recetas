package display.behaviours;

import control.Controladora;
import display.dialogs.*;

import java.awt.Window;
import java.util.List;

public class ScrollPaneEtiquetaBehaviour implements ScrollPaneBehaviour {

    private final Controladora c;

    public ScrollPaneEtiquetaBehaviour() {
        c = Controladora.getInstance();
    }

    @Override
    public List<String> getElementos() {
        return c.getEtiquetas();
    }

    @Override
    public List<String> getElementosReceta() {
        return c.getEtiquetasReceta();
    }

    @Override
    public void agregar(Window ventana) {
        DialogCrear crear = new DialogCrear("etiqueta", ventana);
        crear.setVisible(true);
    }

    @Override
    public void agregarAReceta(String element) {
        c.agregarEtiquetaAReceta(element);
    }

    @Override
    public void eliminarDeReceta(String element) {
        c.eliminarEtiquetaDeReceta(element);
    }

    @Override
    public boolean tieneCheckBox() {
        return false;
    }
}