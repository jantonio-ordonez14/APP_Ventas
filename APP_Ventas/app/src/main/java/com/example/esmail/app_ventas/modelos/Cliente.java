package com.example.esmail.app_ventas.modelos;

public class Cliente {
    private String cod_articulo;
    private String nombre;

    public Cliente(String cod_articulo, String nombre) {
        this.cod_articulo = cod_articulo;
        this.nombre = nombre;
    }


    public String getCod_articulo() {
        return cod_articulo;
    }

    public String getNombre() {
        return nombre;
    }

}
