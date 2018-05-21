package com.example.esmail.app_ventas.fragments;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.esmail.app_ventas.R;
import com.example.esmail.app_ventas.modelos.DetallePedido;
import com.example.esmail.app_ventas.scanner.ScanActivity;
import com.example.esmail.app_ventas.sqlite.DatabaseOperations;

import java.util.ArrayList;

public class MakeSaleFragment2 extends Fragment {
    private String idCabecera, caja, cliente,tipo;
    private ArrayList<String> al = new ArrayList<>();

    public MakeSaleFragment2() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_make_sale_2, container, false);
        Log.e("Fragment", "onCreateView");


        return v;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.e("Fragment", "onCreate");
        SharedPreferences sharedPreferences=getActivity().getSharedPreferences("MakeSaleFragment2", Context.MODE_PRIVATE);

        tipo="L";

        if (sharedPreferences!=null) {
            System.out.println("entra");
            this.idCabecera=sharedPreferences.getString("id-cabecera",null);
        }
        System.out.println("Id Cab -> "+idCabecera);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.e("Fragment", "onActivityCreated");
        String auxIdCabecera = getArguments().getString("idcabecera");
        String auxCaja = getArguments().getString("caja");
        String auxCliente = getArguments().getString("cliente");

        if (auxIdCabecera!=null)   this.idCabecera=auxIdCabecera;
        if (auxCaja!=null)         this.caja=auxCaja;
        if (auxCliente!=null)      this.cliente=auxCliente;



        Button btnScan = getActivity().findViewById(R.id.btn_scan);
        Button btnFinalizar = getActivity().findViewById(R.id.btn_finalizar);
        //lv = getActivity().findViewById(R.id.lv_make_sale);

        btnScan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getActivity(), ScanActivity.class);
                startActivity(intent);
            }
        });

        final String c_barras = getArguments().getString("c-barras");
        final String unidades = getArguments().getString("unidades");


        btnFinalizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("Tipo -> " +tipo+"\tArticulo -> " + c_barras + "\tUnidades -> "+unidades+"\tId Cab -> "+idCabecera);
                DatabaseOperations operations=DatabaseOperations.obtenerInstancia(getActivity());
                operations.insertarDetalles(new DetallePedido(tipo,c_barras,unidades,idCabecera));
            }
        });

        if(c_barras!=null){

        }


    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        Log.e("MS","onSaveInstanceState");
        System.out.println("Id Cab -> "+idCabecera);


        SharedPreferences sharedPreferences=getActivity().getSharedPreferences("MakeSaleFragment2", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.putString("id-cabecera",idCabecera);
        editor.commit();


    }


}
