package com.example.esmail.app_ventas.modelos;

public class Cliente {
    private String fk_cod_articulo;
    private String nombre;

    public Cliente(String fk_cod_articulo, String nombre) {
        this.fk_cod_articulo = fk_cod_articulo;
        this.nombre = nombre;
    }


    public String getFk_cod_articulo() {
        return fk_cod_articulo;
    }

    public void setFk_cod_articulo(String fk_cod_articulo) {
        this.fk_cod_articulo = fk_cod_articulo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}
