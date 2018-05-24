package com.example.esmail.app_ventas.adapters;

import android.support.v7.widget.RecyclerView;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.example.esmail.app_ventas.R;
import com.example.esmail.app_ventas.modelos.Cliente;

import java.util.ArrayList;
import java.util.List;

public class RecyclerViewAdapterFilterCustomers extends RecyclerView.Adapter
        <com.example.esmail.app_ventas.adapters.RecyclerViewAdapterCustomers.ListItemViewHolder>
        implements Filterable, View.OnClickListener{

    private List<Cliente> items;
    private ArrayList<Cliente> itemsFilters;
    private CustomFilter mFilter;
    private SparseBooleanArray selectedItems;
    private View.OnClickListener listener;

    public RecyclerViewAdapterFilterCustomers(List<Cliente> modelData) {
        if (modelData == null) {
            throw new IllegalArgumentException("modelData must not be null");
        }
        items = modelData;
        this.itemsFilters = new ArrayList();
        this.itemsFilters.addAll(items);
        this.mFilter = new CustomFilter(RecyclerViewAdapterFilterCustomers.this);
        selectedItems = new SparseBooleanArray();
    }

    @Override
    public com.example.esmail.app_ventas.adapters.RecyclerViewAdapterCustomers.ListItemViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View itemView = LayoutInflater.
                from(viewGroup.getContext()).
                inflate(R.layout.estructure_lv_customers, viewGroup, false);
        itemView.setOnClickListener(this);
        return new com.example.esmail.app_ventas.adapters.RecyclerViewAdapterCustomers.ListItemViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(com.example.esmail.app_ventas.adapters.RecyclerViewAdapterCustomers.ListItemViewHolder viewHolder, int position) {
        Cliente cliente = itemsFilters.get(position);
        viewHolder.name.setText(cliente.getNombre());
        viewHolder.age.setText(cliente.getCod_articulo());
        viewHolder.itemView.setActivated(selectedItems.get(position, false));
    }

    @Override
    public int getItemCount() {
        return itemsFilters.size();
    }

    @Override
    public Filter getFilter() {
        return mFilter;
    }
    public void setOnClickListener(View.OnClickListener listener) {
        this.listener = listener;
    }
    @Override
    public void onClick(View v) {
        if(listener != null)
            listener.onClick(v);
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

    public class CustomFilter extends Filter {
        private RecyclerViewAdapterFilterCustomers RecyclerViewAdapterFilterCustomers;

        private CustomFilter(RecyclerViewAdapterFilterCustomers RecyclerViewAdapterFilterCustomers) {
            super();
            this.RecyclerViewAdapterFilterCustomers = RecyclerViewAdapterFilterCustomers;
        }

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            itemsFilters.clear();
            final FilterResults results = new FilterResults();
            if (constraint.length() == 0) {
                itemsFilters.addAll(items);
            } else {
                final String filterPattern = constraint.toString().toLowerCase().trim();
                for (final Cliente cliente : items) {
                    if (cliente.getNombre().toLowerCase().contains(filterPattern)) {
                        itemsFilters.add(cliente);
                    }
                }
            }
            results.values = itemsFilters;
            results.count = itemsFilters.size();
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            this.RecyclerViewAdapterFilterCustomers.notifyDataSetChanged();
        }
    }
}


