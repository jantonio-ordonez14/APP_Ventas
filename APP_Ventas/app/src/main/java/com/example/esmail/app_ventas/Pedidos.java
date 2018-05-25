package com.example.esmail.app_ventas;

public class Pedidos {
    private String tipo, idregistro,fecha,caja,cliente,articulo,unidades;

    public Pedidos(String tipo, String idregistro, String fecha, String caja, String cliente, String articulo, String unidades) {
        this.tipo = tipo;
        this.idregistro = idregistro;
        this.fecha = fecha;
        this.caja = caja;
        this.cliente = cliente;
        this.articulo = articulo;
        this.unidades = unidades;
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
}
