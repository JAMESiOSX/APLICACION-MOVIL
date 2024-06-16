package android.studio.capystorecomputer;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;

import android.studio.capystorecomputer.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    private AppBarConfiguration mAppBarConfiguration;
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.appBarMain.toolbar);
        binding.appBarMain.fab.setOnClickListener(view ->
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show());

        DrawerLayout drawerLayout = binding.drawerLayout;
        NavigationView navigationView = binding.navView;

        // Configure the AppBarConfiguration with the correct top-level destinations
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_products, R.id.nav_assistant,
                R.id.nav_sucursales, R.id.nav_contacto, R.id.nav_perfil)
                .setOpenableLayout(drawerLayout)
                .build();

        // Initialize NavController
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);

        // Setup the ActionBar with NavController and AppBarConfiguration
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);

        // Setup NavigationView with NavController for handling item clicks
        NavigationUI.setupWithNavController(navigationView, navController);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);

        int itemId = item.getItemId();
        if (itemId == R.id.nav_home || itemId == R.id.nav_home ||
                itemId == R.id.nav_assistant || itemId == R.id.nav_assistant ||
                itemId == R.id.nav_contacto || itemId == R.id.nav_contacto ||
                itemId == R.id.nav_perfil || itemId == R.id.nav_perfil ||
                itemId == R.id.nav_products || itemId == R.id.nav_products ||
                itemId == R.id.nav_sucursales || itemId == R.id.nav_sucursales) {
            return NavigationUI.onNavDestinationSelected(item, navController);
        }

        return super.onOptionsItemSelected(item);
    }
}
