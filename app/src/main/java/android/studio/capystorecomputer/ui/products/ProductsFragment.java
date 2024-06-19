package android.studio.capystorecomputer.ui.products;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import android.studio.capystorecomputer.R;

public class ProductsFragment extends Fragment {

    private RecyclerView recyclerView;
    private ProductAdapter adapter;
    private List<Producto> productList;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_products, container, false);

        recyclerView = root.findViewById(R.id.recyclerViewProducts);
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));

        productList = new ArrayList<>();
        adapter = new ProductAdapter(getActivity(), productList);
        recyclerView.setAdapter(adapter);

        // Llamar al método para cargar los productos desde el servidor
        loadProducts();

        return root;
    }

    private void loadProducts() {
        String url = "http://192.168.1.71/PHP/consulta_productos.php"; // Reemplaza con la URL de tu script PHP

        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null,
                response -> {
                    try {
                        productList.clear();

                        for (int i = 0; i < response.length(); i++) {
                            JSONObject productObject = response.getJSONObject(i);
                            int id = productObject.getInt("id_producto");
                            String nombre = productObject.getString("nombre");
                            double precio = productObject.getDouble("precio");
                            String imagen = productObject.getString("imagen");
                            String descripcion = productObject.getString("descripcion");

                            // Añadir producto a la lista
                            Producto producto = new Producto(id, nombre, precio, imagen, descripcion);
                            productList.add(producto);
                        }

                        adapter.notifyDataSetChanged();

                    } catch (JSONException e) {
                        e.printStackTrace();
                        Toast.makeText(getActivity(), "Error al procesar JSON: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                },
                error -> {
                    error.printStackTrace();
                    Toast.makeText(getActivity(), "Error de red: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                });

        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(request);
    }
}
