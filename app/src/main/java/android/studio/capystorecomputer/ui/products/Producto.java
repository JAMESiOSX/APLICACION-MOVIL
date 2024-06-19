package android.studio.capystorecomputer.ui.products;

public class Producto {
    private int idProducto;
    private String nombre;
    private double precio;
    private String imagen;
    private String descripcion;

    public Producto(int idProducto, String nombre, double precio, String imagen, String descripcion) {
        this.idProducto = idProducto;
        this.nombre = nombre;
        this.precio = precio;
        this.imagen = imagen;
        this.descripcion = descripcion;
    }

    public int getIdProducto() {
        return idProducto;
    }

    public String getNombre() {
        return nombre;
    }

    public double getPrecio() {
        return precio;
    }

    public String getImagen() {
        return imagen;
    }

    public String getDescripcion() {
        return descripcion;
    }
}
