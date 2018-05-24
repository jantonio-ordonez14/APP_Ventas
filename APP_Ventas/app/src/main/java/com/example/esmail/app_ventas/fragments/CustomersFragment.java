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
import android.widget.Button;
import android.widget.Toast;

import com.example.esmail.app_ventas.MainActivity;
import com.example.esmail.app_ventas.imports.CustomersImport;
import com.example.esmail.app_ventas.R;
import com.example.esmail.app_ventas.adapters.RecyclerViewAdapterCustomers;
import com.example.esmail.app_ventas.modelos.Cliente;
import com.example.esmail.app_ventas.sqlite.DatabaseOperations;

import java.util.ArrayList;
import java.util.List;

public class CustomersFragment extends Fragment implements View.OnClickListener {

    RecyclerViewAdapterCustomers adapter;
    RecyclerView recyclerView;
    private static List<Cliente> clientes;
    private Button btnImportar, btnEliminar;
    private String id = "clientes";

    public CustomersFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_customers, container, false);

        btnImportar = v.findViewById(R.id.btn_import_customers);
        btnImportar.setOnClickListener(this);
        btnEliminar = v.findViewById(R.id.btn_delete_customers);
        btnEliminar.setOnClickListener(this);
        recyclerView = (RecyclerView) v.findViewById(R.id.recycler_view);
        clientes = new ArrayList<>();

        return v;

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        //recycler view
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(llm);

        //adaptador
        adapter = new RecyclerViewAdapterCustomers(clientes);
        recyclerView.setAdapter(adapter);

        //obtenemos clientes
        Cursor c = DatabaseOperations.obtenerInstancia(getActivity()).obtenerClientes();
        if (c.moveToFirst()) {
            do {
                String codArticulo = c.getString(1);
                String nombreCli = c.getString(2);

                clientes.add(new Cliente(codArticulo, nombreCli));

            } while (c.moveToNext());
        }
        adapter.notifyDataSetChanged();


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_import_customers:
                importar();
                break;
            case R.id.btn_delete_customers:
                eliminar();
                break;
        }
    }

    private void eliminar() {
        if (DatabaseOperations.obtenerInstancia(getActivity()).eliminarClientes()) {
            Toast.makeText(getActivity(), "Eliminada la tabla clientes", Toast.LENGTH_SHORT).show();
            ((MainActivity) getActivity()).recargarFragment(id);

        }
    }

    private void importar() {
        //objeto para importar documento clientes
        CustomersImport customersImport = new CustomersImport("clientes.csv", getActivity());
        if (customersImport.actionImport()) {
            Toast.makeText(getActivity(), "Actualizados clientes", Toast.LENGTH_SHORT).show();
            ((MainActivity) getActivity()).recargarFragment(id);

        }
    }
}
