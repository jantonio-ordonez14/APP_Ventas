package com.example.esmail.app_ventas.modelos;

public class Pedidos {
    private String idRegsitro, tipo, fecha, caja, cliente, articulo, unidades;

    public Pedidos(String idRegsitro, String tipo, String fecha, String caja, String cliente, String articulo, String unidades) {
        this.idRegsitro = idRegsitro;
        this.tipo = tipo;
        this.fecha = fecha;
        this.caja = caja;
        this.cliente = cliente;
        this.articulo = articulo;
        this.unidades = unidades;
    }

    public String getIdRegsitro() {
        return idRegsitro;
    }

    public String getTipo() {
        return tipo;
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
