package android.studio.capystorecomputer.ui.carrito;

import android.studio.capystorecomputer.ui.products.Producto;

public class CarritoProducto {
    private Producto producto;
    private int cantidad;

    public CarritoProducto(Producto producto, int cantidad) {
        this.producto = producto;
        this.cantidad = cantidad;
    }

    public Producto getProducto() {
        return producto;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }
}
