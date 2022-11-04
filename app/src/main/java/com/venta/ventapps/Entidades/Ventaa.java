package com.venta.ventapps.Entidades;

public class Ventaa {
    String numV;
    String fecha;
    double monto;
    int idcliente;
    String cliente;
    String metodoPago;

    public Ventaa() {
    }

    public Ventaa(String numV, String fecha, double monto,int idcliente, String cliente, String metodoPago) {
        this.numV = numV;
        this.fecha = fecha;
        this.monto = monto;
        this.idcliente= idcliente;
        this.cliente = cliente;
        this.metodoPago = metodoPago;
    }

    public int getIdcliente() {
        return idcliente;
    }

    public void setIdcliente(int idcliente) {
        this.idcliente = idcliente;
    }

    public String getNumV() {
        return numV;
    }

    public void setNumV(String numV) {
        this.numV = numV;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public double getMonto() {
        return monto;
    }

    public void setMonto(double monto) {
        this.monto = monto;
    }

    public String getCliente() {
        return cliente;
    }

    public void setCliente(String cliente) {
        this.cliente = cliente;
    }

    public String getMetodoPago() {
        return metodoPago;
    }

    public void setMetodoPago(String metodoPago) {
        this.metodoPago = metodoPago;
    }
}
