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

    TextView id,nombre,tipodoc,documento,telefono,correo;
    ImageView atras;
    FloatingActionButton eliminar,editar;

    conexionSQLite conn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_cliente);

        id=findViewById(R.id.listidcliente);
        nombre=findViewById(R.id.listnombrecliente);
        tipodoc=findViewById(R.id.listTipoDocliente);
        documento=findViewById(R.id.listdocumentoclientee);
        telefono=findViewById(R.id.listtelefonocliente);
        correo=findViewById(R.id.listcorreocliente);
        atras=findViewById(R.id.atrasdetalleliente);
        eliminar=findViewById(R.id.eliminarcliente);
        editar=findViewById(R.id.editarcliente);

        conn=new conexionSQLite(getApplicationContext(),"ventApps",null,1);

        Bundle miBundle=this.getIntent().getExtras();
        if(miBundle!=null){
            int ide=miBundle.getInt("ID");
            String nom=miBundle.getString("NOM");
            String td=miBundle.getString("TIPD");
            String docu=miBundle.getString("DOC");
            String tel=miBundle.getString("CEL");
            String corre=miBundle.getString("CORREO");

            id.setText(ide+"");
            nombre.setText(nom);
            tipodoc.setText(td);
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

        editar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                IrAeditarCliente();
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

    private void IrAeditarCliente() {
        Intent miintent=new Intent(DetalleCliente.this,clientes.class);
        Bundle mibundle=new Bundle();
        mibundle.putInt("ID",Integer.parseInt(id.getText().toString()));
        mibundle.putString("NOM",nombre.getText().toString());
        mibundle.putString("TIPD",tipodoc.getText().toString());
        mibundle.putString("DOC",documento.getText().toString());
        mibundle.putString("CEL",telefono.getText().toString());
        mibundle.putString("CORREO",correo.getText().toString());
        mibundle.putString("acc","editar");
        miintent.putExtras(mibundle);
        startActivity(miintent);
        finish();
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