package display.dialogs;

import control.Controladora;

import java.awt.*;S

public class DialogCrearIngrediente extends DialogCrear {

    public DialogCrearIngrediente(Color azul, Color beige, Color rojo, Font font, Window propietario) {
        super(azul, beige, rojo, font, "Agregar Ingrediente", "Escriba el ingrediente...", propietario);
    }

    @Override
    protected void crear(String nombre) throws Exception {
        Controladora controladora = Controladora.getInstance();

        // agregarIngrediente(String nombre)
        // Recibe el nombre del ingrediente y retorna void. Lanza Exception con el mensaje para el usuario si el nombre
        // esta vacio o si ya existe un ingrediente con ese nombre 
        // Es para registrar el ingrediente como NO disponible en la alacena. 
        controladora.agregarIngrediente(nombre);
    }
}S