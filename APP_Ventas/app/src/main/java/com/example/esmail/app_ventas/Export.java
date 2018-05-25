package com.example.esmail.app_ventas;

import android.content.Context;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.List;

public class Export {
    private String NOMBRE_DOCUMENTO = "pedido.csv";
    private List<Pedidos> pedidos;
    private Context context;

    public Export(List<Pedidos> pedidos, Context context) {
        this.pedidos = pedidos;
        this.context = context;
    }


    public void action() {
        try {
            //creamos un objeto file
            File f = leerFichero(NOMBRE_DOCUMENTO);

            OutputStreamWriter fout =
                    new OutputStreamWriter(
                            new FileOutputStream(f));

            // obtenemos los registros
            for (Pedidos pd : pedidos) {

                String datos = pd.getTipo() + "\t" + pd.getIdregistro() + "\t" + pd.getFecha() + "\t" + pd.getCaja()
                        + "\t" + pd.getCliente() + "\t" + pd.getArticulo() + "\t" + pd.getUnidades() + "\r\n";
                System.out.println(datos);
                // insertamos los registros en el archivo
                fout.write(datos);
                Toast.makeText(context, "Se ha exportado el pedido", Toast.LENGTH_SHORT).show();

            }
            fout.close();
        } catch (Exception ex) {
            Toast.makeText(context, "Error al escribir fichero", Toast.LENGTH_SHORT).show();

            Log.e("Ficheros", ex.getMessage());
        }
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

}
