package com.venta.ventapps.Actividades;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputLayout;
import com.venta.ventapps.Adapters.AdaptadorProductos;
import com.venta.ventapps.Adapters.AdapterEligeProducto;
import com.venta.ventapps.Entidades.Productos;
import com.venta.ventapps.Entidades.conexionSQLite;
import com.venta.ventapps.R;
import com.venta.ventapps.utilidades.Utilidades;

import android.app.DatePickerDialog;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class ventas extends AppCompatActivity implements AdapterEligeProducto.RecylerItemCLick{

    LinearLayout inputF;
    TextView fec,numeroventa;
    private int dia, mes, anio;
    CardView seleccionarProd;

    Spinner spnMetpago;
    conexionSQLite conn;

    int siguienteNumVenta;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ventas);

        inputF = findViewById(R.id.inputfecha);
        fec = findViewById(R.id.txtfecha);
        spnMetpago = findViewById(R.id.MetodoPago);
        seleccionarProd = findViewById(R.id.seleccprod);
        numeroventa = findViewById(R.id.numventa);

        conn=new conexionSQLite(getApplicationContext(),"ventApps",null,1);

        ObtenerFecha();
        NumeroVenta();
        llenarSpiner();

    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.inputfecha:
                seleccionarFecha();
                break;
            case R.id.seleccprod:
                abrirDialogoElegirProdcuto();
                break;
            case R.id.btnAtrasV:
                finish();
                break;
        }
    }

    private void NumeroVenta(){
        SQLiteDatabase db=conn.getReadableDatabase();
        try {
            Cursor cursor =db.rawQuery("select max("+ Utilidades.NUMERO_VENTA+") from "+Utilidades.TABLA_VENTA,null);
            cursor.moveToFirst();
            siguienteNumVenta=cursor.getInt(0);
            if(siguienteNumVenta==0){
                siguienteNumVenta=1;
                numeroventa.setText("00"+siguienteNumVenta);
            }else{
                if(siguienteNumVenta<=9){
                    numeroventa.setText("00"+siguienteNumVenta);
                }else if(siguienteNumVenta>9 && siguienteNumVenta<=99){
                    numeroventa.setText("0"+siguienteNumVenta);
                }else{
                    numeroventa.setText(siguienteNumVenta+"");
                }
            }
            cursor.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void abrirDialogoElegirProdcuto() {
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        LayoutInflater inflater=getLayoutInflater();
        View v=inflater.inflate(R.layout.dialogo_seleccionproducto,null);
        builder.setView(v);

        final AlertDialog dialog=builder.create();
        dialog.show();

        ImageButton cerrar=v.findViewById(R.id.btnClose);
        RecyclerView listaproductos=v.findViewById(R.id.listElegirProd);
        MaterialButton buscar=v.findViewById(R.id.btnbuscaa);
        TextInputLayout buscado=v.findViewById(R.id.txtnomprodu);

        cargarRecyclerProductos("",listaproductos);

        cerrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        buscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cargarRecyclerProductos(buscado.getEditText().getText().toString(),listaproductos);
            }
        });

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

    private void llenarSpiner() {
        String[] Metodos = {"Efectivo", "Tarjeta", "YAPE", "Transferencia Bancaria", "otros"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_activated_1, Metodos);
        spnMetpago.setAdapter(adapter);
    }

    public void cargarRecyclerProductos(String consulta,RecyclerView lista){
        try {
            AdapterEligeProducto adapter = new AdapterEligeProducto(conn.CargarProductoLista(consulta),this);
            lista.setHasFixedSize(true);
            lista.setLayoutManager(new LinearLayoutManager(this));
            lista.setAdapter(adapter);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void itemClick(Productos productos) {
        Toast.makeText(getApplicationContext(),productos.getNombre(),Toast.LENGTH_SHORT).show();
    }
}