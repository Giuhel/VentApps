package com.venta.ventapps.Actividades.productos;

import androidx.appcompat.app.AppCompatActivity;

import com.venta.ventapps.Entidades.Clientes;
import com.venta.ventapps.Entidades.Productos;
import com.venta.ventapps.Entidades.conexionSQLite;
import com.venta.ventapps.R;
import com.venta.ventapps.utilidades.Utilidades;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class DetalleProducto extends AppCompatActivity {

    TextView id,nombre,stock,codigo,precio,categoria,descripcion;
    ImageView img;

    conexionSQLite conn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_producto);

        id=findViewById(R.id.listidproducto);
        nombre=findViewById(R.id.listnombreproducto);
        stock=findViewById(R.id.listStockActualprod);
        codigo=findViewById(R.id.listcodigoproducto);
        precio=findViewById(R.id.listprecioproducto);
        categoria=findViewById(R.id.listcategoriaprodcuto);
        descripcion=findViewById(R.id.listdescripprodcuto);
        img=findViewById(R.id.listimgprodcutoo);

        conn=new conexionSQLite(getApplicationContext(),"ventApps",null,1);

        Bundle miBundle=this.getIntent().getExtras();
        if(miBundle!=null){
            int ide=miBundle.getInt("ID");
            id.setText(ide+"");

            CargarProductoLista(id.getText().toString());
        }
    }

    public void CargarProductoLista(String consulta){
        try {
            SQLiteDatabase db=conn.getReadableDatabase();

            Cursor cursor=db.rawQuery("select * from "+ Utilidades.TABLA_PRODUCTOS +" where id like '%"+consulta+"%'",null);
            if(cursor.getCount()!=0){
                while (cursor.moveToNext()){
                    nombre.setText(cursor.getString(1));
                    stock.setText(cursor.getInt(2)+"");
                    byte [] imgbytes=cursor.getBlob(3);
                    Bitmap objectbitmap= BitmapFactory.decodeByteArray(imgbytes,0,imgbytes.length);
                    img.setImageBitmap(objectbitmap);
                    codigo.setText(cursor.getString(4));
                    precio.setText(cursor.getDouble(5)+"");
                    categoria.setText(cursor.getString(6));
                    descripcion.setText(cursor.getString(7));
                }
            }else{
                Toast.makeText(getApplicationContext(),"No Hay Producto",Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(getApplicationContext(),"error de conexion",Toast.LENGTH_SHORT).show();
        }
    }

    public void onClickear(View view) {
        switch (view.getId()){
            case R.id.atrasdetalleproducto:
                finish();
                break;
        }
    }
}