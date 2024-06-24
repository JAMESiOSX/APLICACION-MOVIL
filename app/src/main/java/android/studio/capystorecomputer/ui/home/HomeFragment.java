package android.studio.capystorecomputer.ui.home;

import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import java.util.ArrayList;
import java.util.List;

import android.studio.capystorecomputer.R;


public class HomeFragment extends Fragment {

    private ViewPager viewPager;
    private SliderPagerAdapter sliderPagerAdapter;
    private List<Integer> imageList = new ArrayList<>();
    private int currentPage = 0;
    private Handler handler;
    private final int delay = 2000; // Tiempo en milisegundos entre cada cambio de página

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_home, container, false);
        viewPager = root.findViewById(R.id.viewPager);

        // Agregar imágenes al ViewPager
        imageList.add(R.drawable.imagen1);
        imageList.add(R.drawable.imagen2);
        imageList.add(R.drawable.imagen3);
        imageList.add(R.drawable.imagen4);
        imageList.add(R.drawable.imagen5);

        // Configurar adaptador para el ViewPager
        sliderPagerAdapter = new SliderPagerAdapter(getContext(), imageList);
        viewPager.setAdapter(sliderPagerAdapter);

        // Iniciar el slide automático
        handler = new Handler();
        startSlide();

        return root;
    }

    // Método para iniciar el slide automático
    private void startSlide() {
        handler.postDelayed(runnable, delay);
    }

    // Runnable para manejar el cambio de páginas automáticamente
    private Runnable runnable = new Runnable() {
        public void run() {
            if (sliderPagerAdapter != null && viewPager != null) {
                currentPage = viewPager.getCurrentItem();
                currentPage = (currentPage + 1) % imageList.size();
                viewPager.setCurrentItem(currentPage, true);
                handler.postDelayed(this, delay);
            }
        }
    };

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        // Detener el slide automático cuando se destruye la vista
        handler.removeCallbacks(runnable);
    }
}
