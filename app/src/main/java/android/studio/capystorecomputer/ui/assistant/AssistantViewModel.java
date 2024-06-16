package android.studio.capystorecomputer.ui.assistant;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class AssistantViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public AssistantViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("HOLA ESTO ES EL ASISTENTE");
    }

    public LiveData<String> getText() {
        return mText;
    }
}
