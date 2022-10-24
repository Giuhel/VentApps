package com.venta.ventapps.Actividades.productos;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.venta.ventapps.Actividades.clientes.DetalleCliente;
import com.venta.ventapps.Actividades.clientes.ListadeCientes;
import com.venta.ventapps.Actividades.clientes.clientes;
import com.venta.ventapps.Entidades.Clientes;
import com.venta.ventapps.Entidades.Productos;
import com.venta.ventapps.Entidades.conexionSQLite;
import com.venta.ventapps.R;
import com.venta.ventapps.fragmentos.Inventario;
import com.venta.ventapps.utilidades.Utilidades;

import android.content.DialogInterface;
import android.content.Intent;
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

    FloatingActionButton editar,eliminar;

    conexionSQLite conn;
    byte [] getimgbytes;

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
        editar=findViewById(R.id.editarproducto);
        eliminar=findViewById(R.id.eliminarproducto);

        conn=new conexionSQLite(getApplicationContext(),"ventApps",null,1);

        Bundle miBundle=this.getIntent().getExtras();
        if(miBundle!=null){
            int ide=miBundle.getInt("ID");
            id.setText(ide+"");

            CargarProductoLista(id.getText().toString());
        }

        editar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                enviarDAtos();
            }
        });

        eliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogoEliminar();
            }
        });
    }

    private void DialogoEliminar(){
        AlertDialog.Builder builder=new AlertDialog.Builder(DetalleProducto.this);
        builder.setTitle("Advertencia");
        builder.setMessage("¿Estas seguro de eliminar el Producto?")
                .setPositiveButton("SI", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        eliminarProducto();
                    }
                })
                .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                }).setCancelable(false)
                .show();
    }

    private void eliminarProducto() {
        SQLiteDatabase db=conn.getWritableDatabase();
        String [] parametros={id.getText().toString()};

        db.delete(Utilidades.TABLA_PRODUCTOS,Utilidades.ID_PRODUCTO+"=?",parametros);
        db.close();
        Toast.makeText(getApplicationContext(),"Se elimino el Producto",Toast.LENGTH_SHORT).show();
        TotalProducto();
        Intent intent = new Intent(DetalleProducto.this, ListaProductos.class);
        startActivity(intent);
        finish();
    }

    private void TotalProducto() {
        SQLiteDatabase db=conn.getReadableDatabase();
        int total;
        try {
            Cursor cursor =db.rawQuery("select count(*) from "+ Utilidades.TABLA_PRODUCTOS,null);
            cursor.moveToFirst();
            total=cursor.getInt(0);
            Inventario.cantProducT.setText(total+"");
            cursor.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void enviarDAtos() {
        Intent miintent=new Intent(DetalleProducto.this, productos.class);
        Bundle mibundle=new Bundle();
        mibundle.putInt("ID",Integer.parseInt(id.getText().toString()));
        mibundle.putString("NOM",nombre.getText().toString());
        mibundle.putInt("STK",Integer.parseInt(stock.getText().toString()));
        mibundle.putString("COD",codigo.getText().toString());
        mibundle.putDouble("PREV",Double.parseDouble(precio.getText().toString()));
        mibundle.putString("CATE",categoria.getText().toString());
        mibundle.putString("DESC",descripcion.getText().toString());
        mibundle.putByteArray("IMG",getimgbytes);
        mibundle.putString("acc","editar");
        miintent.putExtras(mibundle);
        startActivity(miintent);
        finish();
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

                    getimgbytes=cursor.getBlob(3);
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