package com.venta.ventapps.Actividades.productos;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputLayout;
import com.venta.ventapps.Entidades.Categorias;
import com.venta.ventapps.Entidades.Productos;
import com.venta.ventapps.Entidades.conexionSQLite;
import com.venta.ventapps.R;
import com.venta.ventapps.fragmentos.Inventario;
import com.venta.ventapps.utilidades.Utilidades;

import java.util.ArrayList;

public class productos extends AppCompatActivity {
    ImageView img;
    TextView idprod;
    TextInputLayout nombre,cantidad,codigo,precioV,descripcion;
    Spinner categoria;
    MaterialButton Agregar;

    ArrayList<Categorias> ListaCategoria;
    ArrayList<String> CategoriasLista;

    conexionSQLite conn;
    int siguienteID;
    String accion="guardar";
    boolean seleccionoImagen=false;

    private static final int PICK_IMAGE_REQUEST=100;
    private Uri imageFilePath;
    private Bitmap imageTostore;
    Bitmap objectbitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_productos);

        img=findViewById(R.id.imgproducto);
        idprod=findViewById(R.id.txtIdprod);
        nombre=findViewById(R.id.txtnombreprod);
        cantidad=findViewById(R.id.txtcantidadstock);
        codigo=findViewById(R.id.txtcodigop);
        precioV=findViewById(R.id.txtprecioventa);
        categoria=findViewById(R.id.spinerCategoriaP);
        descripcion=findViewById(R.id.txtdescripcionP);
        Agregar=findViewById(R.id.btnAgregaPRoducto);

        conn=new conexionSQLite(getApplicationContext(),"ventApps",null,1);

        if (ContextCompat.checkSelfPermission(productos.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(productos.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(productos.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA}, 1000);
        }

        SiguienteID();
        CargarCAterogirasEnSpinner();
        ArrayAdapter<CharSequence> adapter=new ArrayAdapter(this, android.R.layout.simple_list_item_activated_1,CategoriasLista);
        categoria.setAdapter(adapter);

        Bundle miBundle=this.getIntent().getExtras();
        if(miBundle!=null){
            siguienteID=miBundle.getInt("ID");
            String nom=miBundle.getString("NOM");
            int stok=miBundle.getInt("STK");
            String codi=miBundle.getString("COD");
            double pre=miBundle.getDouble("PREV");
            String cat=miBundle.getString("CATE");
            String des=miBundle.getString("DESC");
            byte [] imgg=miBundle.getByteArray("IMG");
            objectbitmap= BitmapFactory.decodeByteArray(imgg,0,imgg.length);
            String acci=miBundle.getString("acc");

            idprod.setText("ID de producto: "+siguienteID);
            nombre.getEditText().setText(nom);
            cantidad.getEditText().setText(stok+"");
            img.setImageBitmap(objectbitmap);
            codigo.getEditText().setText(codi+"");
            precioV.getEditText().setText(pre+"");
            descripcion.getEditText().setText(des);
            accion=acci;
            Agregar.setText("EDITAR PRODUCTO");
            Agregar.setCornerRadius(20);
        }else {
            SiguienteID();
            accion="guardar";
        }

        Agregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(accion=="guardar"){
                    GuardarPRoducto();
                }else {
                    EditarProductos();
                }
            }
        });

    }

    //metodos para la carga de imagen
    @Override
    protected void onActivityResult(int requestCode, int resultCode,Intent data) {
        try {
            super.onActivityResult(requestCode, resultCode, data);
            if(requestCode==PICK_IMAGE_REQUEST && resultCode==RESULT_OK && data!=null && data.getData()!=null){
                imageFilePath=data.getData();
                imageTostore=MediaStore.Images.Media.getBitmap(getContentResolver(),imageFilePath);
                img.setImageBitmap(imageTostore);
                objectbitmap=imageTostore;
                seleccionoImagen=true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void GuardarPRoducto(){
        try {
            if(cantidad.getEditText().getText().toString().isEmpty() || nombre.getEditText().getText().toString().isEmpty()
                    || codigo.getEditText().getText().toString().isEmpty() || precioV.getEditText().getText().toString().isEmpty()){
                Toast.makeText(this,"LLene los campos",Toast.LENGTH_SHORT).show();
            }else{
                if(categoria.getSelectedItem().equals("Seleccione")){
                    Toast.makeText(this,"Seleccione una categoria",Toast.LENGTH_SHORT).show();
                }else{
                    if(seleccionoImagen==false){
                        Bitmap bitmap=BitmapFactory.decodeResource(getResources(),R.drawable.sinimg);
                        objectbitmap=bitmap;
                    }
                    conn.GuardarPRoductos(new Productos(siguienteID,nombre.getEditText().getText().toString(),objectbitmap,Integer.parseInt(cantidad.getEditText().getText().toString()),
                            codigo.getEditText().getText().toString(),Double.parseDouble(precioV.getEditText().getText().toString()),categoria.getSelectedItem().toString(),
                            descripcion.getEditText().getText().toString()));
                    limpiarcampos();
                    TotalProducto();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
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


    private void EditarProductos() {
        try {
            if(cantidad.getEditText().getText().toString().isEmpty() || nombre.getEditText().getText().toString().isEmpty()
                    || codigo.getEditText().getText().toString().isEmpty() || precioV.getEditText().getText().toString().isEmpty()){
                Toast.makeText(this,"LLene los campos",Toast.LENGTH_SHORT).show();
            }else{
                if(categoria.getSelectedItem().equals("Seleccione")){
                    Toast.makeText(this,"Seleccione una categoria",Toast.LENGTH_SHORT).show();
                }else{
                    conn.EditarPRoductos(new Productos(siguienteID,nombre.getEditText().getText().toString(),objectbitmap,Integer.parseInt(cantidad.getEditText().getText().toString()),
                            codigo.getEditText().getText().toString(),Double.parseDouble(precioV.getEditText().getText().toString()),categoria.getSelectedItem().toString(),
                            descripcion.getEditText().getText().toString()),siguienteID);
                    //limpiarcampos();
                    finish();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void choseImage(View objectView){
        try {
            Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(intent,PICK_IMAGE_REQUEST);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //medotos

    private void limpiarcampos() {
        SiguienteID();
       nombre.getEditText().setText("");
       img.setImageResource(R.drawable.camara);
       cantidad.getEditText().setText("");
       codigo.getEditText().setText("");
       precioV.getEditText().setText("");
       categoria.setSelection(0);
       descripcion.getEditText().setText("");
    }

    private void SiguienteID(){
        SQLiteDatabase db=conn.getReadableDatabase();
        try {
            Cursor cursor =db.rawQuery("select max("+Utilidades.ID_PRODUCTO+") from "+Utilidades.TABLA_PRODUCTOS,null);
            cursor.moveToFirst();
            siguienteID=cursor.getInt(0);
            if(siguienteID==0){
                siguienteID=1;
                idprod.setText("ID de producto: "+siguienteID);
            }else{
                siguienteID=siguienteID+1;
                idprod.setText("ID de producto: "+siguienteID+"");
            }
            cursor.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void CargarCAterogirasEnSpinner() {
        SQLiteDatabase db=conn.getReadableDatabase();

        Categorias categorias=null;
        ListaCategoria = new ArrayList<Categorias>();

        Cursor cursor=db.rawQuery("Select * from "+ Utilidades.TABLA_CATEGORIA,null);
        while (cursor.moveToNext()){
            categorias=new Categorias();
            categorias.setIdcategoria(cursor.getInt(0));
            categorias.setNomCategoria(cursor.getString(1));

            ListaCategoria.add(categorias);
        }
        obtenerLista();
    }

    private void obtenerLista() {
        CategoriasLista=new ArrayList<String>();
        CategoriasLista.add("Seleccione");

        for (int i=0;i<ListaCategoria.size();i++){
            CategoriasLista.add(ListaCategoria.get(i).getIdcategoria()+" - "+ListaCategoria.get(i).getNomCategoria());
        }
    }

    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btnAtrasP:
                finish();break;
            case R.id.imgproducto:
                choseImage(view);
                break;
        }
    }

}