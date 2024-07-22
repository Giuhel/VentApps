package com.venta.ventapps.Entidades;

public class Categorias {

    private String idcategoria;
    private String nomCategoria;

    public Categorias() {
    }

    public Categorias(String idcategoria, String nomCategoria) {
        this.idcategoria = idcategoria;
        this.nomCategoria = nomCategoria;
    }

    public String getIdcategoria() {
        return idcategoria;
    }

    public void setIdcategoria(String idcategoria) {
        this.idcategoria = idcategoria;
    }

    public String getNomCategoria() {
        return nomCategoria;
    }

    public void setNomCategoria(String nomCategoria) {
        this.nomCategoria = nomCategoria;
    }
}
