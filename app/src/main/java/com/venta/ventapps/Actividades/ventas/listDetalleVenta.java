package com.venta.ventapps.Actividades.ventas;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputLayout;
import com.venta.ventapps.Adapters.AdapterDetalleVenta;
import com.venta.ventapps.Entidades.conexionSQLite;
import com.venta.ventapps.Entidades.detalleVenta;
import com.venta.ventapps.R;
import com.venta.ventapps.utilidades.Utilidades;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.telephony.PhoneNumberUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

public class listDetalleVenta extends AppCompatActivity {

    TextView numventaa,fecha,metpago,total,cliente,doccliente;
    ImageButton atras;
    MaterialButton comprobante,eliminar;

    RecyclerView recyclerlista;
    conexionSQLite conn;
    AdapterDetalleVenta adapter;
    ArrayList<detalleVenta> listadetalle;

    int idcliente;
    String telefCliente;
    Uri uri;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_detalle_venta);

        verifyStoragePermission(this);
        StrictMode.VmPolicy.Builder builder= new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());

        numventaa=findViewById(R.id.numventaDetalle);
        atras=findViewById(R.id.atraaaas);
        recyclerlista=findViewById(R.id.listaprodoctosDetalleVenta);
        fecha=findViewById(R.id.FechaDetalleventa);
        metpago=findViewById(R.id.metodopagoDetalleVenta);
        total=findViewById(R.id.totalDetlleVentaa);
        cliente=findViewById(R.id.clienteDetalleVenta);
        comprobante=findViewById(R.id.BtnComprobante);
        eliminar=findViewById(R.id.btnEliminarVenta);
        doccliente=findViewById(R.id.txtdocclienteDetalleventa);

        conn=new conexionSQLite(getApplicationContext(),"ventApps",null,1);
        recyclerlista.setLayoutManager(new LinearLayoutManager(this));

        Bundle miBundle=this.getIntent().getExtras();
        if(miBundle!=null){
            String ide=miBundle.getString("NUMV");
            numventaa.setText(ide);
            CargarDAtosVenta(ide);
            cargarDEtalle(ide);
            cargarDatosCliente(idcliente);
            System.out.println(idcliente);
        }
        eventosClick();
    }

    private void CargarDAtosVenta(String numventa) {
        try {
            SQLiteDatabase db=conn.getReadableDatabase();

            Cursor cursor=db.rawQuery("select * from "+ Utilidades.TABLA_VENTA +" where "+Utilidades.NUMERO_VENTA+" like '%"+numventa+"%'",null);
            if(cursor.getCount()!=0){
                while (cursor.moveToNext()){
                    fecha.setText(cursor.getString(1));
                    total.setText(cursor.getDouble(2)+"");
                    idcliente=cursor.getInt(3);
                    cliente.setText(cursor.getString(4));
                    metpago.setText(cursor.getString(5));
                }
            }else{
                Toast.makeText(getApplicationContext(),"Error AL cargar",Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(getApplicationContext(),"error de conexion",Toast.LENGTH_SHORT).show();
        }
    }

    private void cargarDEtalle(String consulta) {
        SQLiteDatabase db=conn.getReadableDatabase();
        detalleVenta detalle=null;
        listadetalle = new ArrayList<>();

        Cursor cursor =db.rawQuery("select * from "+ Utilidades.TABLA_DETAVENTA +" where " +Utilidades.DETALLEV_NUMEROV+" like '%"+consulta+"%'",null);
        while (cursor.moveToNext()){
            detalle=new detalleVenta();
            detalle.setId(cursor.getInt(0));
            detalle.setNumeroventa(cursor.getString(1));
            detalle.setIdProd(cursor.getInt(2));
            detalle.setNomProd(cursor.getString(3));
            detalle.setCant(cursor.getInt(4));
            detalle.setPrecio(cursor.getDouble(5));
            listadetalle.add(detalle);
        }
        adapter=new AdapterDetalleVenta(listadetalle);
        recyclerlista.setAdapter(adapter);
        db.close();
    }

    private void cargarDatosCliente(int idclientee) {
        try {
            SQLiteDatabase db=conn.getReadableDatabase();

            Cursor cursor=db.rawQuery("select * from "+ Utilidades.TABLA_CLIENTE +" where "+Utilidades.CAMPO_ID+"="+idclientee,null);
            while (cursor.moveToNext()){
                String doc=(cursor.getString(3));
                if(idclientee==0){
                    telefCliente="na";
                }else{
                    doccliente.setText(doc);
                    telefCliente= cursor.getString(4);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(getApplicationContext(),"error de conexion",Toast.LENGTH_SHORT).show();
        }
    }

    private void eventosClick(){
        atras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        comprobante.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Screenshot2();
            }
        });
    }

    public void Screenshot2() {
        String filename=Environment.getExternalStorageDirectory() + "/Comprobantes/"+ numventaa.getText().toString()+".jpg";

        ConstraintLayout root=getWindow().getDecorView().getRootView().findViewById(R.id.imgComprobante);
        root.setDrawingCacheEnabled(true);
        Bitmap bitmap = Bitmap.createBitmap(root.getDrawingCache());
        root.setDrawingCacheEnabled(false);

        File file= new File(filename);
        file.getParentFile().mkdirs();

        try {
            FileOutputStream fileOutputStream=new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.JPEG,100,fileOutputStream);
            fileOutputStream.flush();
            fileOutputStream.close();

            uri= Uri.fromFile(file);
            EnviarComprobanteWPS(uri);


        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void EnviarComprobanteWPS(Uri urii){
        if(telefCliente=="na"){
            dialogo_Ingresar_NUMERO();
        }else{
            EnviarCAptura(urii);
        }
    }

    private void EnviarCAptura(Uri urii){
        try {
            Intent intent = new Intent("android.intent.action.MAIN");
            intent.setAction(Intent.ACTION_SEND);
            intent.setType("text/plain");
            intent.putExtra(Intent.EXTRA_STREAM, urii);
            intent.putExtra(Intent.EXTRA_TEXT,"Se envia una copia de su compra...");
            intent.putExtra("jid", telefCliente + "@s.whatsapp.net"); //numero telefonico sin prefijo "+"!
            intent.setPackage("com.whatsapp");
            startActivity(intent);
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(getApplicationContext(), "Whatsapp no esta instalado.", Toast.LENGTH_LONG).show();
        }
    }

    private void dialogo_Ingresar_NUMERO(){
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        LayoutInflater inflater=getLayoutInflater();
        View v=inflater.inflate(R.layout.dialogo_ingresartelefono,null);
        builder.setView(v);

        final AlertDialog dialogo=builder.create();
        dialogo.show();

        ImageButton cerrar=v.findViewById(R.id.btnCerrarNumero);
        TextInputLayout numero=v.findViewById(R.id.numeroAwsp);
        MaterialButton envia=v.findViewById(R.id.btnEnviaComprobante);

        cerrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogo.dismiss();
            }
        });
        envia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                telefCliente=numero.getEditText().getText().toString();
                EnviarCAptura(uri);
            }
        });
    }

    private static final int REQUEST_EXTERNAL_STORAGE=1;
    private static String[] PERMISSION_EXTORAGE={
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE
    };

    public static void verifyStoragePermission(Activity activity){

        int permission=ActivityCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if(permission!=PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(activity,PERMISSION_EXTORAGE,REQUEST_EXTERNAL_STORAGE);
        }
    }
}