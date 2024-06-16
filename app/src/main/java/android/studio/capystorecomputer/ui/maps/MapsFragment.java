package android.studio.capystorecomputer.ui.maps;

import android.os.Bundle;
import android.studio.capystorecomputer.databinding.FragmentMapsBinding;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.studio.capystorecomputer.databinding.FragmentMapsBinding;

public class MapsFragment extends Fragment {

    private FragmentMapsBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        MapsViewModel mapsViewModel =
                new ViewModelProvider(this).get(MapsViewModel.class);

        binding = FragmentMapsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.textSlideshow;
        mapsViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}