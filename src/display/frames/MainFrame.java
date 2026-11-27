package display.frames;

import javax.swing.*;
import control.Controladora;
import display.components.Colores;
import display.components.Fuentes;
import display.panels.*;
import display.components.*;

import java.awt.*;

public class MainFrame extends JFrame {
    private Controladora controladora = Controladora.getInstance();

    public MainFrame() {
        setLayout(new BorderLayout());


    }
}
