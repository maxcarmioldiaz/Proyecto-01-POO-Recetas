package display.panels;

import display.behaviours.IngredienteBehaviour;
import display.components.CheckBoxScrollPane;
import display.components.Navegacion;

import javax.swing.*;
import java.awt.*;

public class PanelIngredientes extends JPanel {

    public PanelIngredientes(Color azul, Color beige, Color rojo, Font font, Font fontPequena) {
        setLayout(new BorderLayout(0, 20));
        setBackground(azul);
        setBorder(BorderFactory.createEmptyBorder(10, 20, 30, 20));

        JButton botonVolver = new JButton("\u2190");
        botonVolver.setFont(font);
        botonVolver.setBackground(beige);
        botonVolver.setForeground(rojo);
        botonVolver.setFocusPainted(false);

        // setUpPanelNavegacion(String destino)
        // Necesito que PanelInicio se registre en el CardLayout con el nombre "inicio"
        // para que esta flecha regrese a la pantalla principal
        botonVolver.addActionListener(e -> Navegacion.mostrar(this, "inicio"));

        JPanel contenedorVolver = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        contenedorVolver.setOpaque(false);
        contenedorVolver.add(botonVolver);

        JPanel imagen = new JPanel();
        imagen.setBackground(Color.WHITE);
        imagen.setPreferredSize(new Dimension(160, 120));

        JPanel contenedorImagen = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));
        contenedorImagen.setOpaque(false);
        contenedorImagen.add(imagen);

        JPanel seccionSuperior = new JPanel(new BorderLayout());
        seccionSuperior.setOpaque(false);
        seccionSuperior.add(contenedorVolver, BorderLayout.NORTH);
        seccionSuperior.add(contenedorImagen, BorderLayout.CENTER);

        CheckBoxScrollPane tablaIngredientes = new CheckBoxScrollPane(
                "Ingredientes Disponibles", new IngredienteBehaviour(), beige, rojo, font, fontPequena);
        tablaIngredientes.setBorder(BorderFactory.createLineBorder(rojo));

        add(seccionSuperior, BorderLayout.NORTH);
        add(tablaIngredientes, BorderLayout.CENTER);
    }
}