package com.example.esmail.app_ventas.fragments;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.esmail.app_ventas.ProductsImport;
import com.example.esmail.app_ventas.R;
import com.example.esmail.app_ventas.AdapterProductsImport;
import com.example.esmail.app_ventas.modelos.Articulo;
import com.example.esmail.app_ventas.sqlite.DatabaseOperations;

import java.util.ArrayList;


public class ProductsImportFragment extends Fragment {

    private ListView lv;

    public ProductsImportFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.fragment_products_import, container, false);
        lv=v.findViewById(R.id.lv_products_import);
        
        action("productos.csv",v.getContext());
        return v;
    }

    public void action(String nombreDocumento, Context context) {
        ProductsImport productsImport = new ProductsImport(nombreDocumento, context);

        ArrayList<Articulo> al = new ArrayList<>();

        if (productsImport.actionImport()) {
            DatabaseOperations db = DatabaseOperations.obtenerInstancia(context);
            Cursor c = db.obtenerArticulos();
            if (c.moveToFirst()) {
                do {
                    String codArticulo = c.getString(1);
                    String codBarras = c.getString(2);
                    String descripcion = c.getString(3);
                    String unidades=c.getString(4);
                    String precio=c.getString(5);
                    String importe=c.getString(6);

                    al.add(new Articulo(codArticulo,codBarras,descripcion,unidades,precio,importe));
                } while (c.moveToNext());
            }

            for (Articulo al2 :
                    al) {
                System.out.println(al2.getCod_articulo()+"\t"+al2.getCod_barras()+"\t"+al2.getDescripcion()+"\t"+al2.getImporte()+"\t"+al2.getPrecio()+"\t"+al2.getUnidades());
            }

            AdapterProductsImport adapter = new AdapterProductsImport(getActivity(), al);
            lv.setAdapter(adapter);
        }
    }
}
