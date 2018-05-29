package com.example.esmail.app_ventas.sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.example.esmail.app_ventas.modelos.Articulo;
import com.example.esmail.app_ventas.modelos.CabeceraPedido;
import com.example.esmail.app_ventas.modelos.Cliente;
import com.example.esmail.app_ventas.modelos.DetallePedido;
import com.example.esmail.app_ventas.modelos.Exportados;
import com.example.esmail.app_ventas.modelos.Pedido;

public final class DatabaseOperations {

    /**
     * Clase auxiliar que implementa a {@link DatabaseOperations} para llevar a cabo el CRUD
     * sobre las entidades existentes.
     */


    private static DataBasesSales baseDatos;
    private static String idCabecera;


    private static DatabaseOperations instancia = new DatabaseOperations();

    private DatabaseOperations() {
    }

    public static DatabaseOperations obtenerInstancia(Context contexto) {
        if (baseDatos == null) {
            baseDatos = new DataBasesSales(contexto);
        }
        return instancia;


    }

    // [OPERACIONES_EXPORTADO]
    public Cursor obtenerExportado() {
        SQLiteDatabase db = baseDatos.getReadableDatabase();

        String sql = String.format("SELECT * FROM %s", DataBasesSales.Tablas.EXPORTADOS);

        return db.rawQuery(sql, null);

    }

    public Cursor obtenerExportadoCabecera(String tipo) {
        SQLiteDatabase db = baseDatos.getWritableDatabase();

        // Columnas
        String[] projection = {
                Sales.Exportado.TIPO,
                Sales.Exportado.FECHA,
                Sales.Exportado.CAJA,
                Sales.Exportado.ID_CLIENTE

        };

        // Filter results WHERE "title" = 'My Title'
        String selection = Sales.Exportado.TIPO + " = ?";
        String[] selectionArgs = {tipo};

        // How you want the results sorted in the resulting Cursor
        String sortOrder =
                Sales.ColumnasDetallePedido.ID + " ASC";

        return db.query(
                DataBasesSales.Tablas.EXPORTADOS,                            // tabla
                projection,                                 // columnas
                selection,                              // columnas para la clausula WHERE
                selectionArgs,                           // valores para la clausula WHERE
                null,
                null,
                sortOrder                                   // The sort order
        );
    }

    public String insertarExportado(Exportados exportado) {
        SQLiteDatabase db = baseDatos.getWritableDatabase();

        // Generar Pk
        String idExportado;

        ContentValues valores = new ContentValues();
        valores.put(Sales.Exportado.IDREGISTRO, exportado.getIdRegistro());
        valores.put(Sales.Exportado.TIPO, exportado.getTipo());
        valores.put(Sales.Exportado.FECHA, exportado.getFecha());
        valores.put(Sales.Exportado.CAJA, exportado.getCaja());
        valores.put(Sales.Exportado.ID_CLIENTE, exportado.getCliente());
        valores.put(Sales.Exportado.ARTICULO, exportado.getArticulo());
        valores.put(Sales.Exportado.UNIDADES, exportado.getUnidades());
        valores.put(Sales.Exportado.FK_ID_CABECERA, exportado.getFk_id_cabecera());

        long id = db.insertOrThrow(DataBasesSales.Tablas.EXPORTADOS, null, valores);
        idExportado = String.valueOf(id);

        return idExportado;
    }

