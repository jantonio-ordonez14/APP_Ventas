package com.example.esmail.app_ventas;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.example.esmail.app_ventas.modelos.CabeceraPedido;
import com.example.esmail.app_ventas.modelos.DetallePedido;
import com.example.esmail.app_ventas.sqlite.DatabaseOperations;

import java.util.ArrayList;

public class Export extends AppCompatActivity {

    private ArrayList<DetallePedido> alDetalle;
    private ArrayList<CabeceraPedido> alCabecera;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_export);
        alDetalle = new ArrayList<>();
        alCabecera = new ArrayList<>();

        Intent intent = getIntent();
        String idCabecera = intent.getStringExtra("id");
        Log.e("Export", idCabecera);

        obtenerDetallesCabecera(idCabecera);
        obtenerDetallesPedido(idCabecera);

        DatabaseOperations operations = DatabaseOperations.obtenerInstancia(this);
        operations.eliminarDetalle();
        operations.eliminarCabecera();

        hacerPedido();
    }

    private void hacerPedido() {
        
    }

    private ArrayList<CabeceraPedido> obtenerDetallesCabecera(String idCabecera) {
        DatabaseOperations operations = DatabaseOperations.obtenerInstancia(this);
        Cursor c = operations.obtenerCabeceraId(idCabecera);

        if (c.moveToFirst()) {
            do {
                String tipo = c.getString(1);
                String fecha = c.getString(2);
                String caja = c.getString(3);
                String fk_id_cliente = c.getString(4);

                alCabecera.add(new CabeceraPedido(tipo, fecha, caja, fk_id_cliente));
            } while (c.moveToNext());
        }

        System.out.println("****************CABECERA**********************");
        for (CabeceraPedido pedido :
                alCabecera) {
            System.out.println("TIPO->"+pedido.getTipo()+"\tFECHA->"+pedido.getFecha()
            +"\tCAJA->"+pedido.getCaja()+"\tIDCLIENTE->"+pedido.getFk_id_cliente());
        }
        System.out.println("*******************FIN************************");

        return alCabecera;
    }

    private ArrayList<DetallePedido> obtenerDetallesPedido(String idCabecera) {
        DatabaseOperations operations = DatabaseOperations.obtenerInstancia(this);
        Cursor c = operations.obtenerDetallesId(idCabecera);

        if (c.moveToFirst()) {
            do {
                String id = c.getString(0);
                String tipo = c.getString(1);
                String articulo = c.getString(2);
                String unidades = c.getString(3);
                String fk_id_cabecera = c.getString(4);

                alDetalle.add(new DetallePedido(tipo, articulo, unidades, fk_id_cabecera));
            } while (c.moveToNext());
        }
        System.out.println("****************DETALLE**********************");

        for (DetallePedido pedido :
                alDetalle) {
            System.out.println("TIPO->"+pedido.getTipo()+"\tARTICULO->"+pedido.getArticulo()+"\tUNIDADES->"+
                    pedido.getUnidades()+"\tIDCABECERA->"+pedido.getFk_id_cabecera());
        }
        System.out.println("*******************FIN************************");

        return alDetalle;
    }
}
