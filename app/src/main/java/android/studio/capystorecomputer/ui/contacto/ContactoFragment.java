package android.studio.capystorecomputer.ui.contacto;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.studio.capystorecomputer.databinding.FragmentContactoBinding;

public class ContactoFragment extends Fragment {

    private FragmentContactoBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        ContactoViewModel contactoViewModel =
                new ViewModelProvider(this).get(ContactoViewModel.class);

        binding = FragmentContactoBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        // Establecer textos en los TextViews
        binding.textContacto.setText("Tú eres lo más importante para nosotros");
        binding.tvCompanyName.setText("Digital Store");
        binding.tvAddress.setText("Av. Horacio #45 Piso 21, Colonia Polanco, Alcaldía Miguel Hidalgo, Ciudad de México.");
        binding.tvPhone.setText("Tel: 55-1234-5678");
        binding.tvEmail.setText("Email: contacto@digitalstore.com");
        binding.tvGithub.setText("GitHub: github.com/digitalstore");
        binding.tvFacebook.setText("Facebook: facebook.com/digitalstore");
        binding.tvInstagram.setText("Instagram: instagram.com/digitalstore");
        binding.tvTiktok.setText("TikTok: tiktok.com/@digitalstore");

        // Añadir funcionalidad de clic
        binding.tvAddress.setOnClickListener(v -> openMap("Av. Horacio #45 Piso 21, Colonia Polanco, Alcaldía Miguel Hidalgo, Ciudad de México."));
        binding.tvPhone.setOnClickListener(v -> dialPhoneNumber("55-1234-5678"));
        binding.tvEmail.setOnClickListener(v -> sendEmail("contacto@digitalstore.com"));
        binding.tvGithub.setOnClickListener(v -> openWebPage("https://github.com/digitalstore"));
        binding.tvFacebook.setOnClickListener(v -> openWebPage("https://facebook.com/digitalstore"));
        binding.tvInstagram.setOnClickListener(v -> openWebPage("https://instagram.com/digitalstore"));
        binding.tvTiktok.setOnClickListener(v -> openWebPage("https://tiktok.com/@digitalstore"));

        return root;
    }

    private void openMap(String address) {
        Uri mapUri = Uri.parse("geo:0,0?q=" + Uri.encode(address));
        Intent mapIntent = new Intent(Intent.ACTION_VIEW, mapUri);
        mapIntent.setPackage("com.google.android.apps.maps");
        if (mapIntent.resolveActivity(getActivity().getPackageManager()) != null) {
            startActivity(mapIntent);
        }
    }

    private void dialPhoneNumber(String phoneNumber) {
        Intent dialIntent = new Intent(Intent.ACTION_DIAL);
        dialIntent.setData(Uri.parse("tel:" + phoneNumber));
        startActivity(dialIntent);
    }

    private void sendEmail(String emailAddress) {
        Intent emailIntent = new Intent(Intent.ACTION_SENDTO);
        emailIntent.setData(Uri.parse("mailto:" + emailAddress));
        startActivity(emailIntent);
    }

    private void openWebPage(String url) {
        Intent webIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        startActivity(webIntent);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