    public Boolean eliminarExportado() {
        try {
            SQLiteDatabase db = baseDatos.getWritableDatabase();
            db.delete(DataBasesSales.Tablas.EXPORTADOS, null, null);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    // [/OPERACIONES_EXPORTADO]

    // [OPERACIONES_PEDIDOS]
    public Cursor obtenerPedidos() {
        SQLiteDatabase db = baseDatos.getReadableDatabase();

        String sql = String.format("SELECT * FROM %s", DataBasesSales.Tablas.PEDIDOS);

        return db.rawQuery(sql, null);

    }

    public Cursor obtenerCabecera(String tipo) {
        SQLiteDatabase db = baseDatos.getWritableDatabase();

        // Columnas
        String[] projection = {
                Sales.Pedidos.TIPO,
                Sales.Pedidos.FECHA,
                Sales.Pedidos.CAJA,
                Sales.Pedidos.FK_ID_CLIENTE,
                Sales.Pedidos.FECHA_Y_HORA

        };

        // Filter results WHERE "title" = 'My Title'
        String selection = Sales.Pedidos.TIPO + " = ?";
        String[] selectionArgs = {tipo};

        // How you want the results sorted in the resulting Cursor
        String sortOrder =
                Sales.ColumnasDetallePedido.ID + " ASC";

        return db.query(
                DataBasesSales.Tablas.PEDIDOS,                            // tabla
                projection,                                 // columnas
                selection,                              // columnas para la clausula WHERE
                selectionArgs,                           // valores para la clausula WHERE
                null,
                null,
                sortOrder                                   // The sort order
        );
    }

    public Cursor obtenerPedidosSeleccionados(String idCabecera) {
        SQLiteDatabase db = baseDatos.getWritableDatabase();

        // Columnas
        String[] projection = {
                Sales.Pedidos.ID,
                Sales.Pedidos.TIPO,
                Sales.Pedidos.FECHA,
                Sales.Pedidos.CAJA,
                Sales.Pedidos.FK_ID_CLIENTE,
                Sales.Pedidos.ARTICULO,
                Sales.Pedidos.UNIDADES,
                Sales.Pedidos.FK_ID_CABECERA

        };

        // Filter results WHERE "title" = 'My Title'
        String selection = Sales.Pedidos.FK_ID_CABECERA + " = ?";
        String[] selectionArgs = {idCabecera};

        // How you want the results sorted in the resulting Cursor
        String sortOrder =
                Sales.ColumnasDetallePedido.ID + " ASC";

        return db.query(
                DataBasesSales.Tablas.PEDIDOS,                            // tabla
                projection,                                 // columnas
                selection,                              // columnas para la clausula WHERE
                selectionArgs,                           // valores para la clausula WHERE
                null,
                null,
                sortOrder                                   // The sort order
        );
    }

    public Cursor obtenerIdCabecera(String horaFecha) {
        SQLiteDatabase db = baseDatos.getWritableDatabase();

        // Columnas
        String[] projection = {
                Sales.Pedidos.FK_ID_CABECERA
        };

        // Filter results WHERE "title" = 'My Title'
        String selection = Sales.Pedidos.FECHA_Y_HORA + " = ?";
        String[] selectionArgs = {horaFecha};

        // How you want the results sorted in the resulting Cursor
        String sortOrder =
                Sales.ColumnasDetallePedido.ID + " ASC";

        return db.query(
                DataBasesSales.Tablas.PEDIDOS,                            // tabla
                projection,                                 // columnas
                selection,                              // columnas para la clausula WHERE
                selectionArgs,                           // valores para la clausula WHERE
                null,
                null,
                sortOrder                                   // The sort order
        );
    }

    public String insertarPedidos(Pedido pedido) {
        SQLiteDatabase db = baseDatos.getWritableDatabase();

        // Generar Pk
        String idPedido;

        ContentValues valores = new ContentValues();
        valores.put(Sales.Pedidos.TIPO, pedido.getTipo());
        valores.put(Sales.Pedidos.FECHA, pedido.getFecha());
        valores.put(Sales.Pedidos.CAJA, pedido.getCaja());
        valores.put(Sales.Pedidos.FK_ID_CLIENTE, pedido.getCliente());
        valores.put(Sales.Pedidos.ARTICULO, pedido.getArticulo());
        valores.put(Sales.Pedidos.UNIDADES, pedido.getUnidades());
        valores.put(Sales.Pedidos.FK_ID_CABECERA, pedido.getFk_id_cabecera());
        valores.put(Sales.Pedidos.FECHA_Y_HORA, pedido.getCreacion());

        long id = db.insertOrThrow(DataBasesSales.Tablas.PEDIDOS, null, valores);
        idPedido = String.valueOf(id);

        return idPedido;
    }

    public Boolean eliminarPedidos() {
        try {
            SQLiteDatabase db = baseDatos.getWritableDatabase();
            db.delete(DataBasesSales.Tablas.PEDIDOS, null, null);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    public boolean eliminarPedidoExportado(String idCabecera) {
        SQLiteDatabase db = baseDatos.getWritableDatabase();

        String whereClause = String.format("%s=?", Sales.Pedidos.FK_ID_CABECERA);
        String[] whereArgs = {idCabecera};

        int resultado = db.delete(DataBasesSales.Tablas.PEDIDOS, whereClause, whereArgs);

        return resultado > 0;

    }

    // [/OPERACIONES_PEDIDOS]

    // [OPERACIONES_DETALLES]
    public Cursor obtenerDetalles() {
        SQLiteDatabase db = baseDatos.getReadableDatabase();

        String sql = String.format("SELECT * FROM %s", DataBasesSales.Tablas.DETALLE_PEDIDO);

        return db.rawQuery(sql, null);
    }

    public Cursor obtenerDetallesId(String idCabecera) {
        SQLiteDatabase db = baseDatos.getReadableDatabase();

        // Columnas
        String[] projection = {
                Sales.ColumnasDetallePedido.ID,
                Sales.ColumnasDetallePedido.TIPO,
                Sales.ColumnasDetallePedido.ARTICULO,
                Sales.ColumnasDetallePedido.UNIDADES,
                Sales.ColumnasDetallePedido.FK_ID_CABECERA

        };

        // Filter results WHERE "title" = 'My Title'
        String selection = Sales.ColumnasDetallePedido.FK_ID_CABECERA + " = ?";
        String[] selectionArgs = {idCabecera};

        // How you want the results sorted in the resulting Cursor
        String sortOrder =
                Sales.ColumnasDetallePedido.ID + " ASC";

        return db.query(
                DataBasesSales.Tablas.DETALLE_PEDIDO,                            // tabla
                projection,                                 // columnas
                selection,                              // columnas para la clausula WHERE
                selectionArgs,                           // valores para la clausula WHERE
                null,
                null,
                sortOrder                                   // The sort order
        );


    }

    public String insertarDetalles(DetallePedido detallePedido) {
        SQLiteDatabase db = baseDatos.getWritableDatabase();

        // Generar Pk
        String idDetalle;

        ContentValues valores = new ContentValues();
        valores.put(Sales.DetallesPedido.TIPO, detallePedido.getTipo());
        valores.put(Sales.DetallesPedido.ARTICULO, detallePedido.getArticulo());
        valores.put(Sales.DetallesPedido.UNIDADES, detallePedido.getUnidades());
        valores.put(Sales.DetallesPedido.FK_ID_CABECERA, detallePedido.getFk_id_cabecera());
        long id = db.insertOrThrow(DataBasesSales.Tablas.DETALLE_PEDIDO, null, valores);
        idDetalle = String.valueOf(id);

        return idDetalle;
    }

    public Boolean consultarDetalle(String codBarras) {
        SQLiteDatabase db = baseDatos.getReadableDatabase();

        // Columnas
        String[] projection = {
                Sales.DetallesPedido.ARTICULO
        };

        // Filter results WHERE "title" = 'My Title'
        String selection = Sales.DetallesPedido.ARTICULO + " = ?";
        String[] selectionArgs = {codBarras};

        // How you want the results sorted in the resulting Cursor
        String sortOrder =
                Sales.Articulos.ID + " ASC";

        Cursor c = db.query(
                DataBasesSales.Tablas.DETALLE_PEDIDO,                            // tabla
                projection,                                 // columnas
                selection,                              // columnas para la clausula WHERE
                selectionArgs,                           // valores para la clausula WHERE
                null,
                null,
                sortOrder                                   // The sort order
        );
        String valor = "";
        if (c.moveToFirst()) {
            //Recorremos el cursor hasta que no haya más registros
            do {
                valor = c.getString(0);

            } while (c.moveToNext());
        }
        if (codBarras.length() == valor.length()) {
            return true;
        } else return false;
    }

    public boolean actualizarArticuloDetalles(DetallePedido detallePedido) {
        SQLiteDatabase db = baseDatos.getWritableDatabase();

        ContentValues valores = new ContentValues();
        valores.put(Sales.DetallesPedido.TIPO, detallePedido.getTipo());
        valores.put(Sales.DetallesPedido.ARTICULO, detallePedido.getArticulo());
        valores.put(Sales.DetallesPedido.UNIDADES, detallePedido.getUnidades());
        valores.put(Sales.DetallesPedido.FK_ID_CABECERA, detallePedido.getFk_id_cabecera());

        String whereClause = String.format("%s=?", Sales.DetallesPedido.ARTICULO);
        final String[] whereArgs = {detallePedido.getArticulo()};

        int resultado = db.update(DataBasesSales.Tablas.DETALLE_PEDIDO, valores, whereClause, whereArgs);

        return resultado > 0;
    }

    public boolean eliminarDetalle() {
        try {
            SQLiteDatabase db = baseDatos.getWritableDatabase();
            db.delete(DataBasesSales.Tablas.DETALLE_PEDIDO, null, null);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }
    // [/OPERACIONES_DETALLE]

    // [OPERACIONES_cabecera]
    public Cursor obtenerCabecera() {
        SQLiteDatabase db = baseDatos.getReadableDatabase();

        String sql = String.format("SELECT * FROM %s", DataBasesSales.Tablas.CABECERA_PEDIDO);

        return db.rawQuery(sql, null);
    }

    public Cursor obtenerCabeceraId(String idCabecera) {
        SQLiteDatabase db = baseDatos.getReadableDatabase();

        // Columnas
        String[] projection = {
                Sales.ColumnasCabeceraPedido.ID,
                Sales.ColumnasCabeceraPedido.TIPO,
                Sales.ColumnasCabeceraPedido.FECHA,
                Sales.ColumnasCabeceraPedido.CAJA,
                Sales.ColumnasCabeceraPedido.FK_ID_CLIENTE

        };

        // Filter results WHERE "title" = 'My Title'
        String selection = Sales.ColumnasCabeceraPedido.ID + " = ?";
        String[] selectionArgs = {idCabecera};

        // How you want the results sorted in the resulting Cursor
        String sortOrder =
                Sales.ColumnasDetallePedido.ID + " ASC";

        return db.query(
                DataBasesSales.Tablas.CABECERA_PEDIDO,                            // tabla
                projection,                                 // columnas
                selection,                              // columnas para la clausula WHERE
                selectionArgs,                           // valores para la clausula WHERE
                null,
                null,
                sortOrder                                   // The sort order
        );
    }

    public String insertarCabecera(CabeceraPedido cabeceraPedido) throws SQLException {
        SQLiteDatabase db = baseDatos.getWritableDatabase();

        ContentValues valores = new ContentValues();
        // Generar Pk
        valores.put(Sales.CabecerasPedido.TIPO, cabeceraPedido.getTipo());
        valores.put(Sales.CabecerasPedido.FECHA, cabeceraPedido.getFecha());
        valores.put(Sales.CabecerasPedido.CAJA, cabeceraPedido.getCaja());
        valores.put(Sales.CabecerasPedido.FK_ID_CLIENTE, cabeceraPedido.getFk_id_cliente());

        long id = db.insertOrThrow(DataBasesSales.Tablas.CABECERA_PEDIDO, null, valores);
        String idcabecera = String.valueOf(id);
        return idcabecera;

    }


    public boolean eliminarCabecera() {
        try {
            SQLiteDatabase db = baseDatos.getWritableDatabase();
            db.delete(DataBasesSales.Tablas.CABECERA_PEDIDO, null, null);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }
    // [/OPERACIONES_cabecera]


    // [OPERACIONES_articulo]
    public Cursor obtenerArticulos() {
        SQLiteDatabase db = baseDatos.getReadableDatabase();

        String sql = String.format("SELECT * FROM %s", DataBasesSales.Tablas.ARTICULOS);

        return db.rawQuery(sql, null);
    }

    public Boolean consultarArticulo(String codBarras) {
        SQLiteDatabase db = baseDatos.getReadableDatabase();

        // Columnas
        String[] projection = {
                Sales.ColumnasArticulo.COD_BARRAS
        };

        // Filter results WHERE "title" = 'My Title'
        String selection = Sales.ColumnasArticulo.COD_BARRAS + " = ?";
        String[] selectionArgs = {codBarras};

        // How you want the results sorted in the resulting Cursor
        String sortOrder =
                Sales.Articulos.ID + " ASC";

        Cursor c = db.query(
                DataBasesSales.Tablas.ARTICULOS,                            // tabla
                projection,                                 // columnas
                selection,                              // columnas para la clausula WHERE
                selectionArgs,                           // valores para la clausula WHERE
                null,
                null,
                sortOrder                                   // The sort order
        );
        String valor = "";
        if (c.moveToFirst()) {
            //Recorremos el cursor hasta que no haya más registros
            do {
                valor = c.getString(0);

            } while (c.moveToNext());
        }
        if (codBarras.length() == valor.length()) {
            return true;
        } else return false;
    }

    public String insertarArticulos(Articulo articulo) {
        SQLiteDatabase db = baseDatos.getWritableDatabase();

        ContentValues valores = new ContentValues();
        // Generar Pk
        String idarticulo = Sales.Articulos.generarIdArticulo();
        valores.put(Sales.Articulos.COD_ERP_ARTICULO, articulo.getCod_articulo());
        valores.put(Sales.Articulos.COD_BARRAS, articulo.getCod_barras());
        valores.put(Sales.Articulos.DESCRIPCION, articulo.getDescripcion());
        valores.put(Sales.Articulos.UNIDADES, articulo.getUnidades());
        valores.put(Sales.Articulos.PRECIO, articulo.getPrecio());
        valores.put(Sales.Articulos.IMPORTE, articulo.getImporte());

        db.insertOrThrow(DataBasesSales.Tablas.ARTICULOS, null, valores);

        return idarticulo;

    }

    public boolean actualizarArticulo(String descripcion, String unidades, String precio, String importe, String cBarras) {
        SQLiteDatabase db = baseDatos.getWritableDatabase();

        ContentValues valores = new ContentValues();
        valores.put(Sales.Articulos.DESCRIPCION, descripcion);
        valores.put(Sales.Articulos.UNIDADES, unidades);
        valores.put(Sales.Articulos.PRECIO, precio);
        valores.put(Sales.Articulos.IMPORTE, importe);

        String whereClause = String.format("%s=?", Sales.Articulos.COD_BARRAS);
        final String[] whereArgs = {cBarras};

        int resultado = db.update(DataBasesSales.Tablas.ARTICULOS, valores, whereClause, whereArgs);

        return resultado > 0;
    }

    public boolean eliminararticulo(String codArticulo) {
        SQLiteDatabase db = baseDatos.getWritableDatabase();

        String whereClause = String.format("%s=?", Sales.Articulos.ID);
        String[] whereArgs = {codArticulo};

        int resultado = db.delete(DataBasesSales.Tablas.ARTICULOS, whereClause, whereArgs);

        return resultado > 0;

    }

    public boolean eliminarArticulos() {
        try {
            SQLiteDatabase db = baseDatos.getWritableDatabase();
            db.delete(DataBasesSales.Tablas.ARTICULOS, null, null);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }
    // [/OPERACIONES_articulo]

    // [OPERACIONES_CLIENTE]
    public Cursor obtenerClientes() {
        SQLiteDatabase db = baseDatos.getReadableDatabase();

        String sql = String.format("SELECT * FROM %s ORDER BY %s", DataBasesSales.Tablas.CLIENTE, Sales.Clientes.NOMBRE);

        return db.rawQuery(sql, null);
    }

    public String insertarCliente(Cliente cliente) {
        SQLiteDatabase db = baseDatos.getWritableDatabase();

        // Generar Pk
        String idCliente = Sales.Clientes.generarIdCliente();

        ContentValues valores = new ContentValues();
        valores.put(Sales.Clientes.CODIGO_ERP_CLIENTE, cliente.getCod_articulo());
        valores.put(Sales.Clientes.NOMBRE, cliente.getNombre());
        db.insertOrThrow(DataBasesSales.Tablas.CLIENTE, null, valores);
        return idCliente;
    }

    public Boolean consultarCliente(String codigo) {
        SQLiteDatabase db = baseDatos.getReadableDatabase();

        // Columnas
        String[] projection = {
                Sales.ColumnasCliente.CODIGO_ERP_CLIENTE
        };

        // Filter results WHERE "title" = 'My Title'
        String selection = Sales.ColumnasCliente.CODIGO_ERP_CLIENTE + " = ?";
        String[] selectionArgs = {codigo};

        // How you want the results sorted in the resulting Cursor
        String sortOrder =
                Sales.Clientes.ID + " ASC";

        Cursor c = db.query(
                DataBasesSales.Tablas.CLIENTE,                            // tabla
                projection,                                 // columnas
                selection,                              // columnas para la clausula WHERE
                selectionArgs,                           // valores para la clausula WHERE
                null,
                null,
                sortOrder                                   // The sort order
        );
        String valor = "";
        if (c.moveToFirst()) {
            //Recorremos el cursor hasta que no haya más registros
            do {
                valor = c.getString(0);

            } while (c.moveToNext());
        }
        if (codigo.length() == valor.length()) {
            return true;
        } else return false;
    }

    public boolean actualizarCliente(String nombre, String id) {
        SQLiteDatabase db = baseDatos.getWritableDatabase();

        ContentValues valores = new ContentValues();
        valores.put(Sales.Clientes.NOMBRE, nombre);

        String whereClause = String.format("%s=?", Sales.Clientes.CODIGO_ERP_CLIENTE);
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

    public boolean eliminarClientes() {
        try {
            SQLiteDatabase db = baseDatos.getWritableDatabase();
            db.delete(DataBasesSales.Tablas.CLIENTE, null, null);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }


// [/OPERACIONES_CLIENTE]


}
