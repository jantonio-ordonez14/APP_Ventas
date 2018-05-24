package com.example.esmail.app_ventas.sqlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;
import android.provider.BaseColumns;

public class DataBasesSales extends SQLiteOpenHelper{
    private static final String NOMBRE_BASE_DATOS = "db_sales17.db";

    private static final int VERSION_ACTUAL = 1;

    private final Context contexto;

    interface Tablas {
        String CABECERA_PEDIDO = "cabecera_pedido";
        String DETALLE_PEDIDO = "detalle_pedido";
        String ARTICULOS = "articulos";
        String CLIENTE = "cliente";
        String PEDIDOS="pedidos";
    }

    interface Referencias {


        String COD_ERP = String.format("REFERENCES %s(%s)",
                Tablas.ARTICULOS, Sales.Articulos.COD_ERP_ARTICULO);
        String ID_CABECERA = String.format("REFERENCES %s(%s)",
                Tablas.CABECERA_PEDIDO, Sales.CabecerasPedido.ID);

    }

    public DataBasesSales(Context contexto) {
        super(contexto, NOMBRE_BASE_DATOS, null, VERSION_ACTUAL);
        this.contexto = contexto;
    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
        if (!db.isReadOnly()) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                db.setForeignKeyConstraintsEnabled(true);
            } else {
                db.execSQL("PRAGMA foreign_keys=ON");
            }
        }
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(String.format("CREATE TABLE %s (%s INTEGER PRIMARY KEY AUTOINCREMENT," +
                        "%s TEXT NOT NULL,%s DATETIME NOT NULL,%s TEXT," +
                        "%s TEXT NOT NULL)",
                Tablas.CABECERA_PEDIDO, BaseColumns._ID, Sales.CabecerasPedido.TIPO, Sales.CabecerasPedido.FECHA,
                Sales.CabecerasPedido.CAJA,Sales.CabecerasPedido.FK_ID_CLIENTE));

        db.execSQL(String.format("CREATE TABLE %s (%s INTEGER PRIMARY KEY AUTOINCREMENT," +
                        "%s TEXT, %s TEXT NOT NULL,%s REAL,%s INTEGER %s)",
                Tablas.DETALLE_PEDIDO, BaseColumns._ID,
                Sales.DetallesPedido.TIPO,
                Sales.DetallesPedido.ARTICULO,Sales.DetallesPedido.UNIDADES,
                Sales.DetallesPedido.FK_ID_CABECERA, Referencias.ID_CABECERA));

        db.execSQL(String.format("CREATE TABLE %s ( %s INTEGER PRIMARY KEY AUTOINCREMENT," +
                        "%s TEXT UNIQUE NOT NULL,%s TEXT UNIQUE NOT NULL ,%s TEXT,%s REAL," +
                        "%s REAL,%s REAL)",
                Tablas.ARTICULOS, BaseColumns._ID,
                Sales.Articulos.COD_ERP_ARTICULO, Sales.Articulos.COD_BARRAS, Sales.Articulos.DESCRIPCION,
                Sales.Articulos.UNIDADES,Sales.Articulos.PRECIO,Sales.Articulos.IMPORTE));

        db.execSQL(String.format("CREATE TABLE %s ( %s INTEGER PRIMARY KEY AUTOINCREMENT," +
                        "%s TEXT UNIQUE NOT NULL,%s TEXT NOT NULL)",
                Tablas.CLIENTE, BaseColumns._ID,
                Sales.Clientes.CODIGO_ERP_CLIENTE,Sales.Clientes.NOMBRE));

        db.execSQL(String.format("CREATE TABLE %s (%s INTEGER PRIMARY KEY AUTOINCREMENT," +
                        "%s TEXT,%s DATETIME,%s TEXT," +
                        "%s TEXT, %s ,%s REAL)",
                Tablas.PEDIDOS, BaseColumns._ID,
                Sales.Pedidos.TIPO, Sales.Pedidos.FECHA,
                Sales.Pedidos.CAJA,Sales.Pedidos.FK_ID_CLIENTE,
                Sales.DetallesPedido.ARTICULO,Sales.DetallesPedido.UNIDADES));
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS " + Tablas.CABECERA_PEDIDO);
        db.execSQL("DROP TABLE IF EXISTS " + Tablas.DETALLE_PEDIDO);
        db.execSQL("DROP TABLE IF EXISTS " + Tablas.ARTICULOS);
        db.execSQL("DROP TABLE IF EXISTS " + Tablas.CLIENTE);

        onCreate(db);
    }

}
