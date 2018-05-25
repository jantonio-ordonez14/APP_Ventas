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

import com.example.esmail.app_ventas.adapters.RecyclerViewAdapterExport;
import com.example.esmail.app_ventas.modelos.CabeceraPedido;
import com.example.esmail.app_ventas.modelos.DetallePedido;
import com.example.esmail.app_ventas.modelos.Pedido;
import com.example.esmail.app_ventas.modelos.Exportados;
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





    }
/*

    private void obtenerPedido() {
        ArrayList<Pedido> pedidos = new ArrayList<>();
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
                String idcabecera=c.getString(7);

                pedidos.add(new Pedido( tipo, fecha, caja, cliente, articulo, unidades,idcabecera));
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
            for (Exportados pd : pedidos) {

                String datos = pd.getTipo() + "\t" + pd.getIdRegistro() + "\t" + pd.getFecha() + "\t" + pd.getCaja()
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

*/

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
        DatabaseOperations.obtenerInstancia(this).eliminarPedidos();
        startActivity(new Intent(this,MainActivity.class));
        finish();
    }
}
