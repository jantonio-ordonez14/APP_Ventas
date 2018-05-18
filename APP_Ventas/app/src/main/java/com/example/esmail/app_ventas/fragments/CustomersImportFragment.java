package com.example.esmail.app_ventas.fragments;

import android.app.Fragment;
import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.esmail.app_ventas.CustomersImport;
import com.example.esmail.app_ventas.R;
import com.example.esmail.app_ventas.adapters.AdapterCustomersImport;
import com.example.esmail.app_ventas.modelos.Cliente;
import com.example.esmail.app_ventas.sqlite.DatabaseOperations;

import java.util.ArrayList;

public class CustomersImportFragment extends Fragment {

    private ListView lv;

    public CustomersImportFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_customers_import, container, false);


        lv =v.findViewById(R.id.lv_customers_import);

        action("clientes.csv",v.getContext());

        return v;

    }

    public void action(String nombreDocumento, Context context) {
        CustomersImport customersImport = new CustomersImport(nombreDocumento, context);

        ArrayList<Cliente> al = new ArrayList<>();

        if (customersImport.actionImport()) {
            DatabaseOperations db = DatabaseOperations.obtenerInstancia(context);
            Cursor c = db.obtenerClientes();
            if (c.moveToFirst()) {
                do {
                    String codArticulo = c.getString(1);
                    String nombreCli = c.getString(2);

                    al.add(new Cliente(codArticulo, nombreCli));

                } while (c.moveToNext());
            }
            System.out.println("inicio");
            for (Cliente al2 :
                    al) {
                System.out.println(al2.getCod_articulo()+"\t"+al2.getNombre());
            }
            System.out.println("fin");

        }
        AdapterCustomersImport adapter = new AdapterCustomersImport(getActivity(), al);
        lv.setAdapter(adapter);
    }


}