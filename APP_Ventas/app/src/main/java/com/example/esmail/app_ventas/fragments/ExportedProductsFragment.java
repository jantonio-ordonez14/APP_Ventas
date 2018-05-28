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

import com.example.esmail.app_ventas.Export;
import com.example.esmail.app_ventas.R;
import com.example.esmail.app_ventas.adapters.RecyclerViewAdapterCustomers;
import com.example.esmail.app_ventas.adapters.RecyclerViewAdapterExport;
import com.example.esmail.app_ventas.modelos.Cliente;
import com.example.esmail.app_ventas.modelos.Exportados;
import com.example.esmail.app_ventas.sqlite.DatabaseOperations;

import java.util.ArrayList;

public class ExportedProductsFragment extends Fragment {
    private RecyclerView recyclerView;
    private ArrayList<Exportados> productosExportados;

    public ExportedProductsFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_exported_products, container, false);

        recyclerView = (RecyclerView) v.findViewById(R.id.rv_exported_products);
        productosExportados = new ArrayList<>();

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
        RecyclerViewAdapterExport adapter = new RecyclerViewAdapterExport(productosExportados);
        recyclerView.setAdapter(adapter);

        //obtenemos clientes
        Cursor c = DatabaseOperations.obtenerInstancia(getActivity()).obtenerExportado();
        if (c.moveToFirst()) {
            do {
                String idRegistro = c.getString(1);
                String tipo = c.getString(2);
                String fecha = c.getString(3);
                String caja = c.getString(4);
                String cliente = c.getString(5);
                String articulo = c.getString(6);
                String unidades = c.getString(7);
                String idCabecera = c.getString(8);
                productosExportados.add(new Exportados(idRegistro, tipo, fecha, caja, cliente, articulo, unidades, idCabecera));

            } while (c.moveToNext());
        }
        adapter.notifyDataSetChanged();
    }
}
