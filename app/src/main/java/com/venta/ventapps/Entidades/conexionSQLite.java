package com.venta.ventapps.Entidades;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.venta.ventapps.utilidades.Utilidades;

public class conexionSQLite extends SQLiteOpenHelper {

    public conexionSQLite( Context context,String name,SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(Utilidades.crear_tabla_usuario);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int versionAntigua, int versionNueva) {
        db.execSQL("DROP TABLE IF EXISTS bd_clientes");
        onCreate(db);
    }
}
