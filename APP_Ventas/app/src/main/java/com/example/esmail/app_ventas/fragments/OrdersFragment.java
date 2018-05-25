package com.example.esmail.app_ventas.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.esmail.app_ventas.R;
import com.example.esmail.app_ventas.adapters.RecyclerViewAdapterCustomers;
import com.example.esmail.app_ventas.adapters.RecyclerViewAdapterMakeSale;
import com.example.esmail.app_ventas.adapters.RecyclerViewAdapterOrders;
import com.example.esmail.app_ventas.modelos.Articulo;

import java.util.ArrayList;
import java.util.List;

public class OrdersFragment extends Fragment {

    private RecyclerView rv;

    public OrdersFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_orders, container, false);

        rv = v.findViewById(R.id.rv_orders);

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
        /*RecyclerViewAdapterOrders adapter= new RecyclerViewAdapterOrders(articulosExportados);
        rv.setAdapter(adapter);*/
    }


}
