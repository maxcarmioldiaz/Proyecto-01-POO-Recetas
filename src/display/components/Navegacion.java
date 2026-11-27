package display.components;

import display.panels.PanelNavegacion;
import javax.swing.*;

public final class Navegacion {

    private Navegacion() {}

    public static void mostrar(JPanel origen, String destino) {
        PanelNavegacion contenido = (PanelNavegacion) SwingUtilities.getAncestorOfClass(PanelNavegacion.class, origen);
        contenido.setUpPanelNavegacion(destino);
    }
}