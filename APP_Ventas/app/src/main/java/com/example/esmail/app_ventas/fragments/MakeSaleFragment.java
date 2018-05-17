package com.example.esmail.app_ventas.fragments;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.esmail.app_ventas.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class MakeSaleFragment extends Fragment {



    private EditText etFecha, etCaja;
    private Button btnBuscar, btnAnadir;

    public MakeSaleFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_make_sale_1, container, false);

        etFecha = v.findViewById(R.id.et_fecha);
        etCaja = v.findViewById(R.id.et_caja);
        btnBuscar = v.findViewById(R.id.btn_buscar);
        btnAnadir = v.findViewById(R.id.btn_anadir);
        Button btn=v.findViewById(R.id.btn_siguiente);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String fecha=etFecha.getText().toString();
                if (fecha.isEmpty()){
                    fecha = new SimpleDateFormat("dd-MM-yyyy").format(new Date());
                    System.out.println("Fecha --> " + fecha);
                }

                MakeSaleFragment2 fragment2 = new MakeSaleFragment2();
                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.content_frame_make, fragment2, "Fragment")
                        .addToBackStack(null);
                fragmentTransaction.commit();
            }
        });
        return v;
    }

}
