package android.studio.capystorecomputer.ui.pedidos;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import android.studio.capystorecomputer.R;

public class PedidosAdapter extends RecyclerView.Adapter<PedidosAdapter.PedidoViewHolder> {

    private List<Pedido> listaPedidos;

    public PedidosAdapter(List<Pedido> listaPedidos) {
        this.listaPedidos = listaPedidos;
    }

    @NonNull
    @Override
    public PedidoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_pedido, parent, false);
        return new PedidoViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull PedidoViewHolder holder, int position) {
        Pedido pedido = listaPedidos.get(position);
        holder.bind(pedido);
    }

    @Override
    public int getItemCount() {
        return listaPedidos.size();
    }

    public class PedidoViewHolder extends RecyclerView.ViewHolder {

        private TextView textId;
        private TextView textFecha;
        private TextView textDetalle;

        public PedidoViewHolder(@NonNull View itemView) {
            super(itemView);
            textId = itemView.findViewById(R.id.textId);
            textFecha = itemView.findViewById(R.id.textFecha);
            textDetalle = itemView.findViewById(R.id.textDetalle);
        }

        public void bind(Pedido pedido) {
            textId.setText("ID: " + pedido.getId());
            textFecha.setText("Fecha: " + pedido.getFecha());
            textDetalle.setText("Detalle: " + pedido.getDetalle());
        }
    }
}
