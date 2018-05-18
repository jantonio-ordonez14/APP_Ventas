package com.example.esmail.app_ventas.modelos;

public class CabeceraPedido {
    private String fecha;
    private String caja;
    private String fk_id_cliente;

    public CabeceraPedido(String fk_id_cliente,String fecha, String caja) {
        this.fk_id_cliente = fk_id_cliente;
        this.fecha = fecha;
        this.caja = caja;
    }


    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getCaja() {
        return caja;
    }

    public void setCaja(String caja) {
        this.caja = caja;
    }

    public String getFk_id_cliente() {
        return fk_id_cliente;
    }

    public void setFk_id_cliente(String fk_id_cliente) {
        this.fk_id_cliente = fk_id_cliente;
    }
}
