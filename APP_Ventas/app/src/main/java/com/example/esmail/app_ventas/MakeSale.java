package com.example.esmail.app_ventas;

import android.app.Dialog;
import android.app.DialogFragment;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.example.esmail.app_ventas.fragments.EditorFragment;
import com.example.esmail.app_ventas.fragments.MakeSaleFragment1;
import com.example.esmail.app_ventas.fragments.MakeSaleFragment2;
import com.example.esmail.app_ventas.modelos.CabeceraPedido;
import com.example.esmail.app_ventas.modelos.DetallePedido;
import com.example.esmail.app_ventas.sqlite.DatabaseOperations;

import static com.example.esmail.app_ventas.scanner.ScanActivity.EXTRA;

public class MakeSale extends AppCompatActivity {

    private String TAG = "MakeSale";
    private String resultado = null;
    private static String unidades;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_make_sale);

        Log.e(TAG, "onCreate");
        resultado = getIntent().getStringExtra(EXTRA);

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
        Fragment fragment;
        if (resultado != null) {
            fragment = new EditorFragment();
            Bundle args = new Bundle();
            args.putString("c-barras", resultado);
            fragment.setArguments(args);

        } else {
            fragment = new MakeSaleFragment1();


        }
        fragmentTransaction.replace(R.id.content_frame_make, fragment, TAG);
        fragmentTransaction.commit();

    }


    public void setParametersToFragment(String fecha, String caja, String cliente) {
        DatabaseOperations operations = DatabaseOperations.obtenerInstancia(this);
        String tipo="C";
        String idCabecera=operations.insertarCabecera(new CabeceraPedido(tipo, fecha, caja,cliente));
        System.out.println("id cabecera -> "+ idCabecera);

        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        MakeSaleFragment2 fragment = new MakeSaleFragment2();
        Bundle args = new Bundle();
        args.putString("idcabecera", idCabecera);
        fragment.setArguments(args);
        fragmentTransaction.replace(R.id.content_frame_make, fragment, TAG);
        fragmentTransaction.commit();

    }

    public void setUnidades(String unidades, String codBarras) {

        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        MakeSaleFragment2 fragment = new MakeSaleFragment2();
        Bundle args = new Bundle();
        args.putString("unidades", unidades);
        args.putString("c-barras", codBarras);
        fragment.setArguments(args);
        fragmentTransaction.replace(R.id.content_frame_make, fragment);
        fragmentTransaction.commit();
    }


}
