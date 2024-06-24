package android.studio.capystorecomputer.ui.pedidos;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import android.studio.capystorecomputer.R;

public class PedidosFragment extends Fragment {

    private RecyclerView recyclerView;
    private PedidosAdapter adapter;
    private List<Pedido> listaPedidos;
    private PedidosViewModel pedidosViewModel;
    private RequestQueue requestQueue;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_pedidos, container, false);

        recyclerView = root.findViewById(R.id.recyclerViewPedidos);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        listaPedidos = new ArrayList<>();
        adapter = new PedidosAdapter(listaPedidos);
        recyclerView.setAdapter(adapter);

        pedidosViewModel = new ViewModelProvider(this).get(PedidosViewModel.class);

        requestQueue = Volley.newRequestQueue(requireContext());

        obtenerUserIdYMostrarPedidos();

        return root;
    }

    private void obtenerUserIdYMostrarPedidos() {
        String urlUltimoUserId = "http://192.168.1.71/PHP/obtenerUltimoUserId.php";

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, urlUltimoUserId, null,
                response -> {
                    try {
                        boolean success = response.getBoolean("success");
                        if (success) {
                            int userId = response.getInt("ultimoUserId");
                            // Almacenar el userId en SharedPreferences
                            guardarUserIdEnSharedPreferences(userId);
                            // Obtener los pedidos para este userId
                            obtenerPedidos(userId);
                        } else {
                            String errorMessage = response.getString("error");
                            Toast.makeText(requireContext(), errorMessage, Toast.LENGTH_SHORT).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                        Toast.makeText(requireContext(), "Error al procesar la respuesta del servidor", Toast.LENGTH_SHORT).show();
                    }
                },
                error -> {
                    String errorMessage = "Error de conexión";
                    if (error.networkResponse != null) {
                        errorMessage += ": " + error.networkResponse.statusCode;
                    }
                    Toast.makeText(requireContext(), errorMessage, Toast.LENGTH_SHORT).show();
                    Log.e("Volley Error", "Error: " + error.toString());
                });

        requestQueue.add(jsonObjectRequest);
    }

    private void obtenerPedidos(int userId) {
        String url = "http://192.168.1.71/PHP/obtener_pedidos.php?user_id=" + userId;

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                response -> {
                    try {
                        boolean success = response.getBoolean("success");
                        if (success) {
                            JSONArray pedidosArray = response.getJSONArray("pedidos");
                            listaPedidos.clear();
                            for (int i = 0; i < pedidosArray.length(); i++) {
                                JSONObject pedidoObj = pedidosArray.getJSONObject(i);
                                int id = pedidoObj.getInt("id");
                                String fecha = pedidoObj.getString("fecha");
                                String detalle = pedidoObj.getString("detalle");
                                Pedido pedido = new Pedido(id, fecha, detalle);
                                listaPedidos.add(pedido);
                            }
                            adapter.notifyDataSetChanged();
                        } else {
                            String errorMessage = "No se encontraron pedidos para este usuario";
                            Toast.makeText(requireContext(), errorMessage, Toast.LENGTH_SHORT).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                        Toast.makeText(requireContext(), "Error al procesar la respuesta del servidor", Toast.LENGTH_SHORT).show();
                    }
                },
                error -> {
                    String errorMessage = "Error de conexión";
                    if (error.networkResponse != null) {
                        errorMessage += ": " + error.networkResponse.statusCode;
                    }
                    Toast.makeText(requireContext(), errorMessage, Toast.LENGTH_SHORT).show();
                    Log.e("Volley Error", "Error: " + error.toString());
                });

        requestQueue.add(jsonObjectRequest);
    }

    private void guardarUserIdEnSharedPreferences(int userId) {
        SharedPreferences sharedPreferences = requireContext().getSharedPreferences("myPrefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("user_id", userId);
        editor.apply();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (requestQueue != null) {
            requestQueue.cancelAll(this);
        }
    }
}
