package display.components;

import display.behaviours.ElementoBehaviour;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class ComboBox extends JComboBox {

    public ComboBox(String criterio, Color BASE, List<String> elements,
                    Color TEXTO, Font GENERAL, Font BOTONES) {
        for (String element : elements) {
            this.addItem(element);
        }


        String selectedElement = (String) this.getSelectedItem();

    }
}
