package display.dialogs;

import control.Controladora;

import java.awt.*;

public class DialogCrearEtiqueta extends DialogCrear {

    public DialogCrearEtiqueta(Color azul, Color beige, Color rojo, Font font, Window propietario) {
        super(azul, beige, rojo, font, "Agregar Etiqueta", "Escriba la etiqueta...", propietario);
    }

    @Override
    protected void crear(String nombre) throws Exception {
        Controladora controladora = Controladora.getInstance();

        // agregarEtiqueta(String nombre)
        // Recibe el nombre de la etiqueta y retornavoid. Lanza Exception con el mensaje para el usuario si el nombre
        // esta vacio o si ya existe una etiqueta con ese nombre 
        // Es para registrar la etiqueta para que aparezca en la lista de etiquetas. 
        controladora.agregarEtiqueta(nombre);
    }
}