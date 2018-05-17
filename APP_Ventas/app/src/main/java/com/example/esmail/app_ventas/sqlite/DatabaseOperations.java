package com.example.esmail.app_ventas.sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.esmail.app_ventas.modelos.Articulo;
import com.example.esmail.app_ventas.modelos.Cliente;

public final class DatabaseOperations {

    /**
     * Clase auxiliar que implementa a {@link DatabaseOperations} para llevar a cabo el CRUD
     * sobre las entidades existentes.
     */


    private static DataBasesSales baseDatos;

    private static DatabaseOperations instancia = new DatabaseOperations();

    private DatabaseOperations() {
    }

    public static DatabaseOperations obtenerInstancia(Context contexto) {
        if (baseDatos == null) {
            baseDatos = new DataBasesSales(contexto);
        }
        return instancia;
    }

    // [OPERACIONES_articulo]
    public Cursor obtenerArticulos() {
        SQLiteDatabase db = baseDatos.getReadableDatabase();

        String sql = String.format("SELECT * FROM %s", DataBasesSales.Tablas.ARTICULOS);

        return db.rawQuery(sql, null);
    }

    public String insertarArticulos(Articulo articulo) {
        SQLiteDatabase db = baseDatos.getWritableDatabase();

        ContentValues valores = new ContentValues();
        // Generar Pk
        String idarticulo = Sales.Articulos.generarIdArticulo();
        valores.put(Sales.Articulos.COD_ARTICULO, articulo.getCod_articulo());
        valores.put(Sales.Articulos.COD_BARRAS, articulo.getCod_barras());
        valores.put(Sales.Articulos.DESCRIPCION, articulo.getDescripcion());
        valores.put(Sales.Articulos.UNIDADES, articulo.getUnidades());
        valores.put(Sales.Articulos.PRECIO, articulo.getPrecio());
        valores.put(Sales.Articulos.IMPORTE, articulo.getImporte());

        db.insertOrThrow(DataBasesSales.Tablas.ARTICULOS, null, valores);

        return idarticulo;

    }
/*
        public boolean actualizararticulo(Articulo articulo) {
            SQLiteDatabase db = baseDatos.getWritableDatabase();

            ContentValues valores = new ContentValues();
            valores.put(Sales.Articulos., articulo.nombre);

            String whereClause = String.format("%s=?", Sales.Articulos.ID);
            String[] whereArgs = {articulo.};

            int resultado = db.update(DataBasesSales.Tablas.articulo, valores, whereClause, whereArgs);

            return resultado > 0;
        }*/

    public boolean eliminararticulo(String codArticulo) {
        SQLiteDatabase db = baseDatos.getWritableDatabase();

        String whereClause = String.format("%s=?", Sales.Articulos.ID);
        String[] whereArgs = {codArticulo};

        int resultado = db.delete(DataBasesSales.Tablas.ARTICULOS, whereClause, whereArgs);

        return resultado > 0;
    }
    // [/OPERACIONES_articulo]

    // [OPERACIONES_CLIENTE]
    public Cursor obtenerClientes() {
        SQLiteDatabase db = baseDatos.getReadableDatabase();

        String sql = String.format("SELECT * FROM %s", DataBasesSales.Tablas.CLIENTE);

        return db.rawQuery(sql, null);
    }

    public String insertarCliente(Cliente cliente) {
        SQLiteDatabase db = baseDatos.getWritableDatabase();

        // Generar Pk
        String idCliente = Sales.Clientes.generarIdCliente();

        ContentValues valores = new ContentValues();
        valores.put(Sales.Clientes.NOMBRE, cliente.getNombre());
        valores.put(Sales.Clientes.FK_CODIGO_ARTICULO, cliente.getCod_articulo());

        return db.insertOrThrow(DataBasesSales.Tablas.CLIENTE, null, valores) > 0 ? idCliente : null;
    }

    public boolean actualizarCliente(Cliente cliente, String id) {
        SQLiteDatabase db = baseDatos.getWritableDatabase();

        ContentValues valores = new ContentValues();
        valores.put(Sales.Clientes.NOMBRE, cliente.getNombre());

        String whereClause = String.format("%s=?", Sales.Clientes.ID);
        final String[] whereArgs = {id};

        int resultado = db.update(DataBasesSales.Tablas.CLIENTE, valores, whereClause, whereArgs);

        return resultado > 0;
    }

    public boolean eliminarCliente(String idCliente) {
        SQLiteDatabase db = baseDatos.getWritableDatabase();

        String whereClause = String.format("%s=?", Sales.Clientes.ID);
        final String[] whereArgs = {idCliente};

        int resultado = db.delete(DataBasesSales.Tablas.CLIENTE, whereClause, whereArgs);

        return resultado > 0;
    }
// [/OPERACIONES_CLIENTE]


}
