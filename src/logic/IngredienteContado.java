package logic;

public class IngredienteContado extends Ingrediente {
    private String cantidad;

    public IngredienteContado(String nombre, String cantidad) throws Exception {
        super(nombre);
        if (cantidad.equals("") || cantidad == null)
            throw new Exception("La cantidad no puede estar vacia");
        this.cantidad = cantidad;
    }

    public String getCantidad() {
        return cantidad;
    }

    public void setCantidad(String cantidad) throws Exception {
        if (cantidad.equals("") || cantidad == null)
            throw new Exception("La cantidad no puede estar vacia");
        this.cantidad = cantidad;
    }
}
