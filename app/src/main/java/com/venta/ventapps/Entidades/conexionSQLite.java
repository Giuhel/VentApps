package com.venta.ventapps.Entidades;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.venta.ventapps.utilidades.Utilidades;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

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
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int versionAntigua, int versionNueva) {
        db.execSQL("DROP TABLE IF EXISTS bd_clientes");
        db.execSQL("DROP TABLE IF EXISTS bd_categorias");
        db.execSQL("DROP TABLE IF EXISTS bd_productos");
        onCreate(db);
    }

    public void StorageImage (Productos productos){
        try {
            SQLiteDatabase bd=this.getWritableDatabase();
            Bitmap image=productos.getImg();
            byteArrayOutputStream=new ByteArrayOutputStream();
            image.compress(Bitmap.CompressFormat.JPEG,100,byteArrayOutputStream);
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
}
