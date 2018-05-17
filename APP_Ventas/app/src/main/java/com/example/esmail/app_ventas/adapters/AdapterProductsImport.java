package com.example.esmail.app_ventas.adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.esmail.app_ventas.R;
import com.example.esmail.app_ventas.modelos.Articulo;

import java.util.ArrayList;

public class AdapterProductsImport extends BaseAdapter{
    protected Activity activity;
    protected ArrayList<Articulo> items;

    public AdapterProductsImport() {
        super();
    }


    public AdapterProductsImport(Activity activity, ArrayList<Articulo> items) {
        this.activity = activity;
        this.items = items;
    }

    public void clear(){
        items.clear();
    }

    public void addAll(ArrayList<Articulo> lista){
        for (int i = 0; i < lista.size(); i++) {
            items.add(lista.get(i));
        }
    }

    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public Object getItem(int args0) {
        return items.get(args0);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View v = convertView;

        if (convertView == null){
            LayoutInflater inf=(LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inf.inflate(R.layout.estructure_lv_products, null);
        }

        Articulo dir = items.get(position);

        TextView codBarras = (TextView) v.findViewById(R.id.txt_cod_barras);
        codBarras.setText(dir.getCod_barras());

        TextView descripcion=(TextView) v.findViewById(R.id.txt_descripcion);
        descripcion.setText(dir.getDescripcion());

        TextView cantidad=(TextView) v.findViewById(R.id.txt_cantidad);
        cantidad.setText(dir.getUnidades());

        return v;
    }
}
