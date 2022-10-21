package com.venta.ventapps.Entidades;

import android.graphics.Bitmap;

public class Productos {
    int id;
    String nombre;
    Bitmap img;
    int cantidad;
    String codigo;
    double preciov;
    String categoria;
    String descripcion;

    public Productos() {
    }

    public Productos(int id, String nombre, Bitmap img, int cantidad, String codigo, double preciov, String categoria, String descripcion) {
        this.id = id;
        this.nombre = nombre;
        this.img = img;
        this.cantidad = cantidad;
        this.codigo = codigo;
        this.preciov = preciov;
        this.categoria = categoria;
        this.descripcion = descripcion;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Bitmap getImg() {
        return img;
    }

    public void setImg(Bitmap img) {
        this.img = img;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public double getPreciov() {
        return preciov;
    }

    public void setPreciov(double preciov) {
        this.preciov = preciov;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
}
