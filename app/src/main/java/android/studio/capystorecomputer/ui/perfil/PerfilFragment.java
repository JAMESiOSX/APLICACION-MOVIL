package android.studio.capystorecomputer.ui.perfil;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import android.studio.capystorecomputer.databinding.FragmentPerfilBinding;

public class PerfilFragment extends Fragment {

    private FragmentPerfilBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentPerfilBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        TextView textName = binding.textName;
        TextView textLastName = binding.textLastName;
        TextView textAddress = binding.textAddress;
        TextView textEmail = binding.textEmail;
        TextView textPhone = binding.textPhone;

        // Obtener el user_id del bundle
        Bundle bundle = getArguments();
        int userId = bundle != null ? bundle.getInt("user_id", -1) : -1;

        if (userId != -1) {
            cargarDatosUsuario(textName, textLastName, textAddress, textEmail, textPhone, userId);
        } else {
            Toast.makeText(getContext(), "Error: No se recibió user_id en la respuesta", Toast.LENGTH_SHORT).show();
        }
    }

    private void cargarDatosUsuario(TextView textName, TextView textLastName, TextView textAddress, TextView textEmail, TextView textPhone, int userId) {
        String url = "http://192.168.1.71/PHP/obtener_usuario.php?user_id=" + userId;

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                response -> {
                    try {
                        JSONObject jsonResponse = new JSONObject(response);
                        if (jsonResponse.has("error")) {
                            Toast.makeText(getContext(), jsonResponse.getString("error"), Toast.LENGTH_SHORT).show();
                        } else {
                            String nombre = jsonResponse.getString("nombre");
                            String apellidos = jsonResponse.getString("apellidos");
                            String direccion = jsonResponse.getString("direccion");
                            String correo = jsonResponse.getString("correo");
                            String telefono = jsonResponse.getString("telefono");

                            textName.setText("Nombre: " + nombre);
                            textLastName.setText("Apellido: " + apellidos);
                            textAddress.setText("Dirección: " + direccion);
                            textEmail.setText("Correo: " + correo);
                            textPhone.setText("Teléfono: " + telefono);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                        Toast.makeText(getContext(), "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                },
                error -> Toast.makeText(getContext(), "Error de conexión: " + error.getMessage(), Toast.LENGTH_SHORT).show());

        RequestQueue requestQueue = Volley.newRequestQueue(requireContext());
        requestQueue.add(stringRequest);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
