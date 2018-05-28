package com.example.esmail.app_ventas;

public class Pedidos {
    private String tipo, idregistro,fecha,caja,cliente,articulo,unidades,fk_id_cabecera;

    public Pedidos(String tipo, String idregistro, String fecha, String caja, String cliente, String articulo, String unidades, String fk_id_cabecera) {
        this.tipo = tipo;
        this.idregistro = idregistro;
        this.fecha = fecha;
        this.caja = caja;
        this.cliente = cliente;
        this.articulo = articulo;
        this.unidades = unidades;
        this.fk_id_cabecera=fk_id_cabecera;
    }

    public String getTipo() {
        return tipo;
    }

    public String getIdregistro() {
        return idregistro;
    }

    public String getFecha() {
        return fecha;
    }

    public String getCaja() {
        return caja;
    }

    public String getCliente() {
        return cliente;
    }

    public String getArticulo() {
        return articulo;
    }

    public String getUnidades() {
        return unidades;
    }

    public String getFk_id_cabecera() {
        return fk_id_cabecera;
    }
}
