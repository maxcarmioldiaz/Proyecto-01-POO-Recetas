package logic;

public class Utensilio {
    private String nombre;
    private Boolean disponible;

    public Utensilio(String nombre) throws Exception {
        if (nombre.equals("") || nombre == null)
            throw new Exception("Nombre no puede estar vacio");
        this.disponible = false;
        this.nombre = nombre;
    }

    public Boolean getDisponible() {
        return disponible;
    }

    public void setDisponible(Boolean disponible) {
        this.disponible = disponible;
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
