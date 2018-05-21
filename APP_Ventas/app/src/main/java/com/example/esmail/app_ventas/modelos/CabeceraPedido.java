package com.example.esmail.app_ventas.modelos;

public class CabeceraPedido {
    private String tipo;
    private String fecha;
    private String caja;
    private String fk_id_cliente;

    public CabeceraPedido(String tipo, String fecha, String caja, String fk_id_cliente) {
        this.tipo = tipo;
        this.fecha = fecha;
        this.caja = caja;
        this.fk_id_cliente = fk_id_cliente;
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

    public String getFk_id_cliente() {
        return fk_id_cliente;
    }
}
