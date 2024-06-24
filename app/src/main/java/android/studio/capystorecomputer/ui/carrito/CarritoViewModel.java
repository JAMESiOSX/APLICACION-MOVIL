package android.studio.capystorecomputer.ui.carrito;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import android.studio.capystorecomputer.ui.products.Producto;

import java.util.ArrayList;
import java.util.List;

public class CarritoViewModel extends ViewModel {

    private final MutableLiveData<List<CarritoProducto>> carritoProductos;

    public CarritoViewModel() {
        carritoProductos = new MutableLiveData<>(new ArrayList<>());
    }

    public LiveData<List<CarritoProducto>> getCarritoProductos() {
        return carritoProductos;
    }

    public void agregarProducto(Producto producto, int cantidad) {
        List<CarritoProducto> currentList = carritoProductos.getValue();
        if (currentList != null) {
            boolean found = false;
            for (CarritoProducto cp : currentList) {
                if (cp.getProducto().getIdProducto() == producto.getIdProducto()) {
                    cp.setCantidad(cp.getCantidad() + cantidad);
                    found = true;
                    break;
                }
            }
            if (!found) {
                currentList.add(new CarritoProducto(producto, cantidad));
            }
            carritoProductos.setValue(currentList);
        }
    }

    public void eliminarProducto(Producto producto) {
        List<CarritoProducto> currentList = carritoProductos.getValue();
        if (currentList != null) {
            currentList.removeIf(cp -> cp.getProducto().getIdProducto() == producto.getIdProducto());
            carritoProductos.setValue(currentList);
        }
    }

    public void actualizarCantidad(Producto producto, int cantidad) {
        List<CarritoProducto> currentList = carritoProductos.getValue();
        if (currentList != null) {
            for (CarritoProducto cp : currentList) {
                if (cp.getProducto().getIdProducto() == producto.getIdProducto()) {
                    cp.setCantidad(cantidad);
                    break;
                }
            }
            carritoProductos.setValue(currentList);
        }
    }

    public void limpiarCarrito() {
        List<CarritoProducto> currentList = carritoProductos.getValue();
        if (currentList != null) {
            currentList.clear();
            carritoProductos.setValue(currentList);
        }
    }
}
