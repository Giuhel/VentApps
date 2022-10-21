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

    //tabla categorias
    public static final String TABLA_CATEGORIA="bd_categorias";
    public static final String ID_CATEGORIA="id";
    public static final String NOMBRE_CATEGORIA="nombre";
    public static final String crear_tabla_categoria="CREATE TABLE "+TABLA_CATEGORIA+" ("+ID_CATEGORIA+" INTEGER, "+NOMBRE_CATEGORIA+" TEXT)";

    //tabla Productos
    public static final String TABLA_PRODUCTOS="bd_productos";
    public static final String ID_PRODUCTO="id";
    public static final String NOMBRE_PRODUCTO="nombre";
    public static final String CANTIDAD_PRODUCTO="stock";
    public static final String IMG_PRODUCTO="img";
    public static final String CODIGO_PRODUCTO="codigo";
    public static final String PRECIOV_PRODUCTO="precioV";
    public static final String CATEGORIA_PRODUCTO="categoria";
    public static final String DESCRIPCION_PRODUCTO="descripcion";
    public static final String crear_tabla_prodcutos="CREATE TABLE "+TABLA_PRODUCTOS+" ("+ID_PRODUCTO+" INTEGER, "+NOMBRE_PRODUCTO+" TEXT, "+CANTIDAD_PRODUCTO+" INTEGER, "+
            IMG_PRODUCTO+" BLOB, "+CODIGO_PRODUCTO+" TEXT, "+PRECIOV_PRODUCTO+" NUMERIC, "+CATEGORIA_PRODUCTO+" TEXT,"+DESCRIPCION_PRODUCTO+" TEXT)";
}
