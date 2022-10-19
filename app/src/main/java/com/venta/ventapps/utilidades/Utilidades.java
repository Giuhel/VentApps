package com.venta.ventapps.utilidades;

public class Utilidades {

    public static final String TABLA_CLIENTE="bd_clientes";
    public static final String CAMPO_ID="id";
    public static final String CAMPO_NOMBRE="nombre";
    public static final String CAMPO_TIPODOC="tipodocumento";
    public static final String CAMPO_NDOC="ndocumento";
    public static final String CAMPO_TELEFONO="telefono";
    public static final String CAMPO_CORREO="correo";

    public static final String crear_tabla_usuario="CREATE TABLE "+TABLA_CLIENTE+" ("+CAMPO_ID+" INTEGER, "+CAMPO_NOMBRE+" TEXT, "+CAMPO_TIPODOC+" TEXT, "+CAMPO_NDOC+" TEXT, "+CAMPO_TELEFONO+" TEXT, "+CAMPO_CORREO+" TEXT)";

    public static final String TABLA_CATEGORIA="bd_categorias";
    public static final String ID_CATEGORIA="id";
    public static final String NOMBRE_CATEGORIA="nombre";

    public static final String crear_tabla_categoria="CREATE TABLE "+TABLA_CATEGORIA+" ("+ID_CATEGORIA+" INTEGER, "+NOMBRE_CATEGORIA+" TEXT)";
}
