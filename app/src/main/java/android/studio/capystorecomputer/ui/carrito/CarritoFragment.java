package android.studio.capystorecomputer.ui.carrito;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import android.studio.capystorecomputer.DatabaseHelper;
import android.studio.capystorecomputer.R;

public class CarritoFragment extends Fragment {

    private CarritoViewModel carritoViewModel;
    private RecyclerView recyclerView;
    private CarritoAdapter adapter;

    private TextView textTotalProductos;
    private TextView textCantidadArticulos;
    private Button buttonPagar;

    private DatabaseHelper databaseHelper; // Referencia a DatabaseHelper

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_carrito, container, false);

        carritoViewModel = new ViewModelProvider(requireActivity()).get(CarritoViewModel.class);
        databaseHelper = new DatabaseHelper(requireContext()); // Inicializa DatabaseHelper

        recyclerView = root.findViewById(R.id.recyclerViewCarrito);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new CarritoAdapter(carritoViewModel);
        recyclerView.setAdapter(adapter);

        textTotalProductos = root.findViewById(R.id.textTotalProductos);
        textCantidadArticulos = root.findViewById(R.id.textCantidadArticulos);
        buttonPagar = root.findViewById(R.id.buttonPagar);

        // Configurar el botón Pagar
        buttonPagar.setOnClickListener(v -> {
            if (carritoViewModel.getCarritoProductos().getValue() != null && !carritoViewModel.getCarritoProductos().getValue().isEmpty()) {
                // Si hay productos en el carrito, realizar el pago
                Toast.makeText(getActivity(), "Pago realizado", Toast.LENGTH_SHORT).show();
                // Guardar el pedido en MySQL utilizando PHP
                guardarPedidoEnMySQL();
                // Limpiar el carrito después de guardar el pedido
                carritoViewModel.limpiarCarrito();
            } else {
                // Si el carrito está vacío, mostrar un mensaje indicando que agregue productos al carrito
                Toast.makeText(getActivity(), "Agregue productos al carrito", Toast.LENGTH_SHORT).show();
            }
        });

        return root;
    }

    private void guardarPedidoEnMySQL() {
        // Obtener el último user_id logeado desde SharedPreferences o donde lo tengas almacenado
        int userId = obtenerUltimoUserId();

        // Obtener el detalle del pedido en formato de texto
        String detallePedido = generarDetallePedido(carritoViewModel.getCarritoProductos().getValue());

        // Crear una solicitud HTTP POST usando Volley (debes agregar la dependencia de Volley en tu archivo build.gradle)
        String url = "http://192.168.1.71/PHP/insertar_pedido.php";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Manejar la respuesta del servidor aquí (opcional)
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("success");
                            if (success) {
                                Toast.makeText(requireContext(), "Pedido guardado correctamente", Toast.LENGTH_SHORT).show();
                            } else {
                                String error = jsonResponse.getString("error");
                                Toast.makeText(requireContext(), "Error al guardar el pedido: " + error, Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(requireContext(), "Error al procesar la respuesta del servidor", Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(requireContext(), "Error de conexión", Toast.LENGTH_SHORT).show();
                    }
                }) {

            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("user_id", String.valueOf(userId));
                params.put("detalle", detallePedido);
                return params;
            }
        };

        // Agregar la solicitud a la cola de Volley (asegúrate de haber inicializado Volley antes)
        RequestQueue requestQueue = Volley.newRequestQueue(requireContext());
        requestQueue.add(stringRequest);
    }

    private int obtenerUltimoUserId() {
        // Aquí deberías implementar la lógica para obtener el último user_id logeado
        // Puedes usar SharedPreferences, base de datos local, etc.
        // Por ejemplo, podrías tener una variable global que mantenga el último user_id logeado
        // o una consulta similar a la que usaste en tu ejemplo PHP para obtener el último user_id desde la tabla conexiones
        return 1; // Por ahora devuelvo un valor estático, reemplaza con tu lógica real
    }

    private String generarDetallePedido(List<CarritoProducto> carritoProductos) {
        StringBuilder detalle = new StringBuilder();

        for (CarritoProducto producto : carritoProductos) {
            detalle.append(producto.getProducto().getNombre())
                    .append(" - Cantidad: ")
                    .append(producto.getCantidad())
                    .append("\n");
        }

        return detalle.toString();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Observar cambios en la lista de productos del carrito
        carritoViewModel.getCarritoProductos().observe(getViewLifecycleOwner(), carritoProductos -> {
            adapter.setCarritoProductos(carritoProductos);

            // Actualizar el resumen (total y cantidad de artículos)
            updateResumen(carritoProductos);
        });
    }

    private void updateResumen(List<CarritoProducto> carritoProductos) {
        double total = 0.0;
        int cantidadArticulos = 0;

        // Calcular el total y cantidad de artículos
        for (CarritoProducto producto : carritoProductos) {
            total += producto.getProducto().getPrecio() * producto.getCantidad();
            cantidadArticulos += producto.getCantidad();
        }

        // Actualizar los TextViews del resumen
        textCantidadArticulos.setText(getString(R.string.cantidad_articulos, cantidadArticulos));
        textTotalProductos.setText(getString(R.string.total_producto, total));
    }
}
