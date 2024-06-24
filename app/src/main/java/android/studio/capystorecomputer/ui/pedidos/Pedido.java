// Pedido.java
package android.studio.capystorecomputer.ui.pedidos;

public class Pedido {
    private int id;
    private String fecha;
    private String detalle;

    public Pedido(int id, String fecha, String detalle) {
        this.id = id;
        this.fecha = fecha;
        this.detalle = detalle;
    }

    public int getId() {
        return id;
    }

    public String getFecha() {
        return fecha;
    }

    public String getDetalle() {
        return detalle;
    }
}
