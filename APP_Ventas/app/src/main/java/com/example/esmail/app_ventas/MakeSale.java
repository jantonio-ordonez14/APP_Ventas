package com.example.esmail.app_ventas;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.esmail.app_ventas.modelos.CabeceraPedido;
import com.example.esmail.app_ventas.scanner.ScanActivity;
import com.example.esmail.app_ventas.sqlite.DatabaseOperations;
import com.google.zxing.Result;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

import static com.example.esmail.app_ventas.scanner.ScanActivity.EXTRA;

public class MakeSale extends AppCompatActivity {

    private String TAG = "MakeSale";
    private String resultado = null;

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
            fragment = new MakeSaleFragment2();
            Bundle args = new Bundle();
            args.putString("c-barras", resultado);
            fragment.setArguments(args);

        } else {
            fragment = new MakeSaleFragment();


        }
        fragmentTransaction.replace(R.id.content_frame_make, fragment, TAG);
        fragmentTransaction.commit();

    }


    public void setParametersToFragment(String fecha, String caja, String cliente) {
        DatabaseOperations operations = DatabaseOperations.obtenerInstancia(this);
        operations.insertarCabecera(new CabeceraPedido(cliente, fecha, caja));

        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        MakeSaleFragment2 fragment = new MakeSaleFragment2();
        Bundle args = new Bundle();
        args.putString("c-barras", resultado);
        fragment.setArguments(args);
        fragmentTransaction.replace(R.id.content_frame_make, fragment, TAG);
        fragmentTransaction.commit();

    }


    /**
     * Fragment formulario
     */
    @SuppressLint("ValidFragment")
    public static class MakeSaleFragment extends Fragment {


        private EditText etFecha, etCaja;
        private Spinner spinner;
        private Button btnBuscar, btnAnadir;
        private String clienteSelected;

        public MakeSaleFragment() {
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

    /**
     * Fragment listview
     */
    @SuppressLint("ValidFragment")
    public static class MakeSaleFragment2 extends Fragment {
        private ListView lv;
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


        }

        @Override
        public void onActivityCreated(@Nullable Bundle savedInstanceState) {
            super.onActivityCreated(savedInstanceState);
            Log.e("Fragment", "onActivityCreated");

            Button btnScan = getActivity().findViewById(R.id.btn_scan);
            Button btnFinalizar = getActivity().findViewById(R.id.btn_finalizar);
            lv = getActivity().findViewById(R.id.lv_make_sale);


            btnScan.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getActivity(), ScanActivity.class);
                    startActivity(intent);
                }
            });

            String c_barras = getArguments().getString("c-barras");
            if (c_barras != null) {
                System.out.println("cod barras -> " + c_barras);

                al.add(c_barras);
                ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, al);
                lv.setAdapter(adapter);

            }

        }
    }


}
