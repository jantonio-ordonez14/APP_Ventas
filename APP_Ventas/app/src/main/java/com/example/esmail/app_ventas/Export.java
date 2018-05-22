package com.example.esmail.app_ventas;

import android.content.Intent;
import android.database.Cursor;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.example.esmail.app_ventas.adapters.RecyclerViewAdapterCustomers;
import com.example.esmail.app_ventas.adapters.RecyclerViewAdapterExport;
import com.example.esmail.app_ventas.modelos.CabeceraPedido;
import com.example.esmail.app_ventas.modelos.Cliente;
import com.example.esmail.app_ventas.modelos.DetallePedido;
import com.example.esmail.app_ventas.modelos.Pedido;
import com.example.esmail.app_ventas.modelos.Pedidos;
import com.example.esmail.app_ventas.sqlite.DatabaseOperations;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

public class Export extends AppCompatActivity {

    private ArrayList<DetallePedido> alDetalle;
    private ArrayList<CabeceraPedido> alCabecera;
    private String NOMBRE_DOCUMENTO = "pedido.csv";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_export);
        alDetalle = new ArrayList<>();
        alCabecera = new ArrayList<>();

        Intent intent = getIntent();
        String idCabecera = intent.getStringExtra("id");
        Log.e("Export", idCabecera);

        hacerPedido(idCabecera);


        DatabaseOperations operations = DatabaseOperations.obtenerInstancia(this);
        operations.eliminarDetalle();
        operations.eliminarCabecera();

    }

    private void hacerPedido(String idCabecera) {
        ArrayList<Pedido> pedido = new ArrayList<>();
        ArrayList<CabeceraPedido> cabecera = obtenerDetallesCabecera(idCabecera);
        ArrayList<DetallePedido> detalle = obtenerDetallesPedido(idCabecera);

        for (int i = 0; i < cabecera.size(); i++) {
            pedido.add(new Pedido(cabecera.get(i).getTipo(), cabecera.get(i).getFecha(), cabecera.get(i).getCaja(),
                    cabecera.get(i).getFk_id_cliente(), "", ""));
            for (int j = 0; j < detalle.size(); j++) {
                pedido.add(new Pedido(detalle.get(j).getTipo(), "", "", "", detalle.get(j).getArticulo(), detalle.get(j).getUnidades()));
            }
        }

        for (Pedido pd :
                pedido) {
            System.out.println(pd.getTipo() + "\t" + pd.getFecha() + "\t" + pd.getCaja() + "\t" + pd.getCliente() + "\t" +
                    pd.getArticulo() + "\t" + pd.getUnidades());

            DatabaseOperations operations = DatabaseOperations.obtenerInstancia(this);
            operations.insertarPedidos(new Pedido(pd.getTipo(), pd.getFecha(), pd.getCaja(), pd.getCliente(), pd.getArticulo(), pd.getUnidades()));
        }

        obtenerPedido();
    }

    private void obtenerPedido() {
        ArrayList<Pedidos> pedidos = new ArrayList<>();
        DatabaseOperations operations = DatabaseOperations.obtenerInstancia(this);
        Cursor c = operations.obtenerPedidos();

        if (c.moveToFirst()) {
            do {
                String id = c.getString(0);
                String tipo = c.getString(1);
                String fecha = c.getString(2);
                String caja = c.getString(3);
                String cliente = c.getString(4);
                String articulo = c.getString(5);
                String unidades = c.getString(6);

                pedidos.add(new Pedidos(id, tipo, fecha, caja, cliente, articulo, unidades));
            } while (c.moveToNext());
        }


        //recycler view
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.rv_export);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(llm);
        //adaptador
        RecyclerViewAdapterExport adapter = new RecyclerViewAdapterExport(pedidos);
        recyclerView.setAdapter(adapter);

        try {
            //creamos un objeto file
            File f = leerFichero(NOMBRE_DOCUMENTO);

            OutputStreamWriter fout =
                    new OutputStreamWriter(
                            new FileOutputStream(f));


            // obtenemos los registros
            for (Pedidos pd : pedidos) {

                String datos = pd.getTipo() + "\t" + pd.getIdRegsitro() + "\t" + pd.getFecha() + "\t" + pd.getCaja()
                        + "\t" + pd.getCliente() + "\t" + pd.getArticulo() + "\t" + pd.getUnidades() + "\r\n";
                System.out.println(datos);
                // insertamos los registros en el archivo
                fout.write(datos);
                Toast.makeText(this, "Se ha exportado el pedido", Toast.LENGTH_SHORT).show();


            }
            fout.close();
        } catch (Exception ex) {
            Toast.makeText(this, "Error al escribir fichero", Toast.LENGTH_SHORT).show();

            Log.e("Ficheros", ex.getMessage());
        }
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
            System.out.println("TIPO->" + pedido.getTipo() + "\tFECHA->" + pedido.getFecha()
                    + "\tCAJA->" + pedido.getCaja() + "\tIDCLIENTE->" + pedido.getFk_id_cliente());
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
            System.out.println("TIPO->" + pedido.getTipo() + "\tARTICULO->" + pedido.getArticulo() + "\tUNIDADES->" +
                    pedido.getUnidades() + "\tIDCABECERA->" + pedido.getFk_id_cabecera());
        }
        System.out.println("*******************FIN************************");

        return alDetalle;
    }

    /**
     * creamos un fichero con la ruta obtenida
     *
     * @param nombreFichero
     * @return
     * @throws IOException
     */
    public static File leerFichero(String nombreFichero) throws IOException {
        File ruta = getRuta();
        File fichero = null;
        if (ruta != null)
            fichero = new File(ruta, nombreFichero);
        return fichero;
    }

    public static File getRuta() {

        // El fichero sera almacenado en un directorio dentro del directorio
        // Descargas
        File ruta = null;
        if (Environment.MEDIA_MOUNTED.equals(Environment
                .getExternalStorageState())) {
            ruta = new File(
                    String.valueOf(Environment
                            .getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)));

            if (ruta != null) {
                if (!ruta.mkdirs()) {
                    if (!ruta.exists()) {
                        return null;
                    }
                }
            }
        }

        return ruta;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(this,MainActivity.class));
        finish();
    }
}
