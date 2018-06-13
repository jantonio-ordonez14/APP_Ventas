package com.example.esmail.app_ventas.makesale;

import android.app.Fragment;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.esmail.app_ventas.R;
import com.example.esmail.app_ventas.adapters.RecyclerViewAdapterMakeSale;
import com.example.esmail.app_ventas.modelos.DetallePedido;
import com.example.esmail.app_ventas.sqlite.DatabaseOperations;

import java.util.ArrayList;
import java.util.List;

public class MakeSaleFragmentDetails extends Fragment {
    private String idCabecera, caja, cliente, tipo;
    private List<DetallePedido> al = new ArrayList<>();
    private RecyclerViewAdapterMakeSale adapter;
    private RecyclerView recyclerView;

    public MakeSaleFragmentDetails() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_make_sale_details, container, false);
        Log.e("Fragment", "onCreateView");

        return v;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.e("Fragment", "onCreate");
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("MakeSaleFragmentDetails", Context.MODE_PRIVATE);
        // indicamos el tipo
        tipo = "L";
        // obtenemos la idcabecera
        if (sharedPreferences != null) {
            System.out.println("entra");
            this.idCabecera = sharedPreferences.getString("id-cabecera", null);
        }
        System.out.println("Id Cab -> " + idCabecera);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.e("Fragment", "onActivityCreated");
        // obtenemos los argumentos
        String auxIdCabecera = getArguments().getString("idcabecera");
        String auxCaja = getArguments().getString("caja");
        String auxCliente = getArguments().getString("cliente");
        // si existen los guardamos
        if (auxIdCabecera != null) this.idCabecera = auxIdCabecera;
        if (auxCaja != null) this.caja = auxCaja;
        if (auxCliente != null) this.cliente = auxCliente;
        // instancias
        Button btnScan = getActivity().findViewById(R.id.btn_scan);
        Button btnFinalizar = getActivity().findViewById(R.id.btn_finalizar);

        // escaneo
        btnScan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // inicia el menu del escaner
                ((MakeSale) getActivity()).inicializeScan();
            }
        });
        // obtenemos argumentos
        String c_barras = getArguments().getString("c-barras");
        String unidades = getArguments().getString("unidades");

        btnFinalizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ((MakeSale) getActivity()).setParametersExport(idCabecera);

            }
        });

        if (c_barras != null) {
            System.out.println("Tipo -> " + tipo + "\tArticulo -> " + c_barras + "\tUnidades -> " + unidades + "\tId Cab -> " + idCabecera);

            DatabaseOperations operations = DatabaseOperations.obtenerInstancia(getActivity());
            if (operations.consultarDetalle(c_barras)) {
                operations.actualizarArticuloDetalles(new DetallePedido(tipo, c_barras, unidades, idCabecera));
            } else {
                // insertar detalles
                operations.insertarDetalles(new DetallePedido(tipo, c_barras, unidades, idCabecera));
            }
            // iniciar arraylist
            al = new ArrayList<DetallePedido>();
            // recyclerview
            recyclerView = (RecyclerView) getView().findViewById(R.id.lv_make_sale);
            recyclerView.setHasFixedSize(true);
            LinearLayoutManager llm = new LinearLayoutManager(getActivity());
            llm.setOrientation(LinearLayoutManager.VERTICAL);
            recyclerView.setLayoutManager(llm);
            adapter = new RecyclerViewAdapterMakeSale(al);
            recyclerView.setAdapter(adapter);
            // obtenemos los detalles para mostrarlos en el recyclerview
            Cursor c = operations.obtenerDetalles();
            if (c.moveToFirst()) {
                do {
                    String articulo = c.getString(2);
                    String unid = c.getString(3);

                    al.add(new DetallePedido(null, articulo, unid, null));

                } while (c.moveToNext());
            }
            // guardar cambios
            adapter.notifyDataSetChanged();
        }


    }

    /**
     * Guardar instancia
     *
     * @param outState
     */
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        Log.e("MS", "onSaveInstanceState");
        System.out.println("Id Cab -> " + idCabecera);
        // guardar idcabecera
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("MakeSaleFragmentDetails", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("id-cabecera", idCabecera);
        editor.commit();


    }


}
