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
import com.example.esmail.app_ventas.imports.ProductsImport;
import com.example.esmail.app_ventas.R;
import com.example.esmail.app_ventas.adapters.RecyclerViewAdapterProducts;
import com.example.esmail.app_ventas.modelos.Articulo;
import com.example.esmail.app_ventas.sqlite.DatabaseOperations;

import java.util.ArrayList;
import java.util.List;

public class ProductsFragment extends Fragment implements View.OnClickListener {

    RecyclerViewAdapterProducts adapter;
    RecyclerView recyclerView;
    private static List<Articulo> articulos;
    private Button btnImportar, btnEliminar;
    private String id = "articulos";

    public ProductsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_products, container, false);
        //instancias
        btnImportar = v.findViewById(R.id.btn_import_products);
        btnImportar.setOnClickListener(this);
        btnEliminar = v.findViewById(R.id.btn_delete_products);
        btnEliminar.setOnClickListener(this);
        recyclerView = (RecyclerView) v.findViewById(R.id.recyclerview_products);

        articulos = new ArrayList<>();

        return v;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //recyclerView
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(llm);
        adapter = new RecyclerViewAdapterProducts(articulos);
        recyclerView.setAdapter(adapter);

        Cursor c = DatabaseOperations.obtenerInstancia(getActivity()).obtenerArticulos();
        if (c.moveToFirst()) {
            do {
                String codArticulo = c.getString(1);
                String codBarras = c.getString(2);
                String descripcion = c.getString(3);
                String unidades = c.getString(4);
                String precio = c.getString(5);
                String importe = c.getString(6);

                articulos.add(new Articulo(codArticulo, codBarras, descripcion, unidades, precio, importe));
            } while (c.moveToNext());
            adapter.notifyDataSetChanged();

        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_import_products:
                importar();
                break;
            case R.id.btn_delete_products:
                eliminar();
                break;
        }
    }

    private void eliminar() {
        if (DatabaseOperations.obtenerInstancia(getActivity()).eliminarArticulos()) {
            Toast.makeText(getActivity(), "Eliminada la tabla articulos", Toast.LENGTH_SHORT).show();
            ((MainActivity) getActivity()).recargarFragment(id);

        }
    }

    private void importar() {
        //objeto para importar documento clientes
        ProductsImport productsImport = new ProductsImport("productos.csv", getActivity());

        if (productsImport.actionImport()) {
            Toast.makeText(getActivity(), "Actualizados articulos", Toast.LENGTH_SHORT).show();
            ((MainActivity) getActivity()).recargarFragment(id);

        }
    }

}
