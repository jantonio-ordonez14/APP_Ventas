package com.example.esmail.app_ventas.modelos;

public class Articulo {
    private String id;
    private String cod_articulo;
    private String cod_barras;
    private String descripcion;
    private  String unidades;
    private String precio;
    private String importe;

    public Articulo(String id, String cod_articulo, String cod_barras, String descripcion, String unidades, String precio, String importe) {
        this.id = id;
        this.cod_articulo = cod_articulo;
        this.cod_barras = cod_barras;
        this.descripcion = descripcion;
        this.unidades = unidades;
        this.precio = precio;
        this.importe = importe;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCod_articulo() {
        return cod_articulo;
    }

    public void setCod_articulo(String cod_articulo) {
        this.cod_articulo = cod_articulo;
    }

    public String getCod_barras() {
        return cod_barras;
    }

    public void setCod_barras(String cod_barras) {
        this.cod_barras = cod_barras;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getUnidades() {
        return unidades;
    }

    public void setUnidades(String unidades) {
        this.unidades = unidades;
    }

    public String getPrecio() {
        return precio;
    }

    public void setPrecio(String precio) {
        this.precio = precio;
    }

    public String getImporte() {
        return importe;
    }

    public void setImporte(String importe) {
        this.importe = importe;
    }
}
