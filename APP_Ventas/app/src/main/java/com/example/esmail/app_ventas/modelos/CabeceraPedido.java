package com.example.esmail.app_ventas.modelos;

public class CabeceraPedido {
    private String id;
    private String fecha;
    private String caja;
    private String fk_id_cliente;

    public CabeceraPedido(String id, String fecha, String caja, String fk_id_cliente) {
        this.id = id;
        this.fecha = fecha;
        this.caja = caja;
        this.fk_id_cliente = fk_id_cliente;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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