package com.venta.ventapps.Entidades;

public class detalleVenta {
    int id;
    String numeroventa;
    int idProd;
    String nomProd;
    int cant;

    public detalleVenta() {
    }

    public detalleVenta(int id, String numeroventa, int idProd, String nomProd, int cant) {
        this.id = id;
        this.numeroventa = numeroventa;
        this.idProd = idProd;
        this.nomProd = nomProd;
        this.cant = cant;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNumeroventa() {
        return numeroventa;
    }

    public void setNumeroventa(String numeroventa) {
        this.numeroventa = numeroventa;
    }

    public int getIdProd() {
        return idProd;
    }

    public void setIdProd(int idProd) {
        this.idProd = idProd;
    }

    public String getNomProd() {
        return nomProd;
    }

    public void setNomProd(String nomProd) {
        this.nomProd = nomProd;
    }

    public int getCant() {
        return cant;
    }

    public void setCant(int cant) {
        this.cant = cant;
    }
}
