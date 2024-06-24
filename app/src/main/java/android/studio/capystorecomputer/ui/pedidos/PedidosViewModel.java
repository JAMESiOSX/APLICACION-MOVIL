package android.studio.capystorecomputer.ui.pedidos;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.List;

public class PedidosViewModel extends AndroidViewModel {

    private MutableLiveData<List<Pedido>> listaPedidos;

    public PedidosViewModel(@NonNull Application application) {
        super(application);
        listaPedidos = new MutableLiveData<>();
    }

    public LiveData<List<Pedido>> getListaPedidos() {
        return listaPedidos;
    }

    public void setListaPedidos(List<Pedido> pedidos) {
        listaPedidos.setValue(pedidos);
    }
}
