package android.studio.capystorecomputer.ui.assistant;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.studio.capystorecomputer.R;

public class AssistantFragment extends Fragment {

    private WebView webView;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_assistant, container, false);
        webView = root.findViewById(R.id.webview);

        // Habilitar JavaScript (si es necesario)
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);

        // Cargar la URL del chatbot (asegúrate de usar la IP correcta)
        String serverUrl = "http://192.168.1.71:5000"; // Reemplaza con la dirección IP local de tu servidor Flask
        webView.loadUrl(serverUrl);

        // Configurar un WebViewClient para navegar dentro del WebView
        webView.setWebViewClient(new WebViewClient());

        return root;
    }
}
