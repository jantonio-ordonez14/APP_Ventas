package com.example.esmail.app_ventas.makesale;

import android.app.Fragment;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.esmail.app_ventas.R;
import com.example.esmail.app_ventas.adapters.RecyclerViewAdapterFilterCustomers;
import com.example.esmail.app_ventas.modelos.Cliente;
import com.example.esmail.app_ventas.sqlite.DatabaseOperations;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class MakeSaleFragmentHeader extends Fragment {

    private EditText etFecha, etCaja, etCliente, etSearch;
    private RecyclerView rv;
    private Button btnSiguiente, btnAnadir;
    private String clienteSelected;

    public MakeSaleFragmentHeader() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_make_sale_header, container, false);
        //obtener instancias
        etFecha = v.findViewById(R.id.et_fecha);
        etCaja = v.findViewById(R.id.et_caja);
        etCliente = v.findViewById(R.id.et_cliente);
        etSearch = v.findViewById(R.id.et_search);
        rv = v.findViewById(R.id.rv_cliente);
        rv.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        rv.setLayoutManager(llm);

        btnAnadir = v.findViewById(R.id.btn_cliente);
        btnSiguiente = v.findViewById(R.id.btn_siguiente);
        btnSiguiente.setEnabled(false);

        final ArrayList<Cliente> clientes = new ArrayList<>();
        DatabaseOperations db = DatabaseOperations.obtenerInstancia(getActivity());
        //obtener clientes
        final Cursor c = db.obtenerClientes();
        if (c.moveToFirst()) {
            do {
                String codigo = c.getString(1);
                String nombre = c.getString(2);
                clientes.add(new Cliente(codigo, nombre));
            } while (c.moveToNext());
        }
        c.close();

        final RecyclerViewAdapterFilterCustomers adapter = new RecyclerViewAdapterFilterCustomers(clientes);
        adapter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = rv.getChildAdapterPosition(v);
                btnSiguiente.setEnabled(true);
                clienteSelected = clientes.get(position).getCod_articulo();
            }
        });
        rv.setAdapter(adapter);

        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                adapter.getFilter().filter(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


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
                //si se deja vacio, coje la del hoy
                if (fecha.isEmpty() || !validarFecha(fecha)) {
                    fecha = new SimpleDateFormat("dd/MM/yyyy").format(new Date());
                }
                //obtenemos la caja
                String caja = etCaja.getText().toString();

                //enviamos los parametros al activity para que se envien al fragment
                ((MakeSale) getActivity()).setParametersToFragment(fecha, caja, clienteSelected);

            }
        });
        return v;
    }

    private boolean validarFecha(String fecha) {
        SimpleDateFormat sfd = new SimpleDateFormat("dd/MM/yyyy");
        boolean result = true;
        try {
            sfd.parse(fecha);
        } catch (ParseException e) {
            e.printStackTrace();
            result = false;
        }
        return result;
    }

}
