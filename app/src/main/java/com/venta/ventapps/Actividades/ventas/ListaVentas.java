package com.venta.ventapps.Actividades.ventas;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.button.MaterialButton;
import com.venta.ventapps.Actividades.clientes.DetalleCliente;
import com.venta.ventapps.Actividades.clientes.ListadeCientes;
import com.venta.ventapps.Adapters.AdaptadorClientes;
import com.venta.ventapps.Adapters.AdapterListaVentas;
import com.venta.ventapps.Entidades.Clientes;
import com.venta.ventapps.Entidades.Ventaa;
import com.venta.ventapps.Entidades.conexionSQLite;
import com.venta.ventapps.R;
import com.venta.ventapps.utilidades.Utilidades;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;

public class ListaVentas extends AppCompatActivity implements AdapterListaVentas.RecylerItemCLick {

    RecyclerView recyclerlista;
    conexionSQLite conn;
    AdapterListaVentas adapter;
    ArrayList<Ventaa> listaVentas;

    LinearLayout inputF;
    private int dia, mes, anio;

    TextView fec;
    MaterialButton filtrar,quitarfiltro;
    ImageButton atras;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_ventas);

        recyclerlista=findViewById(R.id.reciclerlistaVentas);
        inputF=findViewById(R.id.inputfechaventas);
        fec=findViewById(R.id.txtfechalistaV);
        filtrar=findViewById(R.id.filtraventas);
        quitarfiltro=findViewById(R.id.quitafiltroventas);
        atras=findViewById(R.id.atrasVentaas);

        conn=new conexionSQLite(getApplicationContext(),"ventApps",null,1);
        recyclerlista.setLayoutManager(new LinearLayoutManager(this));

        cargarVentas("");
        ObtenerFecha();
        eventoBotones();
    }

    private void eventoBotones(){
        inputF.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                seleccionarFecha();
            }
        });
        filtrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cargarVentas(fec.getText().toString());
            }
        });
        quitarfiltro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cargarVentas("");
            }
        });
        atras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void cargarVentas(String consulta) {
        SQLiteDatabase db=conn.getReadableDatabase();
        Ventaa ventaa=null;
        listaVentas = new ArrayList<>();

        Cursor cursor =db.rawQuery("select * from "+ Utilidades.TABLA_VENTA +" where " +Utilidades.FECHA_VENTA+" like '%"+consulta+"%'",null);
        while (cursor.moveToNext()){
            ventaa=new Ventaa();
            ventaa.setNumV(cursor.getString(0));
            ventaa.setFecha(cursor.getString(1));
            ventaa.setMonto(cursor.getDouble(2));
            ventaa.setIdcliente(cursor.getInt(3));
            ventaa.setCliente(cursor.getString(4));
            ventaa.setMetodoPago(cursor.getString(5));
            listaVentas.add(ventaa);
        }
        adapter=new AdapterListaVentas(listaVentas,this);
        recyclerlista.setAdapter(adapter);
        db.close();
    }

    private void seleccionarFecha() {
        final Calendar c = Calendar.getInstance();
        dia = c.get(Calendar.DAY_OF_MONTH);
        mes = c.get(Calendar.MONTH);
        anio = c.get(Calendar.YEAR);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int año, int mes, int dia) {
                String diaformato, mesformato;
                if (dia < 10) {
                    diaformato = "0" + String.valueOf(dia);
                } else {
                    diaformato = String.valueOf(dia);
                }
                int MES = mes + 1;
                if (MES < 10) {
                    mesformato = "0" + String.valueOf(MES);
                } else {
                    mesformato = String.valueOf(MES);
                }
                fec.setText(diaformato + "/" + mesformato + "/" + año);
            }
        }, anio, mes, dia);
        datePickerDialog.show();
    }

    public void ObtenerFecha() {
        int dia, mes, año;
        String diaformato, mesformato, fecha;
        Calendar fecc = Calendar.getInstance();
        dia = fecc.get(Calendar.DAY_OF_MONTH);
        mes = fecc.get(Calendar.MONTH);
        mes = mes + 1;
        año = fecc.get(Calendar.YEAR);

        if (dia < 10) {
            diaformato = "0" + String.valueOf(dia);
        } else {
            diaformato = String.valueOf(dia);
        }
        if (mes < 10) {
            mesformato = "0" + String.valueOf(mes);
        } else {
            mesformato = String.valueOf(mes);
        }

        fecha = diaformato + "/" + mesformato + "/" + año;
        fec.setText(fecha);
    }

    @Override
    public void itemClick(Ventaa ventas) {
        System.out.println(ventas.getNumV()+" aquiiiii");
        Intent miintent=new Intent(ListaVentas.this, listDetalleVenta.class);
        Bundle mibundle=new Bundle();
        mibundle.putString("NUMV",ventas.getNumV());
        miintent.putExtras(mibundle);
        startActivity(miintent);
        //finish();
    }
}