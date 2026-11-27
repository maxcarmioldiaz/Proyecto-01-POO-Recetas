package display.behaviours;

import java.util.List;
import java.awt.Window;

public interface ElementoBehaviour {

        List<String> getElementos();

        boolean isDisponible(String element);

        void cambiarDisponibilidad(String element, boolean disponible);

        void agregar(Window ventana);

        void eliminar(String element, Window ventana);

        void editar(String element, Window ventana);
}
