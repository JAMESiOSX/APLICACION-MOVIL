package android.studio.capystorecomputer.ui.evaluacion;

import android.os.Bundle;
import android.studio.capystorecomputer.R;
import android.studio.capystorecomputer.ui.evaluacion.EvaluacionDAO;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

public class EvaluacionFragment extends Fragment {

    private EvaluacionViewModel evaluacionViewModel;
    private EvaluacionDAO evaluacionDAO;

    private RatingBar ratingBarPregunta1;
    private RatingBar ratingBarPregunta2;
    private RatingBar ratingBarPregunta3;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_evaluacion, container, false);

        evaluacionViewModel = new ViewModelProvider(this).get(EvaluacionViewModel.class);
        evaluacionDAO = new EvaluacionDAO(requireContext());

        ratingBarPregunta1 = root.findViewById(R.id.ratingBarPregunta1);
        ratingBarPregunta2 = root.findViewById(R.id.ratingBarPregunta2);
        ratingBarPregunta3 = root.findViewById(R.id.ratingBarPregunta3);
        Button btnSubmitEvaluacion = root.findViewById(R.id.btnSubmitEvaluacion);

        btnSubmitEvaluacion.setOnClickListener(view -> {
            float estrellasPregunta1 = ratingBarPregunta1.getRating();
            float estrellasPregunta2 = ratingBarPregunta2.getRating();
            float estrellasPregunta3 = ratingBarPregunta3.getRating();

            evaluacionDAO.insertarEvaluacion(1, (int) estrellasPregunta1, (int) estrellasPregunta2, (int) estrellasPregunta3);
            Toast.makeText(requireContext(), "Evaluación guardada", Toast.LENGTH_SHORT).show();

            // Resetear los RatingBars
            ratingBarPregunta1.setRating(0);
            ratingBarPregunta2.setRating(0);
            ratingBarPregunta3.setRating(0);
        });

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        evaluacionDAO.cerrar();
    }
}
