package com.venta.ventapps.Actividades.clientes;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputLayout;
import com.venta.ventapps.Entidades.conexionSQLite;
import com.venta.ventapps.R;
import com.venta.ventapps.utilidades.Utilidades;

public class clientes extends AppCompatActivity {

    TextView idcliente,totalClient;
    TextInputLayout nombre,ndocument,telefo,correo;
    Spinner tipodocu;
    MaterialButton crearCliente;

    conexionSQLite conn;
    int siguienteId;
    String accion="guardar";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clientes);

        idcliente=findViewById(R.id.txtidcliente);
        nombre=findViewById(R.id.txtnombrecliente);
        tipodocu=findViewById(R.id.spinerTipoDocClie);
        ndocument=findViewById(R.id.txtnumdocumentoClie);
        telefo=findViewById(R.id.txttelefonoclien);
        correo=findViewById(R.id.txtcorreocliente);
        crearCliente=findViewById(R.id.btnCreaCliente);
        totalClient=findViewById(R.id.totalCliente);

        conn=new conexionSQLite(getApplicationContext(),"ventApps",null,1);

        llenarSpiner();
        TotalClientes();
        IdCliente();

        crearCliente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                REgistraCLientes();
            }
        });
    }

    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btnAtrasC:
                finish();
                break;
            case R.id.irlistaClientes:
                Intent intent = new Intent(this, ListadeCientes.class);
                startActivity(intent);
                finish();
                break;
        }
    }

    private void llenarSpiner() {
        String[] Metodos = {"DNI", "PASA PORTE", "CARNET EXTRANJERIA", "RUC", "otros"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_activated_1, Metodos);
        tipodocu.setAdapter(adapter);
    }

    private void TotalClientes(){
        SQLiteDatabase db=conn.getReadableDatabase();
        int total;
        try {
            Cursor cursor =db.rawQuery("select count(*) from "+Utilidades.TABLA_CLIENTE,null);
            cursor.moveToFirst();
            total=cursor.getInt(0);
            totalClient.setText(total+"");
            cursor.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void IdCliente(){
        SQLiteDatabase db=conn.getReadableDatabase();
        try {
            Cursor cursor =db.rawQuery("select max("+Utilidades.CAMPO_ID+") from "+Utilidades.TABLA_CLIENTE,null);
            cursor.moveToFirst();
            siguienteId=cursor.getInt(0);
            if(siguienteId==0){
                siguienteId=1;
                idcliente.setText("ID de CLiente es: "+siguienteId);
            }else{
                siguienteId=siguienteId+1;
                idcliente.setText("ID de Cliente: "+siguienteId+"");
            }
            cursor.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void REgistraCLientes() {
        SQLiteDatabase db=conn.getWritableDatabase();
        ContentValues values=new ContentValues();

        values.put(Utilidades.CAMPO_ID,siguienteId);
        values.put(Utilidades.CAMPO_NOMBRE,nombre.getEditText().getText().toString());
        values.put(Utilidades.CAMPO_TIPODOC,tipodocu.getSelectedItem().toString());
        values.put(Utilidades.CAMPO_NDOC,ndocument.getEditText().getText().toString());
        values.put(Utilidades.CAMPO_TELEFONO,telefo.getEditText().getText().toString());
        values.put(Utilidades.CAMPO_CORREO,correo.getEditText().getText().toString());

        Long idresultante=db.insert(Utilidades.TABLA_CLIENTE,Utilidades.CAMPO_ID,values);

        Toast.makeText(getApplicationContext(),"ID Registro: "+idresultante,Toast.LENGTH_SHORT).show();
        limpiacampos();
    }

    private void limpiacampos(){
        TotalClientes();
        IdCliente();
        nombre.getEditText().setText("");
        ndocument.getEditText().setText("");
        telefo.getEditText().setText("");
        correo.getEditText().setText("");
    }
}