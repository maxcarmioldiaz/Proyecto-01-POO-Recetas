package control;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import logic.Dificultad;
import logic.Etiqueta;
import logic.Ingrediente;
import logic.IngredienteContado;
import logic.Pair;
import logic.Receta;
import logic.RecetaTemporal;
import logic.Utensilio;

public class Controladora {
    private static Controladora instance = null;
    private static final String CANTIDAD_POR_DEFECTO = "Al gusto";
    private Map<String, Ingrediente> ingredientesUsuario;
    private Map<String, Utensilio> utensiliosUsuario;
    private Map<String, Receta> recetas;
    private Map<String, Etiqueta> etiquetas;
    // Borrador sobre el que operan los métodos sin posición (verReceta, getIngredientesReceta, etc.)
    private RecetaTemporal recetaTemporal;
    // Receta que se está modificando con el borrador (null si el borrador es para crear una receta nueva)
    private Receta recetaEnEdicion;

    private Controladora() {
        ingredientesUsuario = new LinkedHashMap<>();
        utensiliosUsuario = new LinkedHashMap<>();
        recetas = new LinkedHashMap<>();
        etiquetas = new LinkedHashMap<>();
        recetaTemporal = null;
        recetaEnEdicion = null;
    }

    public static Controladora getInstance() {
        if (instance == null) instance = new Controladora();
        return instance;
    }

    // ------------------------------------------------------------------
    // Métodos privados de apoyo
    // ------------------------------------------------------------------

    // Las claves de los mapas ignoran mayúsculas y espacios en los extremos
    private String normalizar(String texto) {
        return texto == null ? "" : texto.trim().toLowerCase();
    }

    private void validarNombre(String nombre, String entidad) throws Exception {
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new Exception("El nombre " + entidad + " no puede estar vacío.");
        }
    }

    private void validarPosicion(Integer pos, int tamano, String descripcion) throws Exception {
        if (pos == null || pos < 0 || pos >= tamano) {
            throw new Exception("La posición " + descripcion + " no es válida.");
        }
    }

    private <T> T obtenerPorPosicion(Map<String, T> mapa, Integer pos, String descripcion) throws Exception {
        validarPosicion(pos, mapa.size(), descripcion);
        return new ArrayList<>(mapa.values()).get(pos);
    }

    // Se reconstruye el mapa para actualizar la clave sin cambiar el orden de las posiciones
    private <T> Map<String, T> reemplazarClave(Map<String, T> mapa, T objeto, String claveNueva) {
        Map<String, T> nuevoMapa = new LinkedHashMap<>();
        for (Map.Entry<String, T> entrada : mapa.entrySet()) {
            if (entrada.getValue() == objeto) {
                nuevoMapa.put(claveNueva, objeto);
            } else {
                nuevoMapa.put(entrada.getKey(), entrada.getValue());
            }
        }
        return nuevoMapa;
    }

    private <T> ArrayList<T> copiar(List<T> origen) {
        return origen == null ? new ArrayList<>() : new ArrayList<>(origen);
    }

    private void validarSinRepetidos(List<String> nombres, String elementos) throws Exception {
        Set<String> vistos = new HashSet<>();
        for (String nombre : nombres) {
            if (!vistos.add(normalizar(nombre))) {
                throw new Exception("La receta tiene " + elementos + " repetidos.");
            }
        }
    }

    private void validarDatosReceta(String nombre, Double duracion, Dificultad dificultad, Integer porciones,
                                    List<IngredienteContado> ingredientes, List<Utensilio> utensilios,
                                    String descripcion, List<Etiqueta> etiquetasReceta) throws Exception {
        validarNombre(nombre, "de la receta");
        if (duracion == null || duracion <= 0) {
            throw new Exception("La duración debe ser mayor que cero.");
        }
        if (dificultad == null) {
            throw new Exception("La dificultad no puede ser nula.");
        }
        if (porciones == null || porciones <= 0) {
            throw new Exception("Las porciones deben ser mayores que cero.");
        }
        if (ingredientes == null || ingredientes.isEmpty()) {
            throw new Exception("La receta debe tener al menos un ingrediente.");
        }
        List<String> nombresIngredientes = new ArrayList<>();
        for (IngredienteContado ingrediente : ingredientes) {
            if (ingrediente == null) {
                throw new Exception("La receta contiene un ingrediente nulo.");
            }
            nombresIngredientes.add(ingrediente.getNombre());
        }
        validarSinRepetidos(nombresIngredientes, "ingredientes");
        if (utensilios == null || utensilios.isEmpty()) {
            throw new Exception("La receta debe tener al menos un utensilio.");
        }
        List<String> nombresUtensilios = new ArrayList<>();
        for (Utensilio utensilio : utensilios) {
            if (utensilio == null) {
                throw new Exception("La receta contiene un utensilio nulo.");
            }
            nombresUtensilios.add(utensilio.getNombre());
        }
        validarSinRepetidos(nombresUtensilios, "utensilios");
        if (descripcion == null || descripcion.trim().isEmpty()) {
            throw new Exception("La descripción no puede estar vacía.");
        }
        if (etiquetasReceta != null) {
            for (Etiqueta etiqueta : etiquetasReceta) {
                if (etiqueta == null) {
                    throw new Exception("La receta contiene una etiqueta nula.");
                }
                validarNombre(etiqueta.getNombre(), "de la etiqueta");
            }
        }
    }

    // Guarda la etiqueta en el registro (si no estaba) y devuelve la instancia registrada
    private Etiqueta registrarEtiqueta(Etiqueta etiqueta) {
        String clave = normalizar(etiqueta.getNombre());
        Etiqueta registrada = etiquetas.get(clave);
        if (registrada == null) {
            etiquetas.put(clave, etiqueta);
            registrada = etiqueta;
        }
        return registrada;
    }

    // Registra las etiquetas y elimina las repetidas conservando el orden
    private ArrayList<Etiqueta> resolverEtiquetas(List<Etiqueta> lista) {
        Map<String, Etiqueta> unicas = new LinkedHashMap<>();
        if (lista != null) {
            for (Etiqueta etiqueta : lista) {
                Etiqueta registrada = registrarEtiqueta(etiqueta);
                unicas.put(normalizar(registrada.getNombre()), registrada);
            }
        }
        return new ArrayList<>(unicas.values());
    }

    private void validarListaFiltro(ArrayList<Pair<Receta, Integer>> lista) throws Exception {
        if (lista == null) {
            throw new Exception("La lista de recetas a filtrar no puede ser nula.");
        }
    }

    private RecetaTemporal obtenerRecetaTemporal() throws Exception {
        if (recetaTemporal == null) {
            throw new Exception("No hay una receta en edición.");
        }
        return recetaTemporal;
    }

    private String textoDificultad(Dificultad dificultad) {
        switch (dificultad) {
            case FACIL: return "Fácil";
            case MEDIO: return "Medio";
            case DIFICIL: return "Difícil";
            case EXPERTO: return "Experto";
            default: return dificultad.toString();
        }
    }

    // ------------------------------------------------------------------
    // Ingredientes del usuario
    // ------------------------------------------------------------------

    public Ingrediente obtenerIngrediente(Integer posIngrediente) throws Exception {
        return obtenerPorPosicion(ingredientesUsuario, posIngrediente, "del ingrediente");
    }

    public ArrayList<String> verIngredientes() {
        ArrayList<String> resultado = new ArrayList<>();
        for (Ingrediente ingrediente : ingredientesUsuario.values()) {
            resultado.add(ingrediente.getNombre());
        }
        return resultado;
    }

    public Boolean addIngrediente(String nombre) throws Exception {
        validarNombre(nombre, "del ingrediente");
        String clave = normalizar(nombre);
        if (ingredientesUsuario.containsKey(clave)) {
            throw new Exception("Ya existe un ingrediente con ese nombre.");
        }
        Ingrediente ingrediente = new Ingrediente(nombre.trim());
        ingrediente.setDisponible(true);
        ingredientesUsuario.put(clave, ingrediente);
        return true;
    }

    public Boolean editIngrediente(Integer posIngrediente, String nombre) throws Exception {
        Ingrediente ingrediente = obtenerIngrediente(posIngrediente);
        validarNombre(nombre, "del ingrediente");
        String claveNueva = normalizar(nombre);
        Ingrediente existente = ingredientesUsuario.get(claveNueva);
        if (existente != null && existente != ingrediente) {
            throw new Exception("Ya existe otro ingrediente con ese nombre.");
        }
        ingrediente.setNombre(nombre.trim());
        ingredientesUsuario = reemplazarClave(ingredientesUsuario, ingrediente, claveNueva);
        return true;
    }

    public Boolean deleteIngrediente(Integer posIngrediente) throws Exception {
        Ingrediente ingrediente = obtenerIngrediente(posIngrediente);
        ingredientesUsuario.values().remove(ingrediente);
        return true;
    }

    public void ingredienteCambiarDisponibilidad(Integer posIngrediente, Boolean disponible) throws Exception {
        Ingrediente ingrediente = obtenerIngrediente(posIngrediente);
        if (disponible == null) {
            throw new Exception("La disponibilidad no puede ser nula.");
        }
        ingrediente.setDisponible(disponible);
    }

    // Un ingrediente que no está registrado se considera no disponible
    public Boolean isIngredienteDisponible(String nombreIngrediente) {
        Ingrediente ingrediente = ingredientesUsuario.get(normalizar(nombreIngrediente));
        return ingrediente != null && Boolean.TRUE.equals(ingrediente.getDisponible());
    }

    // ------------------------------------------------------------------
    // Utensilios del usuario
    // ------------------------------------------------------------------

    public Utensilio obtenerUtensilio(Integer posUtensilio) throws Exception {
        return obtenerPorPosicion(utensiliosUsuario, posUtensilio, "del utensilio");
    }

    public ArrayList<String> verUtensilios() {
        ArrayList<String> resultado = new ArrayList<>();
        for (Utensilio utensilio : utensiliosUsuario.values()) {
            resultado.add(utensilio.getNombre());
        }
        return resultado;
    }

    // Igual que agregarUtensilio: el utensilio queda registrado como NO disponible (como en la clase Utensilio)
    public Boolean addUtensilio(String nombre) throws Exception {
        agregarUtensilio(nombre);
        return true;
    }

    // Registra el utensilio como NO disponible en la cocina
    public void agregarUtensilio(String nombre) throws Exception {
        validarNombre(nombre, "del utensilio");
        String clave = normalizar(nombre);
        if (utensiliosUsuario.containsKey(clave)) {
            throw new Exception("Ya existe un utensilio con ese nombre.");
        }
        Utensilio utensilio = new Utensilio(nombre.trim());
        utensilio.setDisponible(false);
        utensiliosUsuario.put(clave, utensilio);
    }

    public Boolean editUtensilio(Integer posUtensilio, String nombre) throws Exception {
        Utensilio utensilio = obtenerUtensilio(posUtensilio);
        validarNombre(nombre, "del utensilio");
        String claveNueva = normalizar(nombre);
        Utensilio existente = utensiliosUsuario.get(claveNueva);
        if (existente != null && existente != utensilio) {
            throw new Exception("Ya existe otro utensilio con ese nombre.");
        }
        utensilio.setNombre(nombre.trim());
        utensiliosUsuario = reemplazarClave(utensiliosUsuario, utensilio, claveNueva);
        return true;
    }

    public Boolean deleteUtensilio(Integer posUtensilio) throws Exception {
        Utensilio utensilio = obtenerUtensilio(posUtensilio);
        utensiliosUsuario.values().remove(utensilio);
        return true;
    }

    public void utensilioCambiarDisponibilidad(Integer posUtensilio, Boolean disponible) throws Exception {
        Utensilio utensilio = obtenerUtensilio(posUtensilio);
        if (disponible == null) {
            throw new Exception("La disponibilidad no puede ser nula.");
        }
        utensilio.setDisponible(disponible);
    }

    // Un utensilio que no está registrado se considera no disponible
    public Boolean isUtensilioDisponible(String nombreUtensilio) {
        Utensilio utensilio = utensiliosUsuario.get(normalizar(nombreUtensilio));
        return utensilio != null && Boolean.TRUE.equals(utensilio.getDisponible());
    }

    // ------------------------------------------------------------------
    // Recetas
    // ------------------------------------------------------------------

    public Receta obtenerReceta(Integer posReceta) throws Exception {
        return obtenerPorPosicion(recetas, posReceta, "de la receta");
    }

    public ArrayList<String> verRecetas() {
        ArrayList<String> resultado = new ArrayList<>();
        for (Receta receta : recetas.values()) {
            resultado.add(receta.getNombre());
        }
        return resultado;
    }

    // Abre un borrador vacío para crear una receta nueva (descarta el borrador anterior, si había)
    public void nuevaRecetaTemporal() {
        recetaTemporal = new RecetaTemporal();
        recetaEnEdicion = null;
    }

    // Abre un borrador con una copia de los datos de la receta indicada (la receta no cambia hasta confirmar)
    public void editarRecetaTemporal(Integer posReceta) throws Exception {
        Receta receta = obtenerReceta(posReceta);
        RecetaTemporal borrador = new RecetaTemporal(receta);
        recetaTemporal = borrador;
        recetaEnEdicion = receta;
    }

    // Guarda los datos simples en el borrador, sin validarlos (se validan al confirmar)
    public void actualizarDatosRecetaTemporal(String nombre, Double duracion, Dificultad dificultad,
                                              Integer porciones, String descripcion) throws Exception {
        RecetaTemporal borrador = obtenerRecetaTemporal();
        borrador.setNombre(nombre);
        borrador.setDuracion(duracion);
        borrador.setDificultad(dificultad);
        borrador.setPorciones(porciones);
        borrador.setDescripcion(descripcion);
    }

    // Valida el borrador y, si es correcto, crea la receta nueva o modifica la existente.
    // Si algún dato no es válido lanza la excepción y no se toca nada (el borrador sigue abierto).
    // Las etiquetas e imágenes no pasan por el borrador: se manejan con addEtiquetaReceta/addImagenReceta.
    public void confirmarRecetaTemporal() throws Exception {
        RecetaTemporal borrador = obtenerRecetaTemporal();
        validarDatosReceta(borrador.getNombre(), borrador.getDuracion(), borrador.getDificultad(),
                borrador.getPorciones(), borrador.getIngredientes(), borrador.getUtensilios(),
                borrador.getDescripcion(), null);
        String claveNueva = normalizar(borrador.getNombre());
        Receta existente = recetas.get(claveNueva);
        if (existente != null && existente != recetaEnEdicion) {
            throw new Exception("Ya existe una receta con ese nombre.");
        }
        if (recetaEnEdicion == null) {
            Receta nueva = new Receta(copiar(borrador.getUtensilios()), borrador.getPorciones(),
                    borrador.getNombre().trim(), copiar(borrador.getIngredientes()), borrador.getDuracion(),
                    borrador.getDificultad(), borrador.getDescripcion(), new ArrayList<>(), new ArrayList<>());
            recetas.put(claveNueva, nueva);
        } else {
            recetaEnEdicion.setNombre(borrador.getNombre().trim());
            recetaEnEdicion.setDuracion(borrador.getDuracion());
            recetaEnEdicion.setDificultad(borrador.getDificultad());
            recetaEnEdicion.setPorciones(borrador.getPorciones());
            recetaEnEdicion.setIngredientes(copiar(borrador.getIngredientes()));
            recetaEnEdicion.setUtensilios(copiar(borrador.getUtensilios()));
            recetaEnEdicion.setDescripcion(borrador.getDescripcion());
            recetas = reemplazarClave(recetas, recetaEnEdicion, claveNueva);
        }
        cancelarRecetaTemporal();
    }

    // Descarta el borrador sin cambiar ninguna receta
    public void cancelarRecetaTemporal() {
        recetaTemporal = null;
        recetaEnEdicion = null;
    }

    // Devuelve: nombre, duración, dificultad, porciones y descripción del borrador (en ese orden).
    // Los datos que todavía no se han escrito se devuelven como texto vacío.
    public ArrayList<String> verReceta() throws Exception {
        RecetaTemporal borrador = obtenerRecetaTemporal();
        ArrayList<String> datos = new ArrayList<>();
        datos.add(borrador.getNombre() == null ? "" : borrador.getNombre());
        datos.add(borrador.getDuracion() == null ? "" : String.valueOf(borrador.getDuracion()));
        datos.add(borrador.getDificultad() == null ? "" : textoDificultad(borrador.getDificultad()));
        datos.add(borrador.getPorciones() == null ? "" : String.valueOf(borrador.getPorciones()));
        datos.add(borrador.getDescripcion() == null ? "" : borrador.getDescripcion());
        return datos;
    }

    public Boolean addReceta(String nombre, Double duracion, Dificultad dificultad, Integer porciones,
                             ArrayList<IngredienteContado> ingredientes, ArrayList<Utensilio> utensilios,
                             String descripcion, ArrayList<Etiqueta> etiquetasReceta,
                             ArrayList<String> fotos) throws Exception {
        validarDatosReceta(nombre, duracion, dificultad, porciones, ingredientes, utensilios, descripcion, etiquetasReceta);
        String clave = normalizar(nombre);
        if (recetas.containsKey(clave)) {
            throw new Exception("Ya existe una receta con ese nombre.");
        }
        Receta receta = new Receta(copiar(utensilios), porciones, nombre.trim(), copiar(ingredientes), duracion,
                dificultad, descripcion, copiar(fotos), resolverEtiquetas(etiquetasReceta));
        recetas.put(clave, receta);
        return true;
    }

    public Boolean editReceta(Integer posReceta, String nombre, Double duracion, Dificultad dificultad,
                              Integer porciones, ArrayList<IngredienteContado> ingredientes,
                              ArrayList<Utensilio> utensilios, String descripcion,
                              ArrayList<Etiqueta> etiquetasReceta, ArrayList<String> fotos) throws Exception {
        Receta receta = obtenerReceta(posReceta);
        validarDatosReceta(nombre, duracion, dificultad, porciones, ingredientes, utensilios, descripcion, etiquetasReceta);
        String claveNueva = normalizar(nombre);
        Receta existente = recetas.get(claveNueva);
        if (existente != null && existente != receta) {
            throw new Exception("Ya existe otra receta con ese nombre.");
        }
        receta.setNombre(nombre.trim());
        receta.setDuracion(duracion);
        receta.setDificultad(dificultad);
        receta.setPorciones(porciones);
        receta.setIngredientes(copiar(ingredientes));
        receta.setUtensilios(copiar(utensilios));
        receta.setDescripcion(descripcion);
        receta.setEtiquetas(resolverEtiquetas(etiquetasReceta));
        receta.setImagenes(copiar(fotos));
        recetas = reemplazarClave(recetas, receta, claveNueva);
        return true;
    }

    public Boolean deleteReceta(Integer posReceta) throws Exception {
        Receta receta = obtenerReceta(posReceta);
        recetas.values().remove(receta);
        if (receta == recetaEnEdicion) {
            cancelarRecetaTemporal();
        }
        return true;
    }

    // ------------------------------------------------------------------
    // Filtros
    // ------------------------------------------------------------------

    // Devuelve todas las recetas con la cantidad de ingredientes y utensilios que faltan para prepararlas
    // (no registrados o marcados como no disponibles), ordenadas de menos a más faltantes.
    public ArrayList<Pair<Receta, Integer>> filtrarPorIngredientesYutensilios() {
        ArrayList<Pair<Receta, Integer>> resultado = new ArrayList<>();
        for (Receta receta : recetas.values()) {
            int faltantes = 0;
            for (IngredienteContado ingrediente : receta.getIngredientes()) {
                if (!isIngredienteDisponible(ingrediente.getNombre())) {
                    faltantes++;
                }
            }
            for (Utensilio utensilio : receta.getUtensilios()) {
                if (!isUtensilioDisponible(utensilio.getNombre())) {
                    faltantes++;
                }
            }
            resultado.add(new Pair<>(receta, faltantes));
        }
        resultado.sort((a, b) -> Integer.compare(a.getValue(), b.getValue()));
        return resultado;
    }

    public ArrayList<Pair<Receta, Integer>> filtrarPorDificultad(Dificultad criterio,
            ArrayList<Pair<Receta, Integer>> lista) throws Exception {
        validarListaFiltro(lista);
        if (criterio == null) {
            throw new Exception("La dificultad no puede ser nula.");
        }
        ArrayList<Pair<Receta, Integer>> resultado = new ArrayList<>();
        for (Pair<Receta, Integer> par : lista) {
            if (par.getKey().getDificultad() == criterio) {
                resultado.add(par);
            }
        }
        return resultado;
    }

    // El criterio es la duración máxima permitida (en las mismas unidades que la duración de la receta)
    public ArrayList<Pair<Receta, Integer>> filtrarPorDuracion(Integer criterio,
            ArrayList<Pair<Receta, Integer>> lista) throws Exception {
        validarListaFiltro(lista);
        if (criterio == null || criterio <= 0) {
            throw new Exception("La duración máxima debe ser mayor que cero.");
        }
        ArrayList<Pair<Receta, Integer>> resultado = new ArrayList<>();
        for (Pair<Receta, Integer> par : lista) {
            if (par.getKey().getDuracion() <= criterio) {
                resultado.add(par);
            }
        }
        return resultado;
    }

    // Conserva las recetas que tienen todas las etiquetas indicadas
    public ArrayList<Pair<Receta, Integer>> filtrarPorEtiqueta(ArrayList<Etiqueta> criterio,
            ArrayList<Pair<Receta, Integer>> lista) throws Exception {
        validarListaFiltro(lista);
        if (criterio == null || criterio.isEmpty()) {
            throw new Exception("Debe indicar al menos una etiqueta.");
        }
        Set<String> buscadas = new HashSet<>();
        for (Etiqueta etiqueta : criterio) {
            if (etiqueta == null) {
                throw new Exception("La etiqueta no puede ser nula.");
            }
            buscadas.add(normalizar(etiqueta.getNombre()));
        }
        ArrayList<Pair<Receta, Integer>> resultado = new ArrayList<>();
        for (Pair<Receta, Integer> par : lista) {
            Set<String> delaReceta = new HashSet<>();
            for (Etiqueta etiqueta : par.getKey().getEtiquetas()) {
                delaReceta.add(normalizar(etiqueta.getNombre()));
            }
            if (delaReceta.containsAll(buscadas)) {
                resultado.add(par);
            }
        }
        return resultado;
    }

    // ------------------------------------------------------------------
    // Ingredientes del borrador de receta
    // ------------------------------------------------------------------

    public List<String> getIngredientesReceta() throws Exception {
        List<String> resultado = new ArrayList<>();
        for (IngredienteContado ingrediente : obtenerRecetaTemporal().getIngredientes()) {
            resultado.add(ingrediente.getNombre());
        }
        return resultado;
    }

    public void agregarIngredienteAReceta(String element) throws Exception {
        agregarIngredienteAReceta(element, CANTIDAD_POR_DEFECTO);
    }

    public void agregarIngredienteAReceta(String element, String cantidad) throws Exception {
        RecetaTemporal borrador = obtenerRecetaTemporal();
        validarNombre(element, "del ingrediente");
        if (cantidad == null || cantidad.trim().isEmpty()) {
            throw new Exception("La cantidad del ingrediente no puede estar vacía.");
        }
        for (IngredienteContado ingrediente : borrador.getIngredientes()) {
            if (normalizar(ingrediente.getNombre()).equals(normalizar(element))) {
                throw new Exception("La receta ya tiene ese ingrediente.");
            }
        }
        borrador.getIngredientes().add(new IngredienteContado(element.trim(), cantidad.trim()));
    }

    public void eliminarIngredienteDeReceta(String element) throws Exception {
        List<IngredienteContado> lista = obtenerRecetaTemporal().getIngredientes();
        for (int i = 0; i < lista.size(); i++) {
            if (normalizar(lista.get(i).getNombre()).equals(normalizar(element))) {
                lista.remove(i);
                return;
            }
        }
        throw new Exception("La receta no tiene ese ingrediente.");
    }

    public String getCantidadIngredienteReceta(String element) throws Exception {
        for (IngredienteContado ingrediente : obtenerRecetaTemporal().getIngredientes()) {
            if (normalizar(ingrediente.getNombre()).equals(normalizar(element))) {
                return ingrediente.getCantidad();
            }
        }
        throw new Exception("La receta no tiene ese ingrediente.");
    }

    // ------------------------------------------------------------------
    // Utensilios del borrador de receta
    // ------------------------------------------------------------------

    public List<String> getUtensiliosReceta() throws Exception {
        List<String> resultado = new ArrayList<>();
        for (Utensilio utensilio : obtenerRecetaTemporal().getUtensilios()) {
            resultado.add(utensilio.getNombre());
        }
        return resultado;
    }

    public void agregarUtensilioAReceta(String element) throws Exception {
        RecetaTemporal borrador = obtenerRecetaTemporal();
        validarNombre(element, "del utensilio");
        for (Utensilio utensilio : borrador.getUtensilios()) {
            if (normalizar(utensilio.getNombre()).equals(normalizar(element))) {
                throw new Exception("La receta ya tiene ese utensilio.");
            }
        }
        borrador.getUtensilios().add(new Utensilio(element.trim()));
    }

    public void eliminarUtensilioDeReceta(String element) throws Exception {
        List<Utensilio> lista = obtenerRecetaTemporal().getUtensilios();
        for (int i = 0; i < lista.size(); i++) {
            if (normalizar(lista.get(i).getNombre()).equals(normalizar(element))) {
                lista.remove(i);
                return;
            }
        }
        throw new Exception("La receta no tiene ese utensilio.");
    }

    // ------------------------------------------------------------------
    // Etiquetas
    // ------------------------------------------------------------------

    // Etiquetas registradas (las que alguna receta ha usado alguna vez)
    public ArrayList<String> verEtiquetas() {
        ArrayList<String> resultado = new ArrayList<>();
        for (Etiqueta etiqueta : etiquetas.values()) {
            resultado.add(etiqueta.getNombre());
        }
        return resultado;
    }

    // Registra la etiqueta para que aparezca en la lista de etiquetas (verEtiquetas)
    public void agregarEtiqueta(String nombre) throws Exception {
        validarNombre(nombre, "de la etiqueta");
        String clave = normalizar(nombre);
        if (etiquetas.containsKey(clave)) {
            throw new Exception("Ya existe una etiqueta con ese nombre.");
        }
        etiquetas.put(clave, new Etiqueta(nombre.trim()));
    }

    // Etiquetas de la receta que se está editando (vacía si el borrador es para una receta nueva)
    public List<String> getEtiquetasReceta() throws Exception {
        obtenerRecetaTemporal();
        List<String> resultado = new ArrayList<>();
        if (recetaEnEdicion != null) {
            for (Etiqueta etiqueta : recetaEnEdicion.getEtiquetas()) {
                resultado.add(etiqueta.getNombre());
            }
        }
        return resultado;
    }

    public Boolean addEtiquetaReceta(Integer posReceta, Etiqueta etiqueta) throws Exception {
        Receta receta = obtenerReceta(posReceta);
        if (etiqueta == null) {
            throw new Exception("La etiqueta no puede ser nula.");
        }
        validarNombre(etiqueta.getNombre(), "de la etiqueta");
        ArrayList<Etiqueta> lista = copiar(receta.getEtiquetas());
        for (Etiqueta existente : lista) {
            if (normalizar(existente.getNombre()).equals(normalizar(etiqueta.getNombre()))) {
                throw new Exception("La receta ya tiene esa etiqueta.");
            }
        }
        lista.add(registrarEtiqueta(etiqueta));
        receta.setEtiquetas(lista);
        return true;
    }

    public Boolean deleteEtiquetaReceta(Integer posReceta, Integer posEtiqueta) throws Exception {
        Receta receta = obtenerReceta(posReceta);
        ArrayList<Etiqueta> lista = copiar(receta.getEtiquetas());
        validarPosicion(posEtiqueta, lista.size(), "de la etiqueta");
        lista.remove(posEtiqueta.intValue());
        receta.setEtiquetas(lista);
        return true;
    }

    // ------------------------------------------------------------------
    // Imágenes de la receta
    // ------------------------------------------------------------------

    public Boolean addImagenReceta(Integer posReceta, String imagen) throws Exception {
        Receta receta = obtenerReceta(posReceta);
        if (imagen == null || imagen.trim().isEmpty()) {
            throw new Exception("La ruta de la imagen no puede estar vacía.");
        }
        ArrayList<String> lista = copiar(receta.getImagenes());
        if (lista.contains(imagen.trim())) {
            throw new Exception("La receta ya tiene esa imagen.");
        }
        lista.add(imagen.trim());
        receta.setImagenes(lista);
        return true;
    }

    public Boolean deleteImagenReceta(Integer posReceta, Integer posImagen) throws Exception {
        Receta receta = obtenerReceta(posReceta);
        ArrayList<String> lista = copiar(receta.getImagenes());
        validarPosicion(posImagen, lista.size(), "de la imagen");
        lista.remove(posImagen.intValue());
        receta.setImagenes(lista);
        return true;
    }
}
