package display.dialogs;

import control.Controladora;

import java.awt.*;

public class DialogCrearUtensilio extends DialogCrear {

    public DialogCrearUtensilio(Color azul, Color beige, Color rojo, Font font, Window propietario) {
        super(azul, beige, rojo, font, "Agregar Utensilio", "Escriba el utensilio...", propietario);
    }

    @Override
    protected void crear(String nombre) throws Exception {
        Controladora controladora = Controladora.getInstance();

        // agregarUtensilio(String nombre)
        // Recibe el nombre del utensilio y retorna void. Lanza Exception con el mensaje para el usuario si el nombre
        // esta vacio o si ya existe un utensilio con ese nombre 
        // Es para registrar el utensilio como NO disponible en la cocina. 
        controladora.agregarUtensilio(nombre);
    }
}