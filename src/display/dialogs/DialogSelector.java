package display.dialogs;

import display.behaviours.ScrollPaneBehaviour;
import display.components.ScrollPaneSelector;

import javax.swing.*;
import java.awt.*;

public class DialogSelector extends JDialog {

    public DialogSelector(Window ventana, String titulo,
                          ScrollPaneBehaviour behaviour,
                          Color BASE, Color TEXTO, Font GENERAL, Font BOTONES) {

        super(ventana);

        setTitle(titulo);
        setModal(true);

        setLayout(new BorderLayout());

        ScrollPaneSelector selector = new ScrollPaneSelector(
                titulo,
                behaviour,
                BASE,
                TEXTO,
                GENERAL,
                BOTONES
        );

        add(selector, BorderLayout.CENTER);

        setSize(300, 400);

        setLocationRelativeTo(ventana);

        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    }
}