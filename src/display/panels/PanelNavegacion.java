package display.panels;

import javax.swing.*;
import display.components.*;
import java.awt.*;

public class PanelNavegacion extends JPanel {
    private CardLayout cardLayout;

    public PanelNavegacion() {
        cardLayout = new CardLayout();
        setLayout(cardLayout);

        add(new PanelInicio(Colores.AZUL_NAVY, Colores.BEIGE, Colores.ROJO_MAHOGANY, Fuentes.MENU), "inicio");
        add(new PanelQueCocinar(), "que_cocinar");
        add(new PanelRecetas(), "recetas");
        add(new PanelUtensilios(), "utensilios");
        add(new PanelIngredientes(), "ingredientes");
        add(new PanelVerReceta(), "ver_receta");
        add(new PanelEditarReceta(), "editar_receta");
        add(new PanelCrearReceta(), "crear_receta");
    }

    public void setUpPanelNavegacion(String nombre) {
        cardLayout.show(this, nombre);
    }
}
