package com.venta.ventapps.Actividades;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputLayout;
import com.venta.ventapps.Actividades.clientes.DetalleCliente;
import com.venta.ventapps.Actividades.clientes.ListadeCientes;
import com.venta.ventapps.Adapters.AdaptadorClientes;
import com.venta.ventapps.Adapters.AdapterEligeProducto;
import com.venta.ventapps.Adapters.AdapterProdAgregados;
import com.venta.ventapps.Entidades.Clientes;
import com.venta.ventapps.Entidades.Productos;
import com.venta.ventapps.Entidades.conexionSQLite;
import com.venta.ventapps.Entidades.detalleVenta;
import com.venta.ventapps.R;
import com.venta.ventapps.utilidades.Utilidades;
import android.app.DatePickerDialog;
import android.content.ContentValues;
import android.content.Intent;
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

import java.util.ArrayList;
import java.util.Calendar;

public class ventas extends AppCompatActivity implements AdapterEligeProducto.RecylerItemCLick,
                            AdapterProdAgregados.RecylerItemCLick{

    LinearLayout inputF;
    TextView fec,numeroventa;
    private int dia, mes, anio;
    CardView seleccionarProd;

    TextInputLayout nomproducto,cantiproducto,monto;
    MaterialButton agregaProd;

    Spinner spnMetpago;
    conexionSQLite conn;


    RecyclerView recyclerProdAgregados;

    int siguienteNumVenta,siguienteNDetalle;

    AlertDialog dialog = null;
    int idproducto;
    double precioprod;
    public static String accion;
    double subtotal;
    double montoTotal; //para obtener monto total cuando se elimine un producto de la lista en venta

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
        agregaProd = findViewById(R.id.botonAgregaProducto);

        conn=new conexionSQLite(getApplicationContext(),"ventApps",null,1);

        ObtenerFecha();
        NumeroVenta();
        IdDetalle();
        llenarSpiner();
        ObtenerMontoTotal(numeroventa.getText().toString());
        botones();
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.inputfecha:
                seleccionarFecha();
                break;
            case R.id.seleccprod:
                abrirDialogoElegirProdcuto();
                break;
            case R.id.irProdAgregados:
                AbrirDialogoProdcutosAgregados();
                break;
            case R.id.btnAtrasV:
                finish();
                break;
        }
    }

    private void botones(){
        agregaProd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                GuardarDEtalleVenta(idproducto,precioprod);
            }
        });
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

    private void cargarRecyclerProdAgregados(RecyclerView lista,String nventa){
        SQLiteDatabase db=conn.getReadableDatabase();
        detalleVenta detalle=null;
        ArrayList<detalleVenta> listaDetalle = new ArrayList<>();

        Cursor cursor =db.rawQuery("select * from "+ Utilidades.TABLA_DETAVENTA + " WHERE "+
                Utilidades.DETALLEV_NUMEROV+"="+nventa,null);
        while (cursor.moveToNext()){
            detalle=new detalleVenta();
            detalle.setId(cursor.getInt(0));
            detalle.setNumeroventa(cursor.getString(1));
            detalle.setIdProd(cursor.getInt(2));
            detalle.setNomProd(cursor.getString(3));
            detalle.setCant(cursor.getInt(4));
            detalle.setPrecio(cursor.getDouble(5));
            listaDetalle.add(detalle);
        }
        AdapterProdAgregados adapter=new AdapterProdAgregados(listaDetalle,this);
        lista.setLayoutManager(new LinearLayoutManager(this));
        lista.setAdapter(adapter);
        db.close();
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
                idproducto=idprod;
                precioprod=precv;
                enviaProducto(nomprod,Integer.parseInt(cant.getText().toString()),precv);
                dialogo.dismiss();
                dialog.dismiss();
            }
        });
    }

    private void enviaProducto(String pro,int cant,double prev){
        nomproducto.getEditText().setText(pro);
        cantiproducto.getEditText().setText(cant+"");
        subtotal=subtotal+(cant*prev);
        monto.getEditText().setText(subtotal+"");
    }

    private void AbrirDialogoProdcutosAgregados(){
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        LayoutInflater inflater=getLayoutInflater();
        View v=inflater.inflate(R.layout.dialogo_productos_agregados_v,null);
        builder.setView(v);

        final AlertDialog dialogoAP=builder.create();
        dialogoAP.show();

        ImageButton cerrar=v.findViewById(R.id.btnCloseProdAgregados);
        recyclerProdAgregados=v.findViewById(R.id.listProductosAgregados);

        cargarRecyclerProdAgregados(recyclerProdAgregados,numeroventa.getText().toString());

        cerrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogoAP.dismiss();
            }
        });

    }

    @Override
    public void itemClickEligeProd(Productos productos) {
        Toast.makeText(getApplicationContext(),productos.getId()+"",Toast.LENGTH_SHORT).show();
        dialogo_Ingresar_Cantidad(productos.getNombre(),productos.getPreciov(),productos.getId());
    }

    @Override
    public void itemClickProdAgregados(detalleVenta detalle) {
        if(accion=="borrar"){
            Toast.makeText(getApplicationContext(),detalle.getNomProd()+"Borrar",Toast.LENGTH_SHORT).show();
            eliminarProductoAgregado(detalle.getId());
        }else{
            if(detalle.getCant()<=1){
                Toast.makeText(getApplicationContext(),detalle.getNomProd()+"Borrar",Toast.LENGTH_SHORT).show();
                eliminarProductoAgregado(detalle.getId());
            }else{
                Toast.makeText(getApplicationContext(),detalle.getNomProd()+"disminuye",Toast.LENGTH_SHORT).show();
                disminuyeProductoAgregado(detalle.getIdProd(),detalle.getCant(),detalle.getNumeroventa());
            }

        }
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

    private void GuardarDEtalleVenta(int idprod,double precio){
        SQLiteDatabase db=conn.getWritableDatabase();
        ContentValues values=new ContentValues();

        values.put(Utilidades.DETALLEV_ID,siguienteNDetalle);
        values.put(Utilidades.DETALLEV_NUMEROV,numeroventa.getText().toString());
        values.put(Utilidades.DETALLEV_IDPROD,idprod);
        values.put(Utilidades.DETALLEV_NOMPROD,nomproducto.getEditText().getText().toString());
        values.put(Utilidades.DETALLEV_CANTIDAD,Integer.parseInt(cantiproducto.getEditText().getText().toString()));
        values.put(Utilidades.DETALLEV_PRECIOPROD,precio);

        db.insert(Utilidades.TABLA_DETAVENTA,Utilidades.DETALLEV_ID,values);
        Toast.makeText(getApplicationContext(),"Se Agrego El Producto",Toast.LENGTH_SHORT).show();
        limpiacamposDetalle();
    }

    private void limpiacamposDetalle() {
        IdDetalle();
        nomproducto.getEditText().setText("");
        cantiproducto.getEditText().setText("");
    }

    private void disminuyeProductoAgregado(int idp,int cant,String nventa){
        SQLiteDatabase db=conn.getWritableDatabase();
        String [] parametros={idp+"",nventa};
        cant=cant-1;
        ContentValues values=new ContentValues();
        values.put(Utilidades.DETALLEV_CANTIDAD,cant);
        db.update(Utilidades.TABLA_DETAVENTA,values,Utilidades.DETALLEV_IDPROD+"=? and "
                +Utilidades.DETALLEV_NUMEROV+"=?",parametros);
        Toast.makeText(getApplicationContext(),"Se actualizo",Toast.LENGTH_SHORT).show();
        db.close();
        cargarRecyclerProdAgregados(recyclerProdAgregados,numeroventa.getText().toString());
        IdDetalle();
        ObtenerMontoTotal(numeroventa.getText().toString());
    }

    private void eliminarProductoAgregado(int idv){
        SQLiteDatabase db=conn.getWritableDatabase();
        String [] parametros={idv+""};

        db.delete(Utilidades.TABLA_DETAVENTA,Utilidades.DETALLEV_ID+"=?",parametros);
        db.close();
        Toast.makeText(getApplicationContext(),"Se elimino",Toast.LENGTH_SHORT).show();
        cargarRecyclerProdAgregados(recyclerProdAgregados,numeroventa.getText().toString());
        IdDetalle();
        ObtenerMontoTotal(numeroventa.getText().toString());
    }

    private void ObtenerMontoTotal(String nuventaa){
        SQLiteDatabase db=conn.getReadableDatabase();
        try {
            Cursor cursor =db.rawQuery("select sum("+Utilidades.DETALLEV_CANTIDAD+"*"+Utilidades.DETALLEV_PRECIOPROD+") " +
                    "from "+Utilidades.TABLA_DETAVENTA+" where "+Utilidades.DETALLEV_NUMEROV+"="+nuventaa,null);
            cursor.moveToFirst();
            montoTotal=cursor.getDouble(0);
            if(montoTotal==0){
                monto.getEditText().setText("");
            }else{
                monto.getEditText().setText(montoTotal+"");
            }
            cursor.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}