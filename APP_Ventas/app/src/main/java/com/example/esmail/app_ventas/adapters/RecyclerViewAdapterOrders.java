package com.example.esmail.app_ventas.adapters;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.esmail.app_ventas.PedidosHoraCreacion;
import com.example.esmail.app_ventas.R;

import java.util.LinkedList;
import java.util.List;

public class RecyclerViewAdapterOrders
        extends RecyclerView.Adapter
        <RecyclerViewAdapterOrders.ListItemViewHolder> {

    private List<PedidosHoraCreacion> items;
    private SparseBooleanArray seleccionados;
    private boolean modoSeleccion;

    public RecyclerViewAdapterOrders(List<PedidosHoraCreacion> modelData) {
        if (modelData == null) {
            throw new IllegalArgumentException("modelData must not be null");
        }
        items = modelData;
        seleccionados = new SparseBooleanArray();
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

        viewHolder.itemView.setActivated(seleccionados.get(position, false));


    }


    @Override
    public int getItemCount() {
        return items.size();
    }

    public final class ListItemViewHolder extends RecyclerView.ViewHolder {
        TextView fecha, caja, cliente, horaCreacion;


        public ListItemViewHolder(View itemView) {
            super(itemView);
            fecha = (TextView) itemView.findViewById(R.id.fecha);
            caja = (TextView) itemView.findViewById(R.id.caja);
            cliente = (TextView) itemView.findViewById(R.id.cliente);
            horaCreacion = (TextView) itemView.findViewById(R.id.hora_creacion);


            //Selecciona el objeto si estaba seleccionado
            if (seleccionados.get(getAdapterPosition())) {
                itemView.setSelected(true);
            } else
                itemView.setSelected(false);

            /**Activa el modo de selección*/
            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    if (!modoSeleccion) {
                        modoSeleccion = true;
                        v.setSelected(true);
                        v.setBackgroundColor(Color.RED);
                        seleccionados.put(getAdapterPosition(), true);
                    }

                    return true;
                }
            });

            /**Selecciona/deselecciona un ítem si está activado el modo selección*/
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (modoSeleccion) {
                        if (!v.isSelected()) {
                            v.setSelected(true);
                            v.setBackgroundColor(Color.RED);
                            seleccionados.put(getAdapterPosition(), true);
                        } else {
                            v.setSelected(false);
                            v.setBackgroundColor(Color.WHITE);
                            seleccionados.put(getAdapterPosition(), false);
                            if (!haySeleccionados())
                                modoSeleccion = false;
                        }
                    }
                }
            });
        }
    }

    public boolean haySeleccionados() {
        for (int i = 0; i <= items.size(); i++) {
            if (seleccionados.get(i))
                return true;
        }
        return false;
    }

    /**
     * Devuelve aquellos objetos marcados.
     */
    public LinkedList<PedidosHoraCreacion> obtenerSeleccionados() {
        LinkedList<PedidosHoraCreacion> marcados = new LinkedList<>();
        for (int i = 0; i < items.size(); i++) {
            if (seleccionados.get(i)) {
                marcados.add(items.get(i));
            }
        }
        return marcados;
    }
}


