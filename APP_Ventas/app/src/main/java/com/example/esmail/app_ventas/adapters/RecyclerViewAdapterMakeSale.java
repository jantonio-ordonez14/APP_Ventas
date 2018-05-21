package com.example.esmail.app_ventas.adapters;

import android.support.v7.widget.RecyclerView;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.esmail.app_ventas.R;
import com.example.esmail.app_ventas.modelos.DetallePedido;

import java.util.List;

public class RecyclerViewAdapterMakeSale
        extends RecyclerView.Adapter
        <RecyclerViewAdapterMakeSale.ListItemViewHolder> {

    private List<DetallePedido> items;
    private SparseBooleanArray selectedItems;

    public RecyclerViewAdapterMakeSale(List<DetallePedido> modelData) {
        if (modelData == null) {
            throw new IllegalArgumentException("modelData must not be null");
        }
        items = modelData;
        selectedItems = new SparseBooleanArray();
    }

    @Override
    public ListItemViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View itemView = LayoutInflater.
                from(viewGroup.getContext()).
                inflate(R.layout.estructure_lv_make, viewGroup, false);
        return new ListItemViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ListItemViewHolder viewHolder, int position) {
        DetallePedido detallePedido = items.get(position);
        viewHolder.articulo.setText(detallePedido.getArticulo());
        viewHolder.unidades.setText(detallePedido.getUnidades());

        viewHolder.itemView.setActivated(selectedItems.get(position, false));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public final static class ListItemViewHolder extends RecyclerView.ViewHolder {
        TextView articulo;
        TextView unidades;

        public ListItemViewHolder(View itemView) {
            super(itemView);
            articulo = (TextView) itemView.findViewById(R.id.titulo);
            unidades = (TextView) itemView.findViewById(R.id.subtitulo);
        }
    }
}
