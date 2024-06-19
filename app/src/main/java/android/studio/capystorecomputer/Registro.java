package android.studio.capystorecomputer;

import android.content.Intent;
import android.os.Bundle;
import android.studio.capystorecomputer.LoginActivity;
import android.studio.capystorecomputer.R;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.HashMap;
import java.util.Map;

public class Registro extends AppCompatActivity {

    private EditText editTextName, editTextLastName, editTextAddress, editTextEmail, editTextPhone, editTextPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        editTextName = findViewById(R.id.editTextName);
        editTextLastName = findViewById(R.id.editTextLastName);
        editTextAddress = findViewById(R.id.editTextAddress);
        editTextEmail = findViewById(R.id.editTextEmail);
        editTextPhone = findViewById(R.id.editTextPhone);
        editTextPassword = findViewById(R.id.editTextPassword);
    }

    public void registrarUsuario(View view) {
        String nombre = editTextName.getText().toString().trim();
        String apellidos = editTextLastName.getText().toString().trim();
        String direccion = editTextAddress.getText().toString().trim();
        String correo = editTextEmail.getText().toString().trim();
        String telefono = editTextPhone.getText().toString().trim();
        String contrasena = editTextPassword.getText().toString().trim();

        // Validar campos vacíos
        if (nombre.isEmpty() || apellidos.isEmpty() || direccion.isEmpty() || correo.isEmpty()
                || telefono.isEmpty() || contrasena.isEmpty()) {
            Toast.makeText(this, "Todos los campos son obligatorios", Toast.LENGTH_SHORT).show();
            return; // Salir del método si hay campos vacíos
        }

        String url = "http://192.168.1.71/PHP/insertar_usuario.php";

        // Crear una solicitud POST usando Volley
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Manejar la respuesta del servidor
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("success");
                            String message = jsonResponse.getString("message");

                            Toast.makeText(Registro.this, message, Toast.LENGTH_SHORT).show();

                            if (success) {
                                // Registro exitoso, redirigir a la actividad de login
                                Intent intent = new Intent(Registro.this, LoginActivity.class);
                                startActivity(intent);
                                finish(); // Finalizar esta actividad para evitar regresar con el botón de atrás
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(Registro.this, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Manejar errores de la solicitud
                        Toast.makeText(Registro.this, "Error de conexión: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                // Construir los parámetros a enviar en la solicitud POST
                Map<String, String> params = new HashMap<>();
                params.put("nombre", nombre);
                params.put("apellidos", apellidos);
                params.put("direccion", direccion);
                params.put("correo", correo);
                params.put("telefono", telefono);
                params.put("contrasena", contrasena);
                return params;
            }

            @Override
            public Map<String, String> getHeaders() {
                // Establecer encabezados si es necesario
                Map<String, String> headers = new HashMap<>();
                headers.put("Content-Type", "application/x-www-form-urlencoded");
                return headers;
            }
        };

        // Obtener la instancia de RequestQueue y agregar la solicitud
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    public void regresaLogin(View view) {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }
}