package com.venta.ventapps.Entidades;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.venta.ventapps.utilidades.Utilidades;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

public class conexionSQLite extends SQLiteOpenHelper {

    private ByteArrayOutputStream byteArrayOutputStream;
    private byte[] imagesInByte;
    Context context;

    public conexionSQLite( Context context,String name,SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        this.context=context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(Utilidades.crear_tabla_usuario);
        db.execSQL(Utilidades.crear_tabla_categoria);
        db.execSQL(Utilidades.crear_tabla_prodcutos);
        db.execSQL(Utilidades.crear_tabla_venta);
        db.execSQL(Utilidades.crear_tabla_detalleventa);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int versionAntigua, int versionNueva) {
        db.execSQL("DROP TABLE IF EXISTS bd_clientes");
        db.execSQL("DROP TABLE IF EXISTS bd_categorias");
        db.execSQL("DROP TABLE IF EXISTS bd_productos");
        db.execSQL("DROP TABLE IF EXISTS bd_ventas");
        db.execSQL("DROP TABLE IF EXISTS bd_detaVenta");
        onCreate(db);
    }

    //guardar imagen
    public void GuardarPRoductos (Productos productos){
        try {
            SQLiteDatabase bd=this.getWritableDatabase();
            Bitmap image=productos.getImg();
            byteArrayOutputStream=new ByteArrayOutputStream();
            image.compress(Bitmap.CompressFormat.PNG,50,byteArrayOutputStream);
            imagesInByte=byteArrayOutputStream.toByteArray();

            ContentValues contentValues=new ContentValues();
            contentValues.put(Utilidades.ID_PRODUCTO,productos.getId());
            contentValues.put(Utilidades.NOMBRE_PRODUCTO,productos.getNombre());
            contentValues.put(Utilidades.CANTIDAD_PRODUCTO,productos.getCantidad());
            contentValues.put(Utilidades.IMG_PRODUCTO,imagesInByte);
            contentValues.put(Utilidades.CODIGO_PRODUCTO,productos.getCodigo());
            contentValues.put(Utilidades.PRECIOV_PRODUCTO,productos.getPreciov());
            contentValues.put(Utilidades.CATEGORIA_PRODUCTO,productos.getCategoria());
            contentValues.put(Utilidades.DESCRIPCION_PRODUCTO,productos.getDescripcion());

            long idresultante=bd.insert(Utilidades.TABLA_PRODUCTOS,null,contentValues);
            if(idresultante!=-1){
                Toast.makeText(context,"Se registro el Producto",Toast.LENGTH_SHORT).show();
                bd.close();
            }else{
                Toast.makeText(context,"No se registro el Producto",Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void EditarPRoductos (Productos productos,int id){
        try {
            SQLiteDatabase bd=this.getWritableDatabase();
            Bitmap image=productos.getImg();
            String [] parametros={id+""};

            byteArrayOutputStream=new ByteArrayOutputStream();
            image.compress(Bitmap.CompressFormat.PNG,50,byteArrayOutputStream);
            imagesInByte=byteArrayOutputStream.toByteArray();
            ContentValues contentValues=new ContentValues();
            contentValues.put(Utilidades.ID_PRODUCTO,productos.getId());
            contentValues.put(Utilidades.NOMBRE_PRODUCTO,productos.getNombre());
            contentValues.put(Utilidades.CANTIDAD_PRODUCTO,productos.getCantidad());
            contentValues.put(Utilidades.IMG_PRODUCTO,imagesInByte);
            contentValues.put(Utilidades.CODIGO_PRODUCTO,productos.getCodigo());
            contentValues.put(Utilidades.PRECIOV_PRODUCTO,productos.getPreciov());
            contentValues.put(Utilidades.CATEGORIA_PRODUCTO,productos.getCategoria());
            contentValues.put(Utilidades.DESCRIPCION_PRODUCTO,productos.getDescripcion());

            bd.update(Utilidades.TABLA_PRODUCTOS,contentValues,Utilidades.ID_PRODUCTO+"=?",parametros);
            Toast.makeText(context,"Se Edito el Producto",Toast.LENGTH_SHORT).show();
            bd.close();
        } catch (Exception e) {
            Toast.makeText(context,"No se Edito el Producto",Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }

    //cargar imagen
    public ArrayList<Productos> CargarProductoLista(String consulta){
        try {
            SQLiteDatabase db=this.getReadableDatabase();
            ArrayList<Productos> listproducto=new ArrayList<>();

            Cursor cursor=db.rawQuery("Select * from "+Utilidades.TABLA_PRODUCTOS+" where nombre like '%"+consulta+"%'",null);
            while (cursor.moveToNext()){
                Integer id=cursor.getInt(0);
                String nombre=cursor.getString(1);
                Integer cant=cursor.getInt(2);
                byte [] imgbytes=cursor.getBlob(3);
                Bitmap objectbitmap= BitmapFactory.decodeByteArray(imgbytes,0,imgbytes.length);
                String codigo=cursor.getString(4);
                double precio=cursor.getDouble(5);
                String cate=cursor.getString(6);
                String descrip=cursor.getString(7);
                listproducto.add(new Productos(id,nombre,objectbitmap,cant,codigo,precio,cate,descrip));
            }
            return listproducto;
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(context,"error de conexion",Toast.LENGTH_SHORT).show();
            return null;
        }
    }
}
