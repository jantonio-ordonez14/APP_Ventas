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
import com.example.esmail.app_ventas.ProductsImport;
import com.example.esmail.app_ventas.R;
import com.example.esmail.app_ventas.adapters.RecyclerViewAdapterProducts;
import com.example.esmail.app_ventas.modelos.Articulo;
import com.example.esmail.app_ventas.sqlite.DatabaseOperations;

import java.util.ArrayList;
import java.util.List;


public class ProductsImportFragment extends Fragment {

    RecyclerViewAdapterProducts adapter;
    RecyclerView recyclerView;
    private static List<Articulo> articulos;

    public ProductsImportFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.fragment_products_import, container, false);

        return v;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        ProductsImport productsImport = new ProductsImport("productos.csv", getActivity());
        articulos =new ArrayList<>();

        recyclerView = (RecyclerView) getView().findViewById(R.id.recyclerview_products);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(llm);
        adapter = new RecyclerViewAdapterProducts(articulos);
        recyclerView.setAdapter(adapter);

        if (productsImport.actionImport()) {
            DatabaseOperations db = DatabaseOperations.obtenerInstancia(getActivity());
            Cursor c = db.obtenerArticulos();
            if (c.moveToFirst()) {
                do {
                    String codArticulo = c.getString(1);
                    String codBarras = c.getString(2);
                    String descripcion = c.getString(3);
                    String unidades=c.getString(4);
                    String precio=c.getString(5);
                    String importe=c.getString(6);



                    articulos.add(new Articulo(codArticulo,codBarras,descripcion,unidades,precio,importe));
                } while (c.moveToNext());
            }
            adapter.notifyDataSetChanged();


        }
    }
}
