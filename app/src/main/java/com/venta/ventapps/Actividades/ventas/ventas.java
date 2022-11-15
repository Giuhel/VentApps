package com.venta.ventapps.Actividades.ventas;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputLayout;
import com.venta.ventapps.Adapters.AdaptadorClientesVenta;
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
        AdapterProdAgregados.RecylerItemCLick, AdaptadorClientesVenta.RecylerItemCLickVentas {

    LinearLayout inputF;
    TextView fec,numeroventa,prodagregados;
    private int dia, mes, anio;

    TextInputLayout nomproducto,cantiproducto,monto,cliente;
    MaterialButton agregaProd,RegistraVenta,elegirCliente;

    Spinner spnMetpago;
    conexionSQLite conn;

    RecyclerView recyclerProdAgregados;

    int siguienteNumVenta,siguienteNDetalle;
    int idclientee=0;

    AlertDialog dialog = null;
    AlertDialog dialogcliente = null;
    int idproducto,stockActual,canti;//canti variablea para controlar el stock
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
        numeroventa = findViewById(R.id.numventa);
        nomproducto = findViewById(R.id.ventaNombreProducto);
        cantiproducto = findViewById(R.id.ventaCantProd);
        monto = findViewById(R.id.ventaMontoPagar);
        cliente=findViewById(R.id.ventaCliente);
        agregaProd = findViewById(R.id.botonAgregaProducto);
        RegistraVenta = findViewById(R.id.btnCrearVenta);
        prodagregados = findViewById(R.id.txtprodAgregados);
        elegirCliente = findViewById(R.id.btnelegirCliente);

        conn=new conexionSQLite(getApplicationContext(),"ventApps",null,1);

        ObtenerFecha();
        NumeroVenta();
        IdDetalle();
        llenarSpiner();
        CantidadProductosAgregados(numeroventa.getText().toString());
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
                if(nomproducto.getEditText().getText().toString().isEmpty()||cantiproducto.getEditText().getText().toString().isEmpty()
                 ||monto.getEditText().getText().toString().isEmpty()){
                    Toast.makeText(getApplicationContext(),"Seleccione producto",Toast.LENGTH_SHORT).show();
                }else{
                    GuardarDEtalleVenta(idproducto,precioprod);
                    DisminuirStock(canti,idproducto);
                }
            }
        });
        RegistraVenta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(monto.getEditText().getText().toString().isEmpty()){

                }else {
                    GuardarVenta();
                }
            }
        });
        elegirCliente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogo_elegirCliente();
            }
        });
    }

    private void dialogo_elegirCliente() {
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        LayoutInflater inflater=getLayoutInflater();
        View v=inflater.inflate(R.layout.dialogo_elegircliente,null);
        builder.setView(v);

        dialogcliente=builder.create();
        dialogcliente.show();

        ImageButton cerrar=v.findViewById(R.id.btnCloseEligeCliente);
        RecyclerView listaclientes=v.findViewById(R.id.listElegirCliente);

        CargarRecyclerCLientes(listaclientes);

        cerrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogcliente.dismiss();
            }
        });
    }

    private void CargarRecyclerCLientes(RecyclerView lista){
        SQLiteDatabase db=conn.getReadableDatabase();
        Clientes clientes=null;
        ArrayList<Clientes> listaclientes = new ArrayList<>();

        Cursor cursor =db.rawQuery("select * from "+ Utilidades.TABLA_CLIENTE ,null);
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
        AdaptadorClientesVenta adapter=new AdaptadorClientesVenta(listaclientes,this);
        lista.setLayoutManager(new LinearLayoutManager(this));
        lista.setAdapter(adapter);
        db.close();
    }

    private void NumeroVenta(){
        SQLiteDatabase db=conn.getReadableDatabase();
        try {
            Cursor cursor =db.rawQuery("select max("+ Utilidades.NUMERO_VENTA+") from "+Utilidades.TABLA_VENTA,null);
            cursor.moveToFirst();
            String numventa=cursor.getString(0);
            if(numventa==null){
                numventa="000";
            }
            siguienteNumVenta= Integer.parseInt(numventa);
            if(siguienteNumVenta==0){
                siguienteNumVenta=1;
                numeroventa.setText("00"+siguienteNumVenta);
            }else{
                if(siguienteNumVenta<=9){
                    siguienteNumVenta=siguienteNumVenta+1;
                    numeroventa.setText("00"+siguienteNumVenta);
                }else if(siguienteNumVenta>9 && siguienteNumVenta<=99){
                    siguienteNumVenta=siguienteNumVenta+1;
                    numeroventa.setText("0"+siguienteNumVenta);
                }else{
                    siguienteNumVenta=siguienteNumVenta+1;
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

    private void cargarRecyclerProdAgregados(RecyclerView lista,String nvent){
        SQLiteDatabase db=conn.getReadableDatabase();
        detalleVenta detalle=null;
        ArrayList<detalleVenta> listaDetalle = new ArrayList<>();

        Cursor cursor =db.rawQuery("select * from "+Utilidades.TABLA_DETAVENTA+ " WHERE "+Utilidades.DETALLEV_NUMEROV+"='"+nvent+"'",null);
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
        EditText cantii=v.findViewById(R.id.cant_cantidad);

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
                canti=Integer.parseInt(cantii.getText().toString());
                if(!validarAgregado(idprod,numeroventa.getText().toString())){
                    obtenerStockActual(idprod);
                    if(stockActual<canti){
                        Toast.makeText(ventas.this, "Stock Insuficiente", Toast.LENGTH_SHORT).show();
                    }else{
                        enviaProducto(nomprod,Integer.parseInt(cantii.getText().toString()),precv);
                        dialogo.dismiss();
                        dialog.dismiss();
                    }
                }else{
                    Toast.makeText(ventas.this, "El producto ya esta agregado", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void enviaProducto(String pro,int cant,double prev){
        nomproducto.getEditText().setText(pro);
        cantiproducto.getEditText().setText(cant+"");
        subtotal=subtotal+(cant*prev);
        monto.getEditText().setText(subtotal+"");
        montoTotal=subtotal;
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
        dialogo_Ingresar_Cantidad(productos.getNombre(),productos.getPreciov(),productos.getId());
    }

    @Override
    public void itemClickProdAgregados(detalleVenta detalle) {
        if(accion=="borrar"){
            eliminarProductoAgregado(detalle.getId());
            AumentaStock(detalle.getCant(),detalle.getIdProd());
        }else if(accion=="disminuye"){
            if(detalle.getCant()<=1){
                eliminarProductoAgregado(detalle.getId());
                AumentaStock(detalle.getCant(),detalle.getIdProd());
            }else{
                disminuyeProductoAgregado(detalle.getIdProd(),detalle.getCant(),detalle.getNumeroventa());
            }
        }else{
            aumentaProductoAgregado(detalle.getIdProd(),detalle.getCant(),detalle.getNumeroventa());
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
            }else{
                siguienteNDetalle=siguienteNDetalle+1;
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

    //validadar que si el producto ya esta agregado
    private boolean validarAgregado(int idp,String nventaaa){
        SQLiteDatabase db=conn.getReadableDatabase();
        int valida;
        Cursor cursor =db.rawQuery("select count(*) from "+Utilidades.TABLA_DETAVENTA+" WHERE " +
                    Utilidades.DETALLEV_IDPROD+"="+idp+" and "+Utilidades.DETALLEV_NUMEROV+"='"+nventaaa+"'",null);
        cursor.moveToFirst();
        valida=cursor.getInt(0);
        if(valida==0){
            return false;
        }else{
            return true;
        }
    }
    //************************************************************************************

    private void limpiacamposDetalle() {
        IdDetalle();
        nomproducto.getEditText().setText("");
        cantiproducto.getEditText().setText("");
        CantidadProductosAgregados(numeroventa.getText().toString());
    }

    private void disminuyeProductoAgregado(int idp,int cant,String nventa){
        SQLiteDatabase db=conn.getWritableDatabase();
        String [] parametros={idp+"",nventa};
        cant=cant-1;
        ContentValues values=new ContentValues();
        values.put(Utilidades.DETALLEV_CANTIDAD,cant);
        db.update(Utilidades.TABLA_DETAVENTA,values,Utilidades.DETALLEV_IDPROD+"=? and "
                +Utilidades.DETALLEV_NUMEROV+"=?",parametros);
        db.close();
        cargarRecyclerProdAgregados(recyclerProdAgregados,numeroventa.getText().toString());
        IdDetalle();
        ObtenerMontoTotal(numeroventa.getText().toString());
        CantidadProductosAgregados(numeroventa.getText().toString());
        subtotal=montoTotal;
        AumentaStock(1,idp);
    }

    private void aumentaProductoAgregado(int idp,int cant,String nventa){
        SQLiteDatabase db=conn.getWritableDatabase();
        String [] parametros={idp+"",nventa};
        cant=cant+1;
        ContentValues values=new ContentValues();
        values.put(Utilidades.DETALLEV_CANTIDAD,cant);
        db.update(Utilidades.TABLA_DETAVENTA,values,Utilidades.DETALLEV_IDPROD+"=? and "
                +Utilidades.DETALLEV_NUMEROV+"=?",parametros);
        db.close();
        cargarRecyclerProdAgregados(recyclerProdAgregados,numeroventa.getText().toString());
        IdDetalle();
        ObtenerMontoTotal(numeroventa.getText().toString());
        CantidadProductosAgregados(numeroventa.getText().toString());
        subtotal=montoTotal;
        DisminuirStock(1,idp);
    }

    private void eliminarProductoAgregado(int idv){
        SQLiteDatabase db=conn.getWritableDatabase();
        String [] parametros={idv+""};

        db.delete(Utilidades.TABLA_DETAVENTA,Utilidades.DETALLEV_ID+"=?",parametros);
        db.close();
        cargarRecyclerProdAgregados(recyclerProdAgregados,numeroventa.getText().toString());
        IdDetalle();
        ObtenerMontoTotal(numeroventa.getText().toString());
        CantidadProductosAgregados(numeroventa.getText().toString());
        subtotal=montoTotal;
    }

    private void ObtenerMontoTotal(String nuventaa){
        SQLiteDatabase db=conn.getReadableDatabase();
        try {
            Cursor cursor =db.rawQuery("select sum("+Utilidades.DETALLEV_CANTIDAD+"*"+Utilidades.DETALLEV_PRECIOPROD+") " +
                    "from "+Utilidades.TABLA_DETAVENTA+" where "+Utilidades.DETALLEV_NUMEROV+"='"+nuventaa+"'",null);
            cursor.moveToFirst();
            montoTotal=cursor.getDouble(0);
            if(montoTotal==0){
                monto.getEditText().setText("");
                subtotal=montoTotal;
            }else{
                monto.getEditText().setText(montoTotal+"");
                subtotal=montoTotal;
            }
            cursor.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void GuardarVenta(){
        SQLiteDatabase db=conn.getWritableDatabase();
        ContentValues values=new ContentValues();

        values.put(Utilidades.NUMERO_VENTA,numeroventa.getText().toString());
        values.put(Utilidades.FECHA_VENTA,fec.getText().toString());
        values.put(Utilidades.MONTO_VENTA,subtotal);
        values.put(Utilidades.IDCLIENTE_VENTA,idclientee);
        values.put(Utilidades.CLIENTE_VENTA,cliente.getEditText().getText().toString());
        values.put(Utilidades.METPAGO_VENTA,spnMetpago.getSelectedItem().toString());

        db.insert(Utilidades.TABLA_VENTA,Utilidades.NUMERO_VENTA,values);
        Toast.makeText(getApplicationContext(),"Se Registro la venta",Toast.LENGTH_SHORT).show();
        limpiamosCamposPostVEnta();
        CantidadProductosAgregados(numeroventa.getText().toString());
        ObtenerMontoTotal(numeroventa.getText().toString());
    }

    private void limpiamosCamposPostVEnta() {
        NumeroVenta();
        nomproducto.getEditText().setText("");
        cantiproducto.getEditText().setText("");
        monto.getEditText().setText("");
        cliente.getEditText().setText("");
    }

    private void CantidadProductosAgregados(String nvent) {
        SQLiteDatabase db = conn.getReadableDatabase();
        int total;
        try {
            Cursor cursor =db.rawQuery("select count(*) from "+Utilidades.TABLA_DETAVENTA+ " WHERE "+Utilidades.DETALLEV_NUMEROV+"='"+nvent+"'",null);
            cursor.moveToFirst();
            total = cursor.getInt(0);
            prodagregados.setText("Productos Agregados: "+total);
            cursor.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void obtenerStockActual(int idpro){
        SQLiteDatabase db=conn.getWritableDatabase();
        Productos productos=null;
        Cursor cursor =db.rawQuery("select * from "+ Utilidades.TABLA_PRODUCTOS +" where "+Utilidades.ID_PRODUCTO+"="+idpro,null);
        while (cursor.moveToNext()){
            productos=new Productos();
            productos.setId(cursor.getInt(0));
            productos.setNombre(cursor.getString(1));
            productos.setCantidad(cursor.getInt(2));
        }
        stockActual=productos.getCantidad();
    }

    private void DisminuirStock(int cant,int idpro){
        obtenerStockActual(idpro);
        SQLiteDatabase db=conn.getWritableDatabase();
        String [] parametros={idpro+""};
        stockActual=stockActual-cant;
        ContentValues values=new ContentValues();
        values.put(Utilidades.CANTIDAD_PRODUCTO,stockActual);
        db.update(Utilidades.TABLA_PRODUCTOS,values,Utilidades.ID_PRODUCTO+"=?",parametros);
        db.close();
    }

    private void AumentaStock(int cant,int idpro){
        obtenerStockActual(idpro);
        SQLiteDatabase db=conn.getWritableDatabase();
        String [] parametros={idpro+""};
        stockActual=stockActual+cant;
        ContentValues values=new ContentValues();
        values.put(Utilidades.CANTIDAD_PRODUCTO,stockActual);
        db.update(Utilidades.TABLA_PRODUCTOS,values,Utilidades.ID_PRODUCTO+"=?",parametros);
        db.close();
    }

    @Override
    public void itemClickClientesVentas(Clientes clientes) {
        cliente.getEditText().setText(clientes.getNombre());
        idclientee=clientes.getId();
        dialogcliente.dismiss();
    }
}