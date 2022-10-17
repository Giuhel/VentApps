package com.venta.ventapps.Actividades.clientes;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.venta.ventapps.Entidades.conexionSQLite;
import com.venta.ventapps.MainActivity;
import com.venta.ventapps.R;
import com.venta.ventapps.Splash;
import com.venta.ventapps.utilidades.Utilidades;

public class DetalleCliente extends AppCompatActivity {

    TextView id,nombre,documento,telefono,correo;
    ImageView atras;
    FloatingActionButton eliminar;

    conexionSQLite conn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_cliente);

        id=findViewById(R.id.listidcliente);
        nombre=findViewById(R.id.listnombrecliente);
        documento=findViewById(R.id.listdocumentocliente);
        telefono=findViewById(R.id.listtelefonocliente);
        correo=findViewById(R.id.listcorreocliente);
        atras=findViewById(R.id.atrasdetalleliente);
        eliminar=findViewById(R.id.eliminarcliente);

        conn=new conexionSQLite(getApplicationContext(),"ventApps",null,1);

        Bundle miBundle=this.getIntent().getExtras();
        if(miBundle!=null){
            int ide=miBundle.getInt("ID");
            String nom=miBundle.getString("NOM");
            String docu=miBundle.getString("DOC");
            String tel=miBundle.getString("CEL");
            String corre=miBundle.getString("CORREO");

            id.setText(ide+"");
            nombre.setText(nom);
            documento.setText(docu);
            telefono.setText(tel);
            correo.setText(corre);
        }

        eliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogoEliminar();
            }
        });
    }

    public void onClick(View view) {
        switch (view.getId()){
            case R.id.atrasdetalleliente:
                Intent miintent=new Intent(DetalleCliente.this,ListadeCientes.class);
                startActivity(miintent);
                finish();
                break;
        }
    }

    private void editarcliente() {
        SQLiteDatabase db=conn.getWritableDatabase();
        String [] parametros={id.getText().toString()};

        ContentValues values=new ContentValues();
        values.put(Utilidades.CAMPO_ID,id.getText().toString());
    }

    private void eliminarCliente() {
        SQLiteDatabase db=conn.getWritableDatabase();
        String [] parametros={id.getText().toString()};

        db.delete(Utilidades.TABLA_CLIENTE,Utilidades.CAMPO_ID+"=?",parametros);
        db.close();
        Toast.makeText(getApplicationContext(),"SE elimino el cliente",Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(DetalleCliente.this, ListadeCientes.class);
        startActivity(intent);
        finish();
    }

    private void DialogoEliminar(){
        AlertDialog.Builder builder=new AlertDialog.Builder(DetalleCliente.this);
        builder.setTitle("Advertencia");
        builder.setMessage("¿Estas seguro de eliminar al cliente?")
                .setPositiveButton("SI", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        eliminarCliente();
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
}