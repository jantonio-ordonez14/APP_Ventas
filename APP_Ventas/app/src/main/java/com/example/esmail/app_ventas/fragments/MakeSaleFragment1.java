package com.example.esmail.app_ventas.fragments;

import android.app.Fragment;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.esmail.app_ventas.MakeSale;
import com.example.esmail.app_ventas.R;
import com.example.esmail.app_ventas.sqlite.DatabaseOperations;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class MakeSaleFragment1 extends Fragment {


    private EditText etFecha, etCaja;
    private Spinner spinner;
    private Button btnBuscar, btnAnadir;
    private String clienteSelected;

    public MakeSaleFragment1() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_make_sale_1, container, false);

        etFecha = v.findViewById(R.id.et_fecha);
        etCaja = v.findViewById(R.id.et_caja);
        spinner = v.findViewById(R.id.spinner);
        Button btn = v.findViewById(R.id.btn_siguiente);

        ArrayList<String> clientes = new ArrayList<>();
        DatabaseOperations db = DatabaseOperations.obtenerInstancia(getActivity());
        final Cursor c = db.obtenerClientes();
        if (c.moveToFirst()) {
            do {
                String nombre = c.getString(1);
                clientes.add(nombre);
            } while (c.moveToNext());
        }
        c.close();
        spinner.setAdapter(new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, clientes));
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String text = (String) parent.getItemAtPosition(position).toString();
                clienteSelected = text;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String fecha = etFecha.getText().toString();
                if (fecha.isEmpty()) {
                    fecha = new SimpleDateFormat("dd/MM/yyyy").format(new Date());
                }

                String caja = etCaja.getText().toString();


                System.out.println("Fecha --> " + fecha);
                System.out.println("Caja --> " +
                        "" + caja);
                System.out.println("Cliente --> " + clienteSelected);
                ((MakeSale) getActivity()).setParametersToFragment(fecha, caja, clienteSelected);

            }
        });
        return v;
    }

}