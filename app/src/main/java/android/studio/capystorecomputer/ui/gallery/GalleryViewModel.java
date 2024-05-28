package android.studio.capystorecomputer.ui.gallery;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class GalleryViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public GalleryViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("HOLA, AQUI VAN LOS PRODUCTOS DEL MONGO XD");
    }

    public LiveData<String> getText() {
        return mText;
    }
}