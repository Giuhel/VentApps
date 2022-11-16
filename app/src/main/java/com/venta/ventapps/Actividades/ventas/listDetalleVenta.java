package com.venta.ventapps.Actividades.ventas;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.venta.ventapps.Adapters.AdapterDetalleVenta;
import com.venta.ventapps.Adapters.AdapterListaVentas;
import com.venta.ventapps.Entidades.Ventaa;
import com.venta.ventapps.Entidades.conexionSQLite;
import com.venta.ventapps.Entidades.detalleVenta;
import com.venta.ventapps.R;
import com.venta.ventapps.utilidades.Utilidades;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;

public class listDetalleVenta extends AppCompatActivity {

    TextView numventaa;
    ImageButton atras;

    RecyclerView recyclerlista;
    conexionSQLite conn;
    AdapterDetalleVenta adapter;
    ArrayList<detalleVenta> listadetalle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_detalle_venta);

        numventaa=findViewById(R.id.numventaDetalle);
        atras=findViewById(R.id.atraaaas);
        recyclerlista=findViewById(R.id.listaprodoctosDetalleVenta);

        conn=new conexionSQLite(getApplicationContext(),"ventApps",null,1);
        recyclerlista.setLayoutManager(new LinearLayoutManager(this));

        Bundle miBundle=this.getIntent().getExtras();
        if(miBundle!=null){
            String ide=miBundle.getString("NUMV");
            numventaa.setText(ide);
            cargarDEtalle(numventaa.getText().toString());
        }

        eventosClick();
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

    private void eventosClick(){
        atras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}