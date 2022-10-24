package com.venta.ventapps.Actividades.productos;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputLayout;
import com.venta.ventapps.Adapters.AdaptadorProductos;
import com.venta.ventapps.Entidades.Productos;
import com.venta.ventapps.Entidades.conexionSQLite;
import com.venta.ventapps.R;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class ListaProductos extends AppCompatActivity implements AdaptadorProductos.RecylerItemCLick{

    TextInputLayout nombreProd;
    MaterialButton buscar;
    RecyclerView listaproductosRcv;

    AdaptadorProductos adaptadorProductos;
    conexionSQLite conn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_productos);

        nombreProd=findViewById(R.id.nombreprodcutobuscar);
        buscar=findViewById(R.id.buscarProducto);
        listaproductosRcv=findViewById(R.id.listaproductoss);

        conn=new conexionSQLite(getApplicationContext(),"ventApps",null,1);

        cargarRecyclerProductos("");

        buscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cargarRecyclerProductos(nombreProd.getEditText().getText().toString());
            }
        });

    }

    public void cargarRecyclerProductos(String consulta){
        try {
            adaptadorProductos = new AdaptadorProductos(conn.CargarProductoLista(consulta),this);
            listaproductosRcv.setHasFixedSize(true);
            listaproductosRcv.setLayoutManager(new LinearLayoutManager(this));
            listaproductosRcv.setAdapter(adaptadorProductos);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void onClickear(View view) {
        switch (view.getId()){
            case R.id.atraslistaProductos:
                finish();
                break;

        }
    }

    @Override
    public void itemClick(Productos productos) {
        Intent miintent=new Intent(getApplicationContext(), DetalleProducto.class);
        Bundle mibundle=new Bundle();
        mibundle.putInt("ID",productos.getId());
        miintent.putExtras(mibundle);
        startActivity(miintent);
        finish();
    }
}