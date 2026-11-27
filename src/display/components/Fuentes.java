package display.components;

import java.io.File;
import java.io.IOException;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.GraphicsEnvironment;

public final class Fuentes {

    public static Font GENERAL_SMALL;
    public static Font MENU;
    public static Font GENERAL;

    private Fuentes() {}
    public void crearFuentes() throws Exception {
        try{
            GENERAL_SMALL = Font.createFont(Font.TRUETYPE_FONT,
                    new File("C:/Users/Lenovo IdeaPad 5/OneDrive - Estudiantes ITCR/2do semestre/P.O.O/proyectos/proyecto01/Proyecto-01-POO/Montserrat/static/Montserrat-Regular.ttf"))
                    .deriveFont(Font.PLAIN,16);
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(GENERAL_SMALL);

            MENU = Font.createFont(Font.TRUETYPE_FONT,
                            new File("C:/Users/Lenovo IdeaPad 5/OneDrive - Estudiantes ITCR/2do semestre/P.O.O/proyectos/proyecto01/Proyecto-01-POO/Montserrat/static/Montserrat-Regular.ttf"))
                    .deriveFont(Font.PLAIN,40);
            ge.registerFont(MENU);

            GENERAL = Font.createFont(Font.TRUETYPE_FONT,
                            new File("C:/Users/Lenovo IdeaPad 5/OneDrive - Estudiantes ITCR/2do semestre/P.O.O/proyectos/proyecto01/Proyecto-01-POO/Montserrat/static/Montserrat-Regular.ttf"))
                    .deriveFont(Font.PLAIN,24);
            ge.registerFont(GENERAL);

        } catch (IOException | FontFormatException e){
            e.printStackTrace();
        }
    }
}
