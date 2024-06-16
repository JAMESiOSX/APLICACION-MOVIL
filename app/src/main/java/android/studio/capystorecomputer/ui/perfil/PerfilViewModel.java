package android.studio.capystorecomputer.ui.perfil;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class PerfilViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public PerfilViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("HOLA ESTO ES PERFIL");
    }

    public LiveData<String> getText() {
        return mText;
    }
}
