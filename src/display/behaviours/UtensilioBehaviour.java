package display.behaviours;

import control.Controladora;
import display.dialogs.*;
import java.util.List;
import java.awt.Window;

public class UtensilioBehaviour implements ElementoBehaviour {

    private Controladora c;

    public UtensilioBehaviour() {
        c = Controladora.getInstance();
    }

    @Override
    public List<String> getElementos() {
        return c.getUtensilios();
    }

    @Override
    public boolean isDisponible(String element) {
        return c.isUtensilioDisponible(element);
    }

    @Override
    public void cambiarDisponibilidad(String element,
                                      boolean disponible) {
        c.cambiarDisponibilidadUtensilio(element, disponible);
    }

    @Override
    public void agregar(Window ventana) {
        DialogCrear crear = new DialogCrear("utensilio", ventana);
        crear.setVisible(true);
    }

    @Override
    public void eliminar(String element, Window ventana) {
        DialogBorrar borrar = new DialogBorrar("utensilio", element, ventana);
        borrar.setVisible(true);
    }

    @Override
    public void editar(String element, Window ventana) {
        DialogEditar editar = new DialogEditar("utensilio", element, ventana);

        editar.setVisible(true);
    }
}
