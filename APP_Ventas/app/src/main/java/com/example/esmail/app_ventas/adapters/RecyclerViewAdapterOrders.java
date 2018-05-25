package com.example.esmail.app_ventas.adapters;

import android.support.v7.widget.RecyclerView;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.esmail.app_ventas.PedidosHoraCreacion;
import com.example.esmail.app_ventas.R;

import java.util.List;

public class RecyclerViewAdapterOrders
        extends RecyclerView.Adapter
        <RecyclerViewAdapterOrders.ListItemViewHolder> {

    private List<PedidosHoraCreacion> items;
    private SparseBooleanArray selectedItems;

    public RecyclerViewAdapterOrders(List<PedidosHoraCreacion> modelData) {
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
                inflate(R.layout.estructure_lv_orders, viewGroup, false);
        return new ListItemViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ListItemViewHolder viewHolder, int position) {
        PedidosHoraCreacion PedidosHoraCreacion = items.get(position);
        viewHolder.fecha.setText(PedidosHoraCreacion.getFecha());
        viewHolder.caja.setText(PedidosHoraCreacion.getCaja());
        viewHolder.cliente.setText(PedidosHoraCreacion.getCliente());
        viewHolder.horaCreacion.setText("Creado: " + PedidosHoraCreacion.getHora_creacion());
        viewHolder.itemView.setActivated(selectedItems.get(position, false));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public final static class ListItemViewHolder extends RecyclerView.ViewHolder {
        TextView fecha, caja, cliente, horaCreacion;

        public ListItemViewHolder(View itemView) {
            super(itemView);
            fecha = (TextView) itemView.findViewById(R.id.fecha);
            caja = (TextView) itemView.findViewById(R.id.caja);
            cliente = (TextView) itemView.findViewById(R.id.cliente);
            horaCreacion = (TextView) itemView.findViewById(R.id.hora_creacion);
        }
    }
}
