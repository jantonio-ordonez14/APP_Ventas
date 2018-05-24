package com.example.esmail.app_ventas.fragments;

import android.database.Cursor;
import android.os.Bundle;
import android.app.Fragment;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.esmail.app_ventas.R;
import com.example.esmail.app_ventas.adapters.RecyclerViewAdapterCustomers;
import com.example.esmail.app_ventas.adapters.RecyclerViewAdapterProducts;
import com.example.esmail.app_ventas.modelos.Articulo;
import com.example.esmail.app_ventas.modelos.Cliente;
import com.example.esmail.app_ventas.sqlite.DatabaseOperations;

import java.util.ArrayList;

public class InitialFragment extends Fragment {

    public InitialFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
         View v=inflater.inflate(R.layout.fragment_initial, container, false);

        return v;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.e("InitialFragment","onActivityCreated");
        mostrarClientes();
        mostrarProductos();
    }

    private void mostrarClientes() {
        ArrayList<Cliente> clientes = new ArrayList<>();
        //recycler view
        RecyclerView recyclerView = (RecyclerView) getActivity().findViewById(R.id.rv_clientes);

        recyclerView.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(llm);
        //objeto para importar documento clientes
        //adaptador
        RecyclerViewAdapterCustomers adapter = new RecyclerViewAdapterCustomers(clientes);
        recyclerView.setAdapter(adapter);
        //si sale bien los mostramos

            DatabaseOperations db = DatabaseOperations.obtenerInstancia(getActivity());
            //obtenemos clientes
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

    private void mostrarProductos() {
        ArrayList<Articulo> articulos = new ArrayList<>();

        RecyclerView recyclerView = (RecyclerView) getActivity().findViewById(R.id.rv_productos);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(llm);
        RecyclerViewAdapterProducts adapter = new RecyclerViewAdapterProducts(articulos);
        recyclerView.setAdapter(adapter);

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

    @Override
    public void onPause() {
        super.onPause();
        Log.e("InitialFragment","onPause");


    }

    @Override
    public void onResume() {
        super.onResume();
        Log.e("InitialFragment","onResume");
        mostrarClientes();
        mostrarProductos();
    }
}
