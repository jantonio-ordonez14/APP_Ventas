package com.example.esmail.app_ventas.fragments;

import android.app.Fragment;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.esmail.app_ventas.PedidosHoraCreacion;
import com.example.esmail.app_ventas.R;
import com.example.esmail.app_ventas.adapters.RecyclerViewAdapterCustomers;
import com.example.esmail.app_ventas.adapters.RecyclerViewAdapterMakeSale;
import com.example.esmail.app_ventas.adapters.RecyclerViewAdapterOrders;
import com.example.esmail.app_ventas.modelos.Articulo;
import com.example.esmail.app_ventas.modelos.CabeceraPedido;
import com.example.esmail.app_ventas.sqlite.DatabaseOperations;

import java.util.ArrayList;
import java.util.List;

public class OrdersFragment extends Fragment {

    private RecyclerView rv;
    private List<PedidosHoraCreacion> pedidos;
    private RecyclerViewAdapterOrders adapter;


    public OrdersFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_orders, container, false);

        rv = v.findViewById(R.id.rv_orders);
        pedidos = new ArrayList<>();
        return v;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        rv.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        rv.setLayoutManager(llm);

        //adaptador
        adapter = new RecyclerViewAdapterOrders(pedidos);
        rv.setAdapter(adapter);

        rellenar();
    }

    private void rellenar() {
        String tipo = "C";
        Cursor c = DatabaseOperations.obtenerInstancia(getActivity()).obtenerCabecera(tipo);
        if (c.moveToFirst()) {
            do {
                String fecha = c.getString(1);
                String caja = c.getString(2);
                String cliente = c.getString(3);
                String created=c.getString(4);

                pedidos.add(new PedidosHoraCreacion(fecha, caja, cliente,created));
            } while (c.moveToNext());
        }
        adapter.notifyDataSetChanged();

    }


}
