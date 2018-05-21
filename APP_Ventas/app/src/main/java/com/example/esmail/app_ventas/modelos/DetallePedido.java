package com.example.esmail.app_ventas.modelos;

public class DetallePedido {
    private String tipo;
    private String articulo;
    private String unidades;
    private String fk_id_cabecera;

    public DetallePedido(String tipo, String articulo, String unidades, String fk_id_cabecera) {
        this.tipo = tipo;
        this.articulo = articulo;
        this.unidades = unidades;
        this.fk_id_cabecera = fk_id_cabecera;
    }

    public String getTipo() {
        return tipo;
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
