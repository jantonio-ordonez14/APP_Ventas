package com.example.esmail.app_ventas;

import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.example.esmail.app_ventas.fragments.MakeSaleFragment;
import com.example.esmail.app_ventas.fragments.MakeSaleFragment2;
import com.example.esmail.app_ventas.modelos.CabeceraPedido;
import com.example.esmail.app_ventas.sqlite.DatabaseOperations;

public class MakeSale extends AppCompatActivity{

    private String TAG = "MakeSale";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_make_sale);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        MakeSaleFragment fragment = new MakeSaleFragment();
        fragmentTransaction.replace(R.id.content_frame_make, fragment, TAG);
        fragmentTransaction.commit();

    }

    public void setParametersToFragment(String fecha, String caja, String cliente){
        DatabaseOperations operations=DatabaseOperations.obtenerInstancia(this);
        operations.insertarCabecera(new CabeceraPedido(cliente,fecha,caja));

        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        MakeSaleFragment2 fragment = new MakeSaleFragment2();
        fragmentTransaction.replace(R.id.content_frame_make, fragment, TAG);
        fragmentTransaction.commit();

    }

}
