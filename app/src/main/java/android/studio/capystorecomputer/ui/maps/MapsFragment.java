package android.studio.capystorecomputer.ui.maps;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import android.studio.capystorecomputer.R;

public class MapsFragment extends Fragment implements OnMapReadyCallback {

    private GoogleMap mMap;
    private MapView mapView;
    private CardView cardView;
    private TextView tvCompanyName, tvPhone, tvDescription, tvEmail;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_maps, container, false);

        cardView = rootView.findViewById(R.id.card_view);
        tvCompanyName = rootView.findViewById(R.id.tv_company_name);
        tvPhone = rootView.findViewById(R.id.tv_phone);
        tvDescription = rootView.findViewById(R.id.tv_description);
        tvEmail = rootView.findViewById(R.id.tv_email);

        mapView = rootView.findViewById(R.id.mapView);
        mapView.onCreate(savedInstanceState);
        mapView.onResume(); // needed to get the map to display immediately
        mapView.getMapAsync(this);

        MapsInitializer.initialize(requireActivity().getApplicationContext());

        return rootView;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Enable UI controls
        mMap.getUiSettings().setCompassEnabled(true);
        mMap.getUiSettings().setMyLocationButtonEnabled(true);
        mMap.getUiSettings().setZoomControlsEnabled(true); // Enable zoom controls

        // Coordenadas de las sucursales
        LatLng sucursal1 = new LatLng(19.437142, -99.192252);
        LatLng sucursal2 = new LatLng(19.447437, -99.172527);
        LatLng sucursal3 = new LatLng(19.295233, -98.834375);
        LatLng sucursal4 = new LatLng(19.361585, -99.195566);

        // Cargar el icono y ajustar su tamaño
        Bitmap originalBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.empresa);
        Bitmap scaledBitmap = Bitmap.createScaledBitmap(originalBitmap, 96, 96, false); // Ajustar tamaño a 96x96 píxeles
        BitmapDescriptor icon = BitmapDescriptorFactory.fromBitmap(scaledBitmap);

        // Agregar marcadores en el mapa con icono personalizado ajustado
        mMap.addMarker(new MarkerOptions()
                .position(sucursal1)
                .title("DigitalStore - Sucursal 1")
                .icon(icon));

        mMap.addMarker(new MarkerOptions()
                .position(sucursal2)
                .title("DigitalStore - Sucursal 2")
                .icon(icon));

        mMap.addMarker(new MarkerOptions()
                .position(sucursal3)
                .title("DigitalStore - Sucursal 3")
                .icon(icon));

        mMap.addMarker(new MarkerOptions()
                .position(sucursal4)
                .title("DigitalStore - Sucursal 4")
                .icon(icon));

        // Liberar la memoria del bitmap original
        originalBitmap.recycle();

        // Mover la cámara a la primera sucursal
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(sucursal1, 15));

        mMap.setOnMarkerClickListener(marker -> {
            // Mostrar información de la sucursal
            cardView.setVisibility(View.VISIBLE);
            tvCompanyName.setText(marker.getTitle());
            tvPhone.setText("Tel: 55-1234-5678");
            tvDescription.setText("Empresa dedicada a la venta de productos digitales.");
            tvEmail.setText("Email: contacto@digitalstore.com");
            return false;
        });

        // Enable location layer
        if (ActivityCompat.checkSelfPermission(requireActivity(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED ||
                ActivityCompat.checkSelfPermission(requireActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            mMap.setMyLocationEnabled(true);
        } else {
            // Request location permissions if not granted
            ActivityCompat.requestPermissions(requireActivity(),
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION},
                    1);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }
}
