package com.example.esmail.app_ventas.adapters;

import android.support.v7.widget.RecyclerView;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.esmail.app_ventas.R;
import com.example.esmail.app_ventas.modelos.Articulo;

import java.util.List;

public class RecyclerViewAdapterProducts
        extends RecyclerView.Adapter
        <RecyclerViewAdapterProducts.ListItemViewHolder> {

    private List<Articulo> items;
    private SparseBooleanArray selectedItems;

    public RecyclerViewAdapterProducts(List<Articulo> modelData) {
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
                inflate(R.layout.estructure_lv_products, viewGroup, false);
        return new ListItemViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ListItemViewHolder viewHolder, int position) {
        Articulo articulo = items.get(position);
        viewHolder.codArticulo.setText(articulo.getCod_articulo());
        viewHolder.codBarras.setText(articulo.getCod_barras());
        viewHolder.descripcion.setText(articulo.getDescripcion());
        viewHolder.unidades.setText(articulo.getUnidades());
        viewHolder.itemView.setActivated(selectedItems.get(position, false));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public final static class ListItemViewHolder extends RecyclerView.ViewHolder {
        TextView codArticulo;
        TextView codBarras;
        TextView descripcion;
        TextView unidades;
        TextView precio;
        TextView importe;

        public ListItemViewHolder(View itemView) {
            super(itemView);
            codArticulo = (TextView) itemView.findViewById(R.id.txt_cod_erp);
            codBarras = (TextView) itemView.findViewById(R.id.txt_cod_barras);
            descripcion = (TextView) itemView.findViewById(R.id.txt_descripcion);
            unidades = (TextView) itemView.findViewById(R.id.txt_cantidad);
        }
    }
}
