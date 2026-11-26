package logic;

public class Etiqueta {
    private String nombre;

    public Etiqueta(String nombre) throws Exception {
        if (nombre.equals("") || nombre == null)
            throw new Exception("Nombre no puede estar vacio");
        this.nombre = nombre;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) throws Exception {
        if (nombre.equals("") || nombre == null)
            throw new Exception("Nombre no puede estar vacio");
        this.nombre = nombre;
    }
}
