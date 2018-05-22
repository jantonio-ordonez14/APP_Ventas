package com.example.esmail.app_ventas.adapters;

import android.support.v7.widget.RecyclerView;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.esmail.app_ventas.R;
import com.example.esmail.app_ventas.modelos.Pedidos;

import java.util.List;

public class RecyclerViewAdapterExport
        extends RecyclerView.Adapter
        <RecyclerViewAdapterExport.ListItemViewHolder> {

    private List<Pedidos> items;
    private SparseBooleanArray selectedItems;

    public RecyclerViewAdapterExport(List<Pedidos> modelData) {
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
                inflate(R.layout.estructure_lv_export, viewGroup, false);
        return new ListItemViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ListItemViewHolder viewHolder, int position) {
        Pedidos pedidos = items.get(position);
        viewHolder.id.setText(pedidos.getIdRegsitro());
        viewHolder.tipo.setText(pedidos.getTipo());
        viewHolder.fecha.setText(pedidos.getFecha());
        viewHolder.caja.setText(pedidos.getCaja());
        viewHolder.cliente.setText(pedidos.getCliente());
        viewHolder.articulo.setText(pedidos.getArticulo());
        viewHolder.unidades.setText(pedidos.getUnidades());
        viewHolder.itemView.setActivated(selectedItems.get(position, false));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public final static class ListItemViewHolder extends RecyclerView.ViewHolder {
        TextView id, tipo, fecha, caja, cliente, articulo, unidades;

        public ListItemViewHolder(View itemView) {
            super(itemView);
            id = (TextView) itemView.findViewById(R.id.txt_id);
            tipo = (TextView) itemView.findViewById(R.id.txt_tipo);
            fecha = (TextView) itemView.findViewById(R.id.txt_fecha);
            caja = (TextView) itemView.findViewById(R.id.txt_caja);
            cliente = (TextView) itemView.findViewById(R.id.txt_cliente);
            articulo = (TextView) itemView.findViewById(R.id.txt_articulo);
            unidades = (TextView) itemView.findViewById(R.id.txt_unidades);
        }
    }
}