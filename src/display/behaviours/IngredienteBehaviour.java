package display.behaviours;

import control.Controladora;
import display.dialogs.*;
import java.util.List;
import java.awt.Window;

public class IngredienteBehaviour implements ElementoBehaviour {

    private Controladora c;

    public IngredienteBehaviour() {
        c = Controladora.getInstance();
    }

    @Override
    public List<String> getElementos() {
        return c.getIngredientes();
    }

    @Override
    public boolean isDisponible(String element) {
        return c.isIngredienteDisponible(element);
    }

    @Override
    public void cambiarDisponibilidad(String element, boolean disponible) {
        c.ingredienteCambiarDisponibilidad(element, disponible);
    }

    @Override
    public void agregar(Window ventana) {
        DialogCrear crear = new DialogCrear("ingrediente", ventana);
        crear.setVisible(true);
    }

    @Override
    public void eliminar(String element, Window ventana) {
        DialogBorrar borrar = new DialogBorrar("ingrediente", element, ventana);
    }

    @Override
    public void editar(String element, Window ventana) {
        DialogEditar editar = new DialogEditar("ingrediente", element, ventana);
        editar.setVisible(true);
    }
}