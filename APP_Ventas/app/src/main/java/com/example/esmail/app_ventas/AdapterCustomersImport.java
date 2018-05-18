package com.example.esmail.app_ventas;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.esmail.app_ventas.R;
import com.example.esmail.app_ventas.modelos.Cliente;

import java.util.ArrayList;

public class AdapterCustomersImport extends BaseAdapter {
    protected Activity activity;
    protected ArrayList<Cliente> items;

    public AdapterCustomersImport(Activity activity, ArrayList<Cliente> items) {
        this.activity = activity;
        this.items = items;
    }


    public void clear() {
        items.clear();
    }

    public void addAll(ArrayList<Cliente> lista) {
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

        if (convertView == null) {
            LayoutInflater inf = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inf.inflate(R.layout.estructure_lv_customers, null);
        }

        Cliente dir = items.get(position);
/*
        TextView codigo = (TextView) v.findViewById(R.id.txt_titulo);
        codigo.setText(dir.getCod_articulo());

        TextView nombre = (TextView) v.findViewById(R.id.txt_subtitulo);
        nombre.setText(dir.getNombre());
*/

        return v;
    }
}
