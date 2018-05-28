package com.example.esmail.app_ventas.adapters;

import android.support.v7.widget.RecyclerView;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.esmail.app_ventas.PedidosHoraCreacion;
import com.example.esmail.app_ventas.R;
import com.example.esmail.app_ventas.modelos.Exportados;

import java.util.List;

public class RecyclerViewAdapterExport
        extends RecyclerView.Adapter
        <RecyclerViewAdapterExport.ListItemViewHolder> {

    private List<PedidosHoraCreacion> items;
    private SparseBooleanArray selectedItems;

    public RecyclerViewAdapterExport(List<PedidosHoraCreacion> modelData) {
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
        PedidosHoraCreacion exportados = items.get(position);
        viewHolder.fecha.setText(exportados.getFecha());
        viewHolder.caja.setText("CAJA: "+exportados.getCaja());
        viewHolder.cliente.setText("CLIENTE: "+exportados.getCliente());
        viewHolder.itemView.setActivated(selectedItems.get(position, false));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public final static class ListItemViewHolder extends RecyclerView.ViewHolder {
        TextView fecha, caja, cliente;

        public ListItemViewHolder(View itemView) {
            super(itemView);
            fecha = (TextView) itemView.findViewById(R.id.txt_fecha);
            caja = (TextView) itemView.findViewById(R.id.txt_caja);
            cliente = (TextView) itemView.findViewById(R.id.txt_cliente);
        }
    }
}
