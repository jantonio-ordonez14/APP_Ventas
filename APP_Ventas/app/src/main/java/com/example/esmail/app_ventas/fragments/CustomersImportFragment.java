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

import com.example.esmail.app_ventas.CustomersImport;
import com.example.esmail.app_ventas.R;
import com.example.esmail.app_ventas.adapters.RecyclerViewAdapterCustomers;
import com.example.esmail.app_ventas.modelos.Cliente;
import com.example.esmail.app_ventas.sqlite.DatabaseOperations;

import java.util.ArrayList;
import java.util.List;

public class CustomersImportFragment extends Fragment {

    RecyclerViewAdapterCustomers adapter;
    RecyclerView recyclerView;
    private static List<Cliente> clientes;

    public CustomersImportFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_customers_import, container, false);


        return v;

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        clientes = new ArrayList<>();

        recyclerView = (RecyclerView) getView().findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(llm);
        CustomersImport customersImport = new CustomersImport("clientes.csv", getActivity());
        adapter = new RecyclerViewAdapterCustomers(clientes);
        recyclerView.setAdapter(adapter);

        if (customersImport.actionImport()) {
            DatabaseOperations db = DatabaseOperations.obtenerInstancia(getActivity());
            Cursor c = db.obtenerClientes();
            if (c.moveToFirst()) {
                do {
                    String codArticulo = c.getString(1);
                    String nombreCli = c.getString(2);

                    clientes.add(new Cliente(codArticulo, nombreCli));

                } while (c.moveToNext());
            }
            adapter.notifyDataSetChanged();

        }

    }
}
