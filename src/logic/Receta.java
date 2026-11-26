package logic;

import java.sql.SQLSyntaxErrorException;
import java.util.List;
import java.util.ArrayList;

public class Receta {
    private String nombre;
    private Double duracion;
    private Integer porciones;
    private String descripcion;
    private List<String> imagenes;
    private Dificultad dificultad;
    private List<Etiqueta> etiquetas;
    private List<IngredienteContado> ingredientes;
    private List<Utensilio> utensilios;

    public Receta(List<Utensilio> utensilios, Integer porciones, String nombre,
                  List<IngredienteContado> ingredientes, Double duracion, Dificultad dificultad,
                  String descripcion, List<String> imagenes, List<Etiqueta> etiquetas) throws Exception {
        if (utensilios.isEmpty())
            throw new Exception("La lista de utensilios no puede estar vacia");
        if (porciones == null || porciones <= 0)
            throw new Exception("Debe haber una cantidad de porciones positiva");
        if (nombre == null || nombre.equals(""))
            throw new Exception("El nombre no puede estar vacio");
        if (ingredientes.isEmpty())
            throw new Exception("La lista de ingredientes no puede estar vacia");
        if (duracion <= 0)
            throw new Exception("Debe escribir una duracion positiva");
        if (descripcion == null || descripcion.equals(""))
            throw new Exception("La descripcion no debe estar vacia");
        this.utensilios = utensilios;
        this.porciones = porciones;
        this.nombre = nombre;
        this.ingredientes = ingredientes;
        this.etiquetas = etiquetas;
        this.imagenes = imagenes;
        this.duracion = duracion;
        this.dificultad = dificultad;
        this.descripcion = descripcion;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) throws Exception {
        if (descripcion == null || descripcion.equals(""))
            throw new Exception("La descripcion no debe estar vacia");
        this.descripcion = descripcion;
    }

    public Dificultad getDificultad() {
        return dificultad;
    }

    public void setDificultad(Dificultad dificultad) {
        this.dificultad = dificultad;
    }

    public Double getDuracion() {
        return duracion;
    }

    public void setDuracion(Double duracion) throws Exception {
        if (duracion <= 0)
            throw new Exception("Debe escribir una duracion positiva");
        this.duracion = duracion;
    }

    public List<Etiqueta> getEtiquetas() {
        return etiquetas;
    }

    public void setEtiquetas(List<Etiqueta> etiquetas) {
        this.etiquetas = etiquetas;
    }

    public List<String> getImagenes() {
        return imagenes;
    }

    public void setImagenes(List<String> imagenes) {
        this.imagenes = imagenes;
    }

    public List<IngredienteContado> getIngredientes() {
        return ingredientes;
    }

    public void setIngredientes(List<IngredienteContado> ingredientes) throws Exception {
        if (ingredientes.isEmpty())
            throw new Exception("La lista de ingredientes no puede estar vacia");
        this.ingredientes = ingredientes;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) throws Exception {
        if (nombre == null || nombre.equals(""))
            throw new Exception("El nombre no puede estar vacio");
        this.nombre = nombre;
    }

    public Integer getPorciones() {
        return porciones;
    }

    public void setPorciones(Integer porciones) throws Exception {
        if (porciones == null || porciones <= 0)
            throw new Exception("Debe haber una cantidad de porciones positiva");
        this.porciones = porciones;
    }

    public List<Utensilio> getUtensilios() {
        return utensilios;
    }

    public void setUtensilios(List<Utensilio> utensilios) throws Exception {
        if (utensilios.isEmpty())
            throw new Exception("La lista de utensilios no puede estar vacia");
        this.utensilios = utensilios;
    }
}
