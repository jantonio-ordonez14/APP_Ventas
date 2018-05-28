package com.example.esmail.app_ventas;

public class PedidosHoraCreacion {
    String fecha, caja, cliente, hora_creacion;

    public PedidosHoraCreacion(String fecha, String caja, String cliente, String hora_creacion) {

        this.fecha = fecha;
        this.caja = caja;
        this.cliente = cliente;
        this.hora_creacion = hora_creacion;
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

    public String getHora_creacion() {
        return hora_creacion;
    }
}
