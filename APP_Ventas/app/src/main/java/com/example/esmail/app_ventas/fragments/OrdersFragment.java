package com.example.esmail.app_ventas.fragments;

import android.app.Fragment;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.esmail.app_ventas.Export;
import com.example.esmail.app_ventas.MainActivity;
import com.example.esmail.app_ventas.Pedidos;
import com.example.esmail.app_ventas.PedidosHoraCreacion;
import com.example.esmail.app_ventas.R;
import com.example.esmail.app_ventas.adapters.RecyclerViewAdapterOrders;
import com.example.esmail.app_ventas.modelos.Exportados;
import com.example.esmail.app_ventas.modelos.Pedido;
import com.example.esmail.app_ventas.sqlite.DatabaseOperations;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class OrdersFragment extends Fragment {

    private RecyclerView rv;
    private List<PedidosHoraCreacion> pedidosHoraCreacions;
    private RecyclerViewAdapterOrders adapter;
    private String id="pedidos";


    public OrdersFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_orders, container, false);
        //recyclerview
        rv = v.findViewById(R.id.rv_orders);

        pedidosHoraCreacions = new ArrayList<>();

        return v;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        // recycler view
        rv.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        rv.setLayoutManager(llm);

        //adaptador
        adapter = new RecyclerViewAdapterOrders(pedidosHoraCreacions);
        rv.setAdapter(adapter);
        //rellenar recycleview
        rellenar();
        //boton exportar
        Button exp = getView().findViewById(R.id.exportar);
        exp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LinkedList<PedidosHoraCreacion> marcados = adapter.obtenerSeleccionados();
                ArrayList<String> contenidoMarcados = new ArrayList<>();
                //obtener la fecha y hora de creacion
                for (PedidosHoraCreacion os : marcados) {
                    contenidoMarcados.add(os.getHora_creacion());
                }
                System.out.println(contenidoMarcados);
                //obtener la id de la cabecera
                ArrayList<String> idCabecera = obtenerIdCabecera(contenidoMarcados);
                System.out.println("CAbecera -> " + idCabecera);

                if (idCabecera != null) {
                    //obtener el pedido con esa id
                    List<Pedidos> pd = obtenerPedido(idCabecera);
                    //exportar el pedido
                    new Export(pd, getActivity()).action();
                    //insertar en la tabla exportados
                    for (Pedidos al :
                            pd) {
                        System.out.println("idregistro -> " + al.getIdregistro() + "\ttipo -> " + al.getTipo() + "\tfecha -> " + al.getFecha() +
                                "\tcaja -> " + al.getCaja() + "\tidcliente -> " + al.getCliente() + "\tArticulo -> " + al.getArticulo() +
                                "\tunidades -> " + al.getUnidades() + "\tcabecera -> " + al.getFk_id_cabecera());
                        DatabaseOperations.obtenerInstancia(getActivity()).insertarExportado(new Exportados(al.getIdregistro(), al.getTipo(),
                                al.getFecha(), al.getCaja(), al.getCliente(), al.getArticulo(), al.getUnidades(), al.getFk_id_cabecera()));
                    }
                    //eliminar pedidos exportados
                    for (String al : idCabecera) {
                        if (DatabaseOperations.obtenerInstancia(getActivity()).eliminarPedidoExportado(al)){
                            Log.i("OrdersFragment", "Pedido Eliminado");
                            ((MainActivity)getActivity()).recargarFragment(id);
                        }
                    }


                }


            }

        });
    }


    private ArrayList<String> obtenerIdCabecera(ArrayList<String> fechaHora) {
        ArrayList<String> idCabecera = new ArrayList<>();
        for (String al : fechaHora) {
            System.out.println("Al -> " + al);
            Cursor c = DatabaseOperations.obtenerInstancia(getActivity()).obtenerIdCabecera(al);

            if (c.moveToFirst()) {
                do {
                    String id = c.getString(0);
                    System.out.println("id -> " + id);
                    idCabecera.add(id);
                } while (c.moveToNext());
            }
        }

        return idCabecera;

    }

    private List<Pedidos> obtenerPedido(ArrayList<String> idcabecera) {
        List<Pedidos> pedidos = new ArrayList<>();

        Cursor c = null;
        for (String al : idcabecera) {
            c = DatabaseOperations.obtenerInstancia(getActivity()).obtenerPedidosSeleccionados(al);

            if (c.moveToFirst()) {
                do {
                    String id = c.getString(0);
                    String tipo = c.getString(1);
                    String fecha = c.getString(2);
                    String caja = c.getString(3);
                    String cliente = c.getString(4);
                    String articulo = c.getString(5);
                    String unidades = c.getString(6);
                    String fk_id_cabecera = c.getString(7);

                    pedidos.add(new Pedidos(tipo, id, fecha, caja, cliente, articulo, unidades, fk_id_cabecera));
                } while (c.moveToNext());
            }
        }

        return pedidos;
    }

    private void rellenar() {
        String tipo = "C";
        Cursor c = DatabaseOperations.obtenerInstancia(getActivity()).obtenerCabecera(tipo);
        if (c.moveToFirst()) {
            do {
                String fecha = c.getString(1);
                String caja = c.getString(2);
                String cliente = c.getString(3);
                String created = c.getString(4);

                pedidosHoraCreacions.add(new PedidosHoraCreacion(fecha, caja, cliente, created));
            } while (c.moveToNext());
        }
        adapter.notifyDataSetChanged();

    }


}
