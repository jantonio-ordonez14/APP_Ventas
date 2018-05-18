package com.example.esmail.app_ventas.adapters;

import android.support.v7.widget.RecyclerView;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.esmail.app_ventas.R;
import com.example.esmail.app_ventas.modelos.Cliente;

import java.util.List;

public class RecyclerViewAdapterCustomers
        extends RecyclerView.Adapter
        <RecyclerViewAdapterCustomers.ListItemViewHolder> {

    private List<Cliente> items;
    private SparseBooleanArray selectedItems;

    public RecyclerViewAdapterCustomers(List<Cliente> modelData) {
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
                inflate(R.layout.estructure_lv_customers, viewGroup, false);
        return new ListItemViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ListItemViewHolder viewHolder, int position) {
        Cliente cliente = items.get(position);
        viewHolder.name.setText(cliente.getCod_articulo());
        viewHolder.age.setText(cliente.getNombre());
        viewHolder.itemView.setActivated(selectedItems.get(position, false));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public final static class ListItemViewHolder extends RecyclerView.ViewHolder {
        TextView name;
        TextView age;

        public ListItemViewHolder(View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.txt_titulo);
            age = (TextView) itemView.findViewById(R.id.txt_subtitulo);
        }
    }
}
