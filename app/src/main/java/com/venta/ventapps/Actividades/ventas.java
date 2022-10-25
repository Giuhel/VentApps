package com.venta.ventapps.Actividades;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputLayout;
import com.venta.ventapps.Adapters.AdapterEligeProducto;
import com.venta.ventapps.Entidades.Productos;
import com.venta.ventapps.Entidades.conexionSQLite;
import com.venta.ventapps.R;
import com.venta.ventapps.utilidades.Utilidades;
import android.app.DatePickerDialog;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;

public class ventas extends AppCompatActivity implements AdapterEligeProducto.RecylerItemCLick{

    LinearLayout inputF;
    TextView fec,numeroventa;
    private int dia, mes, anio;
    CardView seleccionarProd;

    TextInputLayout nomproducto,cantiproducto,monto;

    Spinner spnMetpago;
    conexionSQLite conn;

    int siguienteNumVenta,siguienteNDetalle;

    AlertDialog dialog = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ventas);

        inputF = findViewById(R.id.inputfecha);
        fec = findViewById(R.id.txtfecha);
        spnMetpago = findViewById(R.id.MetodoPago);
        seleccionarProd = findViewById(R.id.seleccprod);
        numeroventa = findViewById(R.id.numventa);
        nomproducto = findViewById(R.id.ventaNombreProducto);
        cantiproducto = findViewById(R.id.ventaCantProd);
        monto = findViewById(R.id.ventaMontoPagar);

        conn=new conexionSQLite(getApplicationContext(),"ventApps",null,1);

        ObtenerFecha();
        NumeroVenta();
        IdDetalle();
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

        dialog=builder.create();
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

    private void dialogo_Ingresar_Cantidad(String nomprod,double precv,int idprod){
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        LayoutInflater inflater=getLayoutInflater();
        View v=inflater.inflate(R.layout.ingresar_cantidad_avender,null);
        builder.setView(v);

        final AlertDialog dialogo=builder.create();
        dialogo.show();

        ImageButton cerrar=v.findViewById(R.id.cant_Cerrar);
        MaterialButton agrega=v.findViewById(R.id.ingresarCantidad);
        EditText cant=v.findViewById(R.id.cant_cantidad);

        cerrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogo.dismiss();
            }
        });
        agrega.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                enviaProducto(nomprod,Integer.parseInt(cant.getText().toString()),precv);
                dialogo.dismiss();
                dialog.dismiss();
            }
        });
    }

    private void enviaProducto(String pro,int cant,double prev){
        nomproducto.getEditText().setText(pro);
        cantiproducto.getEditText().setText(cant+"");
        double subtotal;
        subtotal=cant*prev;
        monto.getEditText().setText(subtotal+"");
    }

    @Override
    public void itemClick(Productos productos) {
        Toast.makeText(getApplicationContext(),productos.getId()+"",Toast.LENGTH_SHORT).show();
        dialogo_Ingresar_Cantidad(productos.getNombre(),productos.getPreciov(),productos.getId());
    }


    //*++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    //guardar detalle

    private void IdDetalle(){
        SQLiteDatabase db=conn.getReadableDatabase();
        try {
            Cursor cursor =db.rawQuery("select max("+Utilidades.DETALLEV_ID+") from "+Utilidades.TABLA_DETAVENTA,null);
            cursor.moveToFirst();
            siguienteNDetalle=cursor.getInt(0);
            if(siguienteNDetalle==0){
                siguienteNDetalle=1;
                Toast.makeText(getApplicationContext(),siguienteNDetalle+"",Toast.LENGTH_SHORT).show();
            }else{
                siguienteNDetalle=siguienteNDetalle+1;
                Toast.makeText(getApplicationContext(),siguienteNDetalle+"",Toast.LENGTH_SHORT).show();
            }
            cursor.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void GuardarDEtalleVenta(int idprod){
        SQLiteDatabase db=conn.getWritableDatabase();
        ContentValues values=new ContentValues();

        values.put(Utilidades.DETALLEV_ID,siguienteNDetalle);
        values.put(Utilidades.DETALLEV_NUMEROV,siguienteNumVenta);
        values.put(Utilidades.DETALLEV_IDPROD,idprod);
        values.put(Utilidades.DETALLEV_NOMPROD,nomproducto.getEditText().getText().toString());
        values.put(Utilidades.DETALLEV_CANTIDAD,cantiproducto.getEditText().getText().toString());

        Long idresultante=db.insert(Utilidades.TABLA_DETAVENTA,Utilidades.DETALLEV_ID,values);

        Toast.makeText(getApplicationContext(),"ID Registro: "+idresultante,Toast.LENGTH_SHORT).show();
        limpiacamposDetalle();
    }

    private void limpiacamposDetalle() {
        IdDetalle();
        nomproducto.getEditText().setText("");
        cantiproducto.getEditText().setText("");
        monto.getEditText().setText("");
    }
}