package com.example.esmail.app_ventas.modelos;

public class DetallePedido {
    private String id;
    private String tipo;
    private String caja;
    private String unidades;
    private String fk_id_cliente;
    private String fk_cod_articulo;

    public DetallePedido(String id, String tipo, String caja, String unidades, String fk_id_cliente, String fk_cod_articulo) {
        this.id = id;
        this.tipo = tipo;
        this.caja = caja;
        this.unidades = unidades;
        this.fk_id_cliente = fk_id_cliente;
        this.fk_cod_articulo = fk_cod_articulo;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getCaja() {
        return caja;
    }

    public void setCaja(String caja) {
        this.caja = caja;
    }

    public String getUnidades() {
        return unidades;
    }

    public void setUnidades(String unidades) {
        this.unidades = unidades;
    }

    public String getFk_id_cliente() {
        return fk_id_cliente;
    }

    public void setFk_id_cliente(String fk_id_cliente) {
        this.fk_id_cliente = fk_id_cliente;
    }

    public String getFk_cod_articulo() {
        return fk_cod_articulo;
    }

    public void setFk_cod_articulo(String fk_cod_articulo) {
        this.fk_cod_articulo = fk_cod_articulo;
    }
}
