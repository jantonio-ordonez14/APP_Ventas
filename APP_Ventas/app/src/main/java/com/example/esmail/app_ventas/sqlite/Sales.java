package com.example.esmail.app_ventas.sqlite;

import java.util.UUID;

/**
 * Clase que establece los nombres a usar en la base de datos
 */
public class Sales {

    interface ColumnasCabeceraPedido {
        String ID = "_id";
        String TIPO = "tipo";
        String FECHA = "fecha";
        String CAJA = "caja";
        String FK_ID_CLIENTE = "fk_id_cliente";
    }

    interface ColumnasDetallePedido {
        String ID = "_id";
        String TIPO = "tipo";
        String ARTICULO = "articulo";
        String UNIDADES = "unidades";
        String FK_ID_CABECERA = "fk_id_cabecera";
    }

    interface ColumnasArticulo {
        String ID = "_id";
        String COD_ERP_ARTICULO = "cod_erp_articulo";
        String COD_BARRAS = "cod_barras";
        String DESCRIPCION = "descripcion";
        String UNIDADES = "unidades";
        String PRECIO = "precio";
        String IMPORTE = "importe";
    }

    interface ColumnasCliente {
        String ID = "_id";
        String CODIGO_ERP_CLIENTE = "codigo_erp_cliente";
        String NOMBRE = "nombre";
    }

    interface ColumnasPedido {
        String ID = "_id";
        String TIPO = "tipo";
        String FECHA = "fecha";
        String CAJA = "caja";
        String FK_ID_CLIENTE = "fk_id_cliente";
        String ARTICULO = "articulo";
        String UNIDADES = "unidades";
        String FK_ID_CABECERA = "fk_id_cabecera";
        String FECHA_Y_HORA = "fecha_y_hora";
    }

    interface ColumnasExportado {
        String IDREGISTRO = "id_registro";
        String TIPO = "tipo";
        String FECHA = "fecha";
        String CAJA = "caja";
        String ID_CLIENTE = "id_cliente";
        String ARTICULO = "articulo";
        String UNIDADES = "unidades";
        String FK_ID_CABECERA = "fk_id_cabecera";

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

    public static class Pedidos implements ColumnasPedido {
        public static String generarIdPedidos() {
            return "PED-" + UUID.randomUUID().toString();
        }
    }

    public static class Exportado implements ColumnasExportado {
        public static String generarIdExportados() {
            return "EXP-" + UUID.randomUUID().toString();
        }
    }

    private Sales() {
    }

}
