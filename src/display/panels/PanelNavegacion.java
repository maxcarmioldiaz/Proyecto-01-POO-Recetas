package display.panels;

import javax.swing.*;
import display.components.*;
import java.awt.*;

public class PanelNavegacion extends JPanel {
    private CardLayout cardLayout;

    public PanelNavegacion() {
        cardLayout = new CardLayout();
        setLayout(cardLayout);


    }
}
