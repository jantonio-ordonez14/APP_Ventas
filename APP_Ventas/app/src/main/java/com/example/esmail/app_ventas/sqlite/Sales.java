package com.example.esmail.app_ventas.sqlite;

import java.util.UUID;

/**
 * Clase que establece los nombres a usar en la base de datos
 */
public class Sales {

    interface ColumnasCabeceraPedido {
        String ID = "id";
        String FECHA = "fecha";
        String CAJA = "caja";
        String FK_ID_CLIENTE = "fk_id_cliente";
    }

    interface ColumnasDetallePedido {
        String ID = "id";
        String TIPO = "tipo";
        String CAJA = "caja";
        String FK_ID_CLIENTE = "fk_id_cliente";
        String FK_CODIGO_ARTICULO = "fk_codigo_articulo";
        String UNIDADES = "unidades";
    }

    interface ColumnasArticulo {
        String ID = "id";
        String COD_ARTICULO = "cod_articulo";
        String COD_BARRAS = "cod_barras";
        String DESCRIPCION = "descripcion";
        String UNIDADES = "unidades";
        String PRECIO = "precio";
        String IMPORTE = "importe";
    }

    interface ColumnasCliente {
        String ID = "id";
        String FK_CODIGO_ARTICULO = "fk_codigo_articulo";
        String NOMBRE = "nombre";
    }


    public static class CabecerasPedido implements ColumnasCabeceraPedido {
        public static String generarIdCabeceraPedido() {
            return "CP-" + UUID.randomUUID().toString();
        }
    }

    public static class DetallesPedido implements ColumnasDetallePedido {
        public static String generarIdDetallesPedido() {
            return "DP-" + UUID.randomUUID().toString();
        }
    }

    public static class Articulos implements ColumnasArticulo {
        public static String generarIdArticulo() {
            return "ART-" + UUID.randomUUID().toString();
        }
    }

    public static class Clientes implements ColumnasCliente {
        public static String generarIdCliente() {
            return "CLI-" + UUID.randomUUID().toString();
        }
    }


    private Sales() {
    }

}
