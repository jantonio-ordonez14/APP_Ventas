package com.example.esmail.app_ventas;

import android.content.Context;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

import com.example.esmail.app_ventas.modelos.Cliente;
import com.example.esmail.app_ventas.sqlite.DatabaseOperations;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class CustomersImport {
    private final static String NOMBRE_DOCUMENTO = "productos.csv";
    private File f;
    private Context context;
    ;


    /**
     * Metodo que lee el fichero y
     * si esta  correcto rellena
     * la bbdd y la muestra en un listView
     */
    public void actionImport() {
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
                databaseOperations.insertarCliente(new Cliente(alAux.get(0), alAux.get(1)));

            }
            reader.close();


        } catch (Exception ex) {
            Toast.makeText(context, "Error al leer fichero", Toast.LENGTH_LONG).show();
            Log.e("Ficheros", ex.getMessage());
        }

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
