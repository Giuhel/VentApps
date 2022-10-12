package com.venta.ventapps.Actividades;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputLayout;
import com.venta.ventapps.Adapters.AdaptadorClientes;
import com.venta.ventapps.Entidades.Clientes;
import com.venta.ventapps.Entidades.conexionSQLite;
import com.venta.ventapps.R;
import com.venta.ventapps.utilidades.Utilidades;

import java.util.ArrayList;

public class ListadeCientes extends AppCompatActivity {

    TextInputLayout documentobuscarr;
    MaterialButton btnbuscar;
    RecyclerView recyclerlista;

    ArrayList<Clientes> listaclientes;
    conexionSQLite conn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listade_cientes);

        btnbuscar=findViewById(R.id.buscarcliente);
        recyclerlista=findViewById(R.id.listaclientes);
        documentobuscarr=findViewById(R.id.documentobuscar);

        conn=new conexionSQLite(getApplicationContext(),"ventApps",null,1);


        recyclerlista.setLayoutManager(new LinearLayoutManager(this));
        recyclerlista.setHasFixedSize(true);

        consultarListaCLientes("");

        btnbuscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                consultarListaCLientes(documentobuscarr.getEditText().getText().toString());
            }
        });
    }

    private void consultarListaCLientes(String consulta) {
        SQLiteDatabase db=conn.getReadableDatabase();
        Clientes clientes=null;
        listaclientes = new ArrayList<>();

        Cursor cursor =db.rawQuery("select * from "+ Utilidades.TABLA_CLIENTE +" where ndocumento like '%"+consulta+"%'",null);
        while (cursor.moveToNext()){
            clientes=new Clientes();
            clientes.setId(cursor.getInt(0));
            clientes.setNombre(cursor.getString(1));
            clientes.setTipodoc(cursor.getString(2));
            clientes.setDocumento(cursor.getString(3));
            clientes.setTelefono(cursor.getString(4));
            clientes.setCorreo(cursor.getString(5));
            listaclientes.add(clientes);
        }
        AdaptadorClientes adapter=new AdaptadorClientes(listaclientes);
        recyclerlista.setAdapter(adapter);
        db.close();
    }

    public void onClick(View view) {
        switch (view.getId()){
            case R.id.atraslistaclientes:
                Intent intent = new Intent(this, clientes.class);
                startActivity(intent);
                finish();
                break;
        }
    }
}