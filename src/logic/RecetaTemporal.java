package logic;

import java.util.ArrayList;
import java.util.List;

// Borrador de una receta: no valida nada. Las validaciones se hacen en la controladora
// cuando se confirma, para crear una Receta nueva o modificar una existente.
public class RecetaTemporal {
    private String nombre;
    private Double duracion;
    private Integer porciones;
    private String descripcion;
    private Dificultad dificultad;
    private List<IngredienteContado> ingredientes;
    private List<Utensilio> utensilios;

    // Borrador vacío para crear una receta nueva
    public RecetaTemporal() {
        this.nombre = "";
        this.descripcion = "";
        this.ingredientes = new ArrayList<>();
        this.utensilios = new ArrayList<>();
    }

    // Borrador con copia de los datos de una receta existente (la receta original no se modifica)
    public RecetaTemporal(Receta receta) throws Exception {
        this.nombre = receta.getNombre();
        this.duracion = receta.getDuracion();
        this.porciones = receta.getPorciones();
        this.descripcion = receta.getDescripcion();
        this.dificultad = receta.getDificultad();
        this.ingredientes = new ArrayList<>();
        for (IngredienteContado ingrediente : receta.getIngredientes()) {
            this.ingredientes.add(new IngredienteContado(ingrediente.getNombre(), ingrediente.getCantidad()));
        }
        this.utensilios = new ArrayList<>();
        for (Utensilio utensilio : receta.getUtensilios()) {
            this.utensilios.add(new Utensilio(utensilio.getNombre()));
        }
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Double getDuracion() {
        return duracion;
    }

    public void setDuracion(Double duracion) {
        this.duracion = duracion;
    }

    public Integer getPorciones() {
        return porciones;
    }

    public void setPorciones(Integer porciones) {
        this.porciones = porciones;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Dificultad getDificultad() {
        return dificultad;
    }

    public void setDificultad(Dificultad dificultad) {
        this.dificultad = dificultad;
    }

    public List<IngredienteContado> getIngredientes() {
        return ingredientes;
    }

    public void setIngredientes(List<IngredienteContado> ingredientes) {
        this.ingredientes = ingredientes == null ? new ArrayList<>() : new ArrayList<>(ingredientes);
    }

    public List<Utensilio> getUtensilios() {
        return utensilios;
    }

    public void setUtensilios(List<Utensilio> utensilios) {
        this.utensilios = utensilios == null ? new ArrayList<>() : new ArrayList<>(utensilios);
    }
}
//          Mami, 'tán diciendo que el mundo se va a acabar
//          Me tiene' loquito, ¿cuándo es que te va' a trepar encima?
//          Calientita, pero te cambio el clima

//          ¿Dónde estás? Le voy a llegar
//          Ese cuerpito es una nave espacial
//          Baby, vamo a conectar, tú me gustas natural
//          Con estrías y barriguita, baby, acabaíta de bañar, ey

//           Apaga la lu-lu', Netflix y Hulu
//          La serie nos ve a nosotro, el cuarto frío iglú-glú
//          Te llevo a la moo-moon, ma, tú ere' mi bubu
//          Asfixiao contigo, me hiciste un vudú

//          ¿Dónde estás? Sube pa la suite
//          Yo no salgo a janguear
//          Dándote de espaldita, con la vista al mar

//          Dime si lo que quiere' es jugar
//          Estamo en la Tierra, y yo te siento celestial
//          Me lo hizo rico pa que tenga que virar
//          Cuando estés con alguien, ese cabrón me va a odiar
//          Y me va a bloquear

//          Tú te va' a reír y después me va' a buscar
//          Por ti voy a salir y nos vamo a encontrar
//          El cuento de nunca acabar
//          Dicen que, donde uno fue feliz, vuelve a virar

//          Y tú vuelve' a virar
//          ¿Dónde vamo a acabar?
//          Tú por allá, yo por acá, quizás, baby
//          Nunca vamo a acabar
//          El cuento de nunca acabar




