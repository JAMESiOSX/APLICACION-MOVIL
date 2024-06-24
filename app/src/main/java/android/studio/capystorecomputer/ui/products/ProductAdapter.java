package android.studio.capystorecomputer.ui.products;

import android.content.Context;
import android.studio.capystorecomputer.R;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;

import java.util.List;

import android.studio.capystorecomputer.databinding.ItemProductoBinding;
import android.studio.capystorecomputer.ui.carrito.CarritoViewModel;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductViewHolder> {

    private List<Producto> productList;
    private Context context;
    private CarritoViewModel carritoViewModel;

    public ProductAdapter(Context context, List<Producto> productList, CarritoViewModel carritoViewModel) {
        this.context = context;
        this.productList = productList;
        this.carritoViewModel = carritoViewModel;
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        ItemProductoBinding binding = ItemProductoBinding.inflate(inflater, parent, false);
        return new ProductViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        Producto product = productList.get(position);
        holder.bind(product);
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    class ProductViewHolder extends RecyclerView.ViewHolder {

        private ItemProductoBinding binding;
        private int quantity = 0;

        public ProductViewHolder(ItemProductoBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(Producto product) {
            binding.nombreProducto.setText(product.getNombre());
            binding.precioProducto.setText("Precio: $" + product.getPrecio());
            binding.descripcionProducto.setText(product.getDescripcion());

            // Cargar imagen utilizando Glide
            Glide.with(binding.getRoot().getContext())
                    .load(product.getImagen())
                    .apply(new RequestOptions()
                            .placeholder(R.drawable.procesador) // Imagen de carga mientras se carga la imagen
                            .error(R.drawable.notificacion) // Imagen de error si no se puede cargar la imagen
                            .diskCacheStrategy(DiskCacheStrategy.ALL)) // Cachear la imagen
                    .into(binding.imageProducto);

            binding.buttonPlus.setOnClickListener(v -> {
                quantity++;
                binding.textQuantity.setText(String.valueOf(quantity));
            });

            binding.buttonMinus.setOnClickListener(v -> {
                if (quantity > 0) {
                    quantity--;
                    binding.textQuantity.setText(String.valueOf(quantity));
                }
            });

            binding.buttonAddToCart.setOnClickListener(v -> {
                if (quantity > 0) {
                    carritoViewModel.agregarProducto(product, quantity);
                    Toast.makeText(context, product.getNombre() + " agregado al carrito", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(context, "Seleccione una cantidad", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}
