package android.studio.capystorecomputer;

import android.content.Intent;
import android.os.Bundle;
import android.studio.capystorecomputer.R;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Encuentra la vista del botón por su ID
        Button buttonLogin = findViewById(R.id.buttonLogin);

        // Asigna el listener de clic al botón
        buttonLogin.setOnClickListener(v -> {
            // Aquí puedes poner el código que se ejecutará cuando se haga clic en el botón
            // Por ejemplo, iniciar sesión
            iniciarSesion();
        });
    }

    private void iniciarSesion() {
        // Aquí puedes agregar el código para iniciar sesión
    }

    // Método asociado al onClick del botón de registro
    public void Registrate(View view) {
        // Aquí iniciamos la actividad de registro
        Intent intent = new Intent(this, Registro.class);
        startActivity(intent);
    }
}
