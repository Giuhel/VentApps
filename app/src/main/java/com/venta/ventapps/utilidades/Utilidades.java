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

    //TAbla Ventas
    public static final String TABLA_VENTA="bd_ventas";
    //public static final String ID_VENTA="id";
    public static final String NUMERO_VENTA="numerov";
    public static final String FECHA_VENTA="fecha";
    public static final String MONTO_VENTA="monto";
    public static final String CLIENTE_VENTA="cliente";
    public static final String METPAGO_VENTA="metodopago";
    public static final String crear_tabla_venta="CREATE TABLE "+TABLA_VENTA+" ("+NUMERO_VENTA+" TEXT, "+FECHA_VENTA+" TEXT, "+MONTO_VENTA+ " NUMERIC, "
                                                    +CLIENTE_VENTA+ " TEXT, "+METPAGO_VENTA+" TEXT)";


    //TAbla DEtalle Venta
    public static final String TABLA_DETAVENTA="bd_detaVenta";
    public static final String DETALLEV_ID="id";
    public static final String DETALLEV_NUMEROV="numerov";
    public static final String DETALLEV_IDPROD="idProd";
    public static final String DETALLEV_NOMPROD="producto";
    public static final String DETALLEV_CANTIDAD="cantidad";
    public static final String DETALLEV_PRECIOPROD="preVProd";
    public static final String crear_tabla_detalleventa="CREATE TABLE "+TABLA_DETAVENTA+" ("+DETALLEV_ID+" INTEGER, "+DETALLEV_NUMEROV+" TEXT, "+DETALLEV_IDPROD+ " INTEGER, "
            +DETALLEV_NOMPROD+ " TEXT, "+DETALLEV_CANTIDAD+" INTEGER, "+DETALLEV_PRECIOPROD+" NUMERIC)";
}
