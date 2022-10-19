package com.venta.ventapps.Entidades;

public class Categorias {

    private int idcategoria;
    private String nomCategoria;

    public Categorias() {
    }

    public Categorias(int idcategoria, String nomCategoria) {
        this.idcategoria = idcategoria;
        this.nomCategoria = nomCategoria;
    }

    public int getIdcategoria() {
        return idcategoria;
    }

    public void setIdcategoria(int idcategoria) {
        this.idcategoria = idcategoria;
    }

    public String getNomCategoria() {
        return nomCategoria;
    }

    public void setNomCategoria(String nomCategoria) {
        this.nomCategoria = nomCategoria;
    }
}
