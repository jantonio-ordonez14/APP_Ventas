package com.example.esmail.app_ventas.fragments;

import android.app.Fragment;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.example.esmail.app_ventas.MakeSale;
import com.example.esmail.app_ventas.R;
import com.example.esmail.app_ventas.sqlite.DatabaseOperations;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class MakeSaleFragment1 extends Fragment {

    private EditText etFecha, etCaja, etCliente, etSearch;
    private RecyclerView rv;
    private Button btnSiguiente, btnAnadir;
    private String clienteSelected;

    public MakeSaleFragment1() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_make_sale_1, container, false);
        //obtener instancias
        etFecha = v.findViewById(R.id.et_fecha);
        etCaja = v.findViewById(R.id.et_caja);
        etCliente = v.findViewById(R.id.et_cliente);
        etSearch = v.findViewById(R.id.et_search);
        rv = v.findViewById(R.id.lv_cliente);

        btnAnadir = v.findViewById(R.id.btn_cliente);
        btnSiguiente = v.findViewById(R.id.btn_siguiente);
        btnSiguiente.setEnabled(false);

        ArrayList<String> clientes = new ArrayList<>();
        DatabaseOperations db = DatabaseOperations.obtenerInstancia(getActivity());
        //obtener clientes
        final Cursor c = db.obtenerClientes();
        if (c.moveToFirst()) {
            do {
                String nombre = c.getString(2);
                clientes.add(nombre);
            } while (c.moveToNext());
        }
        c.close();
        


        btnAnadir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String clienteObtenido = "";
                //obtener cliente
                String cliente = etCliente.getText().toString();
                //obtener clientes de la bd
                Cursor c = DatabaseOperations.obtenerInstancia(getActivity()).obtenerClientes();
                if (c.moveToFirst()) {
                    do {
                        clienteObtenido = c.getString(1);
                    } while (c.moveToNext());
                }
                //si existe el cliente lo guardamos
                if (clienteObtenido.length() == cliente.length()) {
                    btnSiguiente.setEnabled(true);
                    clienteSelected = cliente;
                } else {
                    btnSiguiente.setEnabled(false);
                }

            }
        });

        btnSiguiente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //obtenemos la fecha
                String fecha = etFecha.getText().toString();
                //si se deja vacio, se coje la del hoy
                if (fecha.isEmpty()) {
                    fecha = new SimpleDateFormat("dd/MM/yyyy").format(new Date());
                }
                //obtenemos la caja
                String caja = etCaja.getText().toString();


                System.out.println("Fecha --> " + fecha);
                System.out.println("Caja --> " +
                        "" + caja);
                System.out.println("Cliente --> " + clienteSelected);
                //enviamos los parametros al activity para que se envien al fragment
                ((MakeSale) getActivity()).setParametersToFragment(fecha, caja, clienteSelected);

            }
        });
        return v;
    }

}
