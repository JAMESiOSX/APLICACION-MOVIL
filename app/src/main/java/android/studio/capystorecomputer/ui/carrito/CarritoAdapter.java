package android.studio.capystorecomputer.ui.carrito;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import android.studio.capystorecomputer.databinding.ItemCarritoBinding;
import android.studio.capystorecomputer.ui.products.Producto;

import com.bumptech.glide.Glide;

public class CarritoAdapter extends RecyclerView.Adapter<CarritoAdapter.CarritoViewHolder> {

    private List<CarritoProducto> carritoProductos;
    private CarritoViewModel carritoViewModel;

    public CarritoAdapter(CarritoViewModel carritoViewModel) {
        this.carritoViewModel = carritoViewModel;
    }

    public void setCarritoProductos(List<CarritoProducto> carritoProductos) {
        this.carritoProductos = carritoProductos;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public CarritoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        ItemCarritoBinding binding = ItemCarritoBinding.inflate(inflater, parent, false);
        return new CarritoViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull CarritoViewHolder holder, int position) {
        CarritoProducto carritoProducto = carritoProductos.get(position);
        holder.bind(carritoProducto);
    }

    @Override
    public int getItemCount() {
        return carritoProductos != null ? carritoProductos.size() : 0;
    }

    class CarritoViewHolder extends RecyclerView.ViewHolder {

        private ItemCarritoBinding binding;

        public CarritoViewHolder(ItemCarritoBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(CarritoProducto carritoProducto) {
            Producto product = carritoProducto.getProducto();
            binding.nombreProductoCarrito.setText(product.getNombre());
            binding.precioProductoCarrito.setText("Precio: $" + product.getPrecio());
            binding.cantidadProductoCarrito.setText(String.valueOf(carritoProducto.getCantidad()));

            Glide.with(binding.imagenProductoCarrito.getContext())
                    .load(product.getImagen()) // Reemplaza con la URL de la imagen del producto
                    .into(binding.imagenProductoCarrito);

            binding.buttonEliminarProducto.setOnClickListener(v -> {
                carritoViewModel.eliminarProducto(product);
                Toast.makeText(binding.getRoot().getContext(), product.getNombre() + " eliminado del carrito", Toast.LENGTH_SHORT).show();
            });

            binding.buttonAumentarCantidad.setOnClickListener(v -> {
                carritoViewModel.actualizarCantidad(product, carritoProducto.getCantidad() + 1);
            });

            binding.buttonDisminuirCantidad.setOnClickListener(v -> {
                if (carritoProducto.getCantidad() > 1) {
                    carritoViewModel.actualizarCantidad(product, carritoProducto.getCantidad() - 1);
                } else {
                    Toast.makeText(binding.getRoot().getContext(), "La cantidad mínima es 1", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}
