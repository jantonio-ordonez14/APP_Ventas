package com.example.esmail.app_ventas.modelos;

public class DetallePedido {
    private String caja;
    private String unidades;
    private String fk_id_cliente;
    private String fk_cod_barras;
    private String fk_id_cabecera;

    public DetallePedido(String caja, String unidades, String fk_id_cliente, String fk_cod_barras, String fk_id_cabecera) {
        this.caja = caja;
        this.unidades = unidades;
        this.fk_id_cliente = fk_id_cliente;
        this.fk_cod_barras = fk_cod_barras;
        this.fk_id_cabecera = fk_id_cabecera;
    }

    public String getCaja() {
        return caja;
    }

    public String getUnidades() {
        return unidades;
    }

    public String getFk_id_cliente() {
        return fk_id_cliente;
    }

    public String getFk_cod_barras() {
        return fk_cod_barras;
    }

    public String getFk_id_cabecera() {
        return fk_id_cabecera;
    }
}
