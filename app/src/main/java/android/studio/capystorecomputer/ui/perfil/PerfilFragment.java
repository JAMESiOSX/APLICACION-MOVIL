package android.studio.capystorecomputer.ui.perfil;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.snackbar.Snackbar;

import org.json.JSONException;
import org.json.JSONObject;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

import android.studio.capystorecomputer.LoginActivity;
import android.studio.capystorecomputer.R;
import android.studio.capystorecomputer.databinding.FragmentPerfilBinding;

public class PerfilFragment extends Fragment {

    private FragmentPerfilBinding binding;
    private boolean isEditing = false; // Variable para controlar el estado de edición
    private int userId; // Variable para almacenar el id del usuario

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentPerfilBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        EditText editName = binding.editTextName;
        EditText editLastName = binding.editTextLastName;
        EditText editAddress = binding.editTextAddress;
        EditText editEmail = binding.editTextEmail;
        EditText editPhone = binding.editTextPhone;

        // URL del script PHP que obtiene el último id de conexiones
        String urlUltimoUserId = "http://192.168.1.71/PHP/obtenerUltimoUserId.php";

        // Crear solicitud HTTP GET usando Volley para obtener el último id
        StringRequest stringRequest = new StringRequest(Request.Method.GET, urlUltimoUserId,
                response -> {
                    try {
                        JSONObject jsonResponse = new JSONObject(response);
                        userId = jsonResponse.getInt("ultimoUserId");

                        // Verificar que se obtuvo un id válido
                        if (userId != -1) {
                            // Ahora obtener los datos del usuario desde la tabla usuarios
                            cargarDatosUsuario(editName, editLastName, editAddress, editEmail, editPhone, userId);
                        } else {
                            showErrorMessage("No se encontró un último id válido en la respuesta");
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                        showErrorMessage("Error: " + e.getMessage());
                    }
                },
                error -> showErrorMessage("Error de conexión: " + error.getMessage()));

        // Agregar la solicitud a la cola de peticiones de Volley
        RequestQueue requestQueue = Volley.newRequestQueue(requireContext());
        requestQueue.add(stringRequest);

        // Configurar botón para cerrar sesión
        Button btnLogout = view.findViewById(R.id.button_logout);
        btnLogout.setOnClickListener(v -> {
            // Implementar lógica para cerrar sesión
            startActivity(new Intent(requireActivity(), LoginActivity.class));
            requireActivity().finish();
        });

        // Configurar botón para editar perfil
        Button btnEditProfile = view.findViewById(R.id.button_edit_profile);
        btnEditProfile.setOnClickListener(v -> {
            toggleEditMode(editName, editLastName, editAddress, editEmail, editPhone);
        });

        // Configurar botón para guardar cambios
        Button btnSave = view.findViewById(R.id.button_save);
        btnSave.setOnClickListener(v -> {
            saveChanges(editName, editLastName, editAddress, editEmail, editPhone);
        });
    }

    private void toggleEditMode(EditText editName, EditText editLastName, EditText editAddress, EditText editEmail, EditText editPhone) {
        isEditing = !isEditing;

        // Habilitar o deshabilitar la edición en los EditTexts
        editName.setEnabled(isEditing);
        editLastName.setEnabled(isEditing);
        editAddress.setEnabled(isEditing);
        editEmail.setEnabled(isEditing);
        editPhone.setEnabled(isEditing);

        // Mostrar u ocultar el botón de "Guardar Cambios"
        Button btnSave = requireView().findViewById(R.id.button_save);
        btnSave.setVisibility(isEditing ? View.VISIBLE : View.GONE);
    }

    private void saveChanges(EditText editName, EditText editLastName, EditText editAddress, EditText editEmail, EditText editPhone) {
        String urlActualizarPerfil = "http://192.168.1.71/PHP/actualizarPerfil.php";

        // Crear un mapa con los parámetros a enviar
        Map<String, String> params = new HashMap<>();
        params.put("id", String.valueOf(userId));  // Usar "id" en lugar de "user_id"
        params.put("nombre", editName.getText().toString());
        params.put("apellidos", editLastName.getText().toString());
        params.put("direccion", editAddress.getText().toString());
        params.put("correo", editEmail.getText().toString());
        params.put("telefono", editPhone.getText().toString());

        // Crear la solicitud POST usando Volley
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, urlActualizarPerfil, new JSONObject(params),
                response -> {
                    try {
                        boolean success = response.getBoolean("success");
                        if (success) {
                            showSuccessMessage("Datos actualizados correctamente");
                            toggleEditMode(editName, editLastName, editAddress, editEmail, editPhone);

                        } else {
                            showErrorMessage(response.getString("message"));
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                        showErrorMessage("Error al procesar la respuesta del servidor: " + e.getMessage());
                    }
                },
                error -> {
                    if (error.networkResponse != null && error.networkResponse.data != null) {
                        String responseBody = new String(error.networkResponse.data, StandardCharsets.UTF_8);
                        Log.e("PerfilFragment", "Error de conexión: " + error.getMessage());
                        Log.e("PerfilFragment", "Respuesta del servidor: " + responseBody);
                        showErrorMessage("Error de conexión: " + error.getMessage() + "\n" + responseBody);
                    } else {
                        Log.e("PerfilFragment", "Error de conexión: " + error.getMessage());
                        showErrorMessage("Error de conexión: " + error.getMessage());
                    }
                });

        // Agregar la solicitud a la cola de peticiones de Volley
        RequestQueue requestQueue = Volley.newRequestQueue(requireContext());
        requestQueue.add(jsonObjectRequest);
    }

    private void cargarDatosUsuario(EditText editName, EditText editLastName, EditText editAddress, EditText editEmail, EditText editPhone, int userId) {
        // URL del script PHP que obtiene datos del perfil basado en user_id
        String urlDatosUsuario = "http://192.168.1.71/PHP/obtenerDatosUsuario.php?user_id=" + userId;

        // Crear solicitud HTTP GET usando Volley para obtener datos del perfil
        StringRequest stringRequest = new StringRequest(Request.Method.GET, urlDatosUsuario,
                response -> {
                    try {
                        JSONObject jsonResponse = new JSONObject(response);
                        boolean success = jsonResponse.getBoolean("success");

                        if (success) {
                            String nombre = jsonResponse.getString("nombre");
                            String apellidos = jsonResponse.getString("apellidos");
                            String direccion = jsonResponse.getString("direccion");
                            String correo = jsonResponse.getString("correo");
                            String telefono = jsonResponse.getString("telefono");

                            // Mostrar los datos del perfil en los EditTexts
                            editName.setText(nombre);
                            editLastName.setText(apellidos);
                            editAddress.setText(direccion);
                            editEmail.setText(correo);
                            editPhone.setText(telefono);
                        } else {
                            String error = jsonResponse.getString("error");
                            showErrorMessage(error);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                        showErrorMessage("Error: " + e.getMessage());
                    }
                },
                error -> {
                    showErrorMessage("Error de conexión: " + error.getMessage());
                });

        RequestQueue requestQueue = Volley.newRequestQueue(requireContext());
        requestQueue.add(stringRequest);
    }

    private void showErrorMessage(String message) {
        View rootView = requireActivity().findViewById(android.R.id.content);
        Snackbar.make(rootView, message, Snackbar.LENGTH_LONG).show();
    }

    private void showSuccessMessage(String message) {
        // Mostrar un mensaje de éxito usando un Toast
        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
