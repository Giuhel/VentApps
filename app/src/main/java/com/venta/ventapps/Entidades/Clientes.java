package com.venta.ventapps.Entidades;

public class Clientes {
    private int id;
    private String nombre;
    private String tipodoc;
    private String documento;
    private String telefono;
    private String correo;

    public Clientes() {
    }

    public Clientes(int id, String nombre, String tipodoc, String documento, String telefono, String correo) {
        this.id = id;
        this.nombre = nombre;
        this.tipodoc = tipodoc;
        this.documento = documento;
        this.telefono = telefono;
        this.correo = correo;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTipodoc() {
        return tipodoc;
    }

    public void setTipodoc(String tipodoc) {
        this.tipodoc = tipodoc;
    }

    public String getDocumento() {
        return documento;
    }

    public void setDocumento(String documento) {
        this.documento = documento;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }
}
