package com.example.esmail.app_ventas.imports;

import android.content.Context;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

import com.example.esmail.app_ventas.modelos.Articulo;
import com.example.esmail.app_ventas.sqlite.DatabaseOperations;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class ProductsImport {
    private String NOMBRE_DOCUMENTO;
    private File f;
    private Context context;

    public ProductsImport(String NOMBRE_DOCUMENTO, Context context) {
        this.NOMBRE_DOCUMENTO = NOMBRE_DOCUMENTO;
        this.context = context;
    }

    /**
     * Metodo que lee el fichero y
     * si esta  correcto rellena
     * la bbdd y la muestra en un listView
     */
    public boolean actionImport() {
        try {
            f = leerFichero(NOMBRE_DOCUMENTO);

            BufferedReader reader;
            reader = new BufferedReader(new InputStreamReader(new FileInputStream(f)));
            String csvLine;

            while ((csvLine = reader.readLine()) != null) {
                String[] row = csvLine.split("\t");
                System.out.println("ARRAY");
                for (int i = 0; i < row.length; i++) {
                    System.out.println(row[i]);
                }
                System.out.println("FIN");
                ArrayList<String> alAux = new ArrayList<>();
                for (int i = 0; i < row.length; i++) {
                    alAux.add(row[i]);
                }
                DatabaseOperations databaseOperations = DatabaseOperations.obtenerInstancia(context);
                    if (databaseOperations.consultarArticulo(alAux.get(1))){
                    databaseOperations.actualizarArticulo(alAux.get(2),alAux.get(3),alAux.get(4),alAux.get(5),alAux.get(1));
                }else
                databaseOperations.insertarArticulos(new Articulo(alAux.get(0), alAux.get(1),alAux.get(2),alAux.get(3),alAux.get(4),alAux.get(5)));

            }
            reader.close();
            return true;

        } catch (Exception ex) {
            Toast.makeText(context, "Error al leer fichero", Toast.LENGTH_LONG).show();
            Log.e("Ficheros", ex.getMessage());
        }

        return false;
    }


    /**
     * Crea un fichero y obtiene la ruta
     *
     * @param nombreFichero
     * @return
     * @throws IOException
     */
    public static File leerFichero(String nombreFichero) throws IOException {
        File ruta = getRuta();
        System.out.println(ruta);
        File fichero = null;
        if (ruta != null)
            fichero = new File(ruta, nombreFichero);
        return fichero;
    }

    /**
     * Metodo para obtener la ruta
     *
     * @return
     */
    public static File getRuta() {

        // El fichero ser? almacenado en un directorio dentro del directorio
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
        } else {
        }

        return ruta;
    }
}
