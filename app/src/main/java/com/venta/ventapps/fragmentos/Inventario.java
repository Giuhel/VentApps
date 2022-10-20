package com.venta.ventapps.fragmentos;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.transition.AutoTransition;
import android.transition.TransitionManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputLayout;
import com.venta.ventapps.Actividades.clientes.DetalleCliente;
import com.venta.ventapps.Actividades.clientes.ListadeCientes;
import com.venta.ventapps.Actividades.productos;
import com.venta.ventapps.Adapters.AdaptadorCategorias;
import com.venta.ventapps.Entidades.Categorias;
import com.venta.ventapps.Entidades.conexionSQLite;
import com.venta.ventapps.R;
import com.venta.ventapps.dialogos.DialogoCrearCategoriaFragment;
import com.venta.ventapps.utilidades.Utilidades;
import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Inventario#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Inventario extends Fragment implements AdaptadorCategorias.RecylerItemCLick {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Inventario() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment Inventario.
     */
    // TODO: Rename and change types and number of parameters
    public static Inventario newInstance() {

        Bundle args = new Bundle();

        Inventario fragment = new Inventario();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    MaterialButton crearProd,creaCategoria;
    public conexionSQLite conn;
    public  static TextView cantCateg;

    ArrayList<Categorias> listacategorias;
    AdaptadorCategorias adapter;
    RecyclerView recyclerlista;

    int SiguienteID;

    String accion="guardar";
    int cantCate=0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View vista = inflater.inflate(R.layout.fragment_inventario, container, false);

        crearProd=vista.findViewById(R.id.BtnCreaProdu);
        creaCategoria=vista.findViewById(R.id.BtnCreaCatego);
        cantCateg=vista.findViewById(R.id.txttotalCategorias);
        recyclerlista=vista.findViewById(R.id.Rcvlistcategoria);

        conn=new conexionSQLite(getContext(),"ventApps",null,1);
        recyclerlista.setLayoutManager(new LinearLayoutManager(getContext()));

        consultarListaCategorias();

        TotalCategorias();

        crearProd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(cantCate>0){
                    Intent ListFruta = new Intent(getContext(),productos.class);
                    startActivity(ListFruta);
                    try {
                        finalize();
                    } catch (Throwable e) {
                        e.printStackTrace();
                    }
                }else{
                    Toast.makeText(getContext(),"Primero Debe Crear Una Categoria",Toast.LENGTH_LONG).show();
                }
            }
        });

        creaCategoria.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RregistraCategoria("","","");
                //DialogoCrearCategoriaFragment dialogoCrearCategoriaFragment=new DialogoCrearCategoriaFragment();
                //dialogoCrearCategoriaFragment.show(getFragmentManager(),"Crear Categoria");
            }
        });

        return vista;
    }

    public void RregistraCategoria(String idd,String nom,String c) {
        AlertDialog.Builder builder=new AlertDialog.Builder(getContext());
        LayoutInflater inflater=getLayoutInflater();
        View v=inflater.inflate(R.layout.fragment_dialogo_crear_categoria,null);
        builder.setView(v);

        final AlertDialog dialog=builder.create();
        dialog.show();

        ImageButton cerrar=v.findViewById(R.id.btnCerrarD);
        TextView id=v.findViewById(R.id.idcateloriga);
        TextInputLayout nombre=v.findViewById(R.id.nombrecategoria);
        MaterialButton registrar=v.findViewById(R.id.RegistraCategoria);
        MaterialButton eliminar=v.findViewById(R.id.EliminaCategoria);

        if(idd.equals("") & nom.equals("") & c.equals("")){
            IdCategoria(id);
            accion="guardar";
        }else{
            id.setText(idd);
            nombre.getEditText().setText(nom);
            registrar.setText("Editar");
            registrar.setCornerRadius(20);
            eliminar.setVisibility(View.VISIBLE);
            accion=c;
        }

        cerrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        registrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(accion.equals("guardar")){
                    accionREgstrar(id,nombre);
                }else{
                    accionEditar(id,nombre);
                }
            }
        });

        eliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogoEliminar(id,dialog);
            }
        });
    }

    private void accionEditar(TextView id, TextInputLayout nombre) {
        SQLiteDatabase db=conn.getWritableDatabase();
        String [] parametros={id.getText().toString()};

        ContentValues values=new ContentValues();
        values.put(Utilidades.NOMBRE_CATEGORIA,nombre.getEditText().getText().toString());

        db.update(Utilidades.TABLA_CATEGORIA,values,Utilidades.ID_CATEGORIA+"=?",parametros);
        Toast.makeText(getContext(),"Se actualizo la categoria",Toast.LENGTH_SHORT).show();
        db.close();
        TotalCategorias();
        consultarListaCategorias();
    }

    private void accionEliminar(TextView id) {
        SQLiteDatabase db=conn.getWritableDatabase();
        String [] parametros={id.getText().toString()};

        db.delete(Utilidades.TABLA_CATEGORIA,Utilidades.ID_CATEGORIA+"=?",parametros);
        db.close();
        Toast.makeText(getContext(),"SE elimino la categoria",Toast.LENGTH_SHORT).show();
        TotalCategorias();
        consultarListaCategorias();
        IdCategoria(id);
    }

    private void DialogoEliminar(TextView id, AlertDialog dialog){
        AlertDialog.Builder builder=new AlertDialog.Builder(getContext());
        builder.setTitle("Advertencia");
        builder.setMessage("¿Estas seguro de eliminar la categoria?")
                .setPositiveButton("SI", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        accionEliminar(id);
                        dialog.dismiss();
                    }
                })
                .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                }).setCancelable(false)
                .show();
    }

    public void accionREgstrar(TextView id,TextInputLayout nombre){
        SQLiteDatabase db=conn.getWritableDatabase();
        ContentValues values=new ContentValues();

        values.put(Utilidades.ID_CATEGORIA, id.getText().toString());
        values.put(Utilidades.NOMBRE_CATEGORIA, nombre.getEditText().getText().toString());

        Long idresultante=db.insert(Utilidades.TABLA_CATEGORIA,Utilidades.ID_CATEGORIA,values);

        Toast.makeText(getContext(),"Se Registro la Categoria",Toast.LENGTH_SHORT).show();
        TotalCategorias();
        consultarListaCategorias();
        IdCategoria(id);
        limpiacampos(nombre);
    }

    private void IdCategoria(TextView id){
        SQLiteDatabase db=conn.getReadableDatabase();
        try {
            Cursor cursor =db.rawQuery("select max("+ Utilidades.ID_CATEGORIA+") from "+Utilidades.TABLA_CATEGORIA,null);
            cursor.moveToFirst();
            SiguienteID=cursor.getInt(0);
            if(SiguienteID==0){
                SiguienteID=1;
                id.setText(SiguienteID+"");
            }else{
                SiguienteID=SiguienteID+1;
                id.setText(SiguienteID+"");
            }
            cursor.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void consultarListaCategorias() {
        SQLiteDatabase db=conn.getReadableDatabase();
        Categorias categorias=null;
        listacategorias = new ArrayList<>();

        Cursor cursor =db.rawQuery("select * from "+ Utilidades.TABLA_CATEGORIA,null);
        while (cursor.moveToNext()){
            categorias=new Categorias();
            categorias.setIdcategoria(cursor.getInt(0));
            categorias.setNomCategoria(cursor.getString(1));
            listacategorias.add(categorias);
        }
        adapter=new AdaptadorCategorias(listacategorias,this);
        recyclerlista.setAdapter(adapter);
        db.close();
    }

    public void TotalCategorias(){
        SQLiteDatabase db=conn.getReadableDatabase();
        int total;
        try {
            Cursor cursor =db.rawQuery("select count(*) from "+ Utilidades.TABLA_CATEGORIA,null);
            cursor.moveToFirst();
            total=cursor.getInt(0);
            cantCateg.setText(total+"");
            cantCate=total;
            cursor.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void limpiacampos(TextInputLayout nombre) {
        nombre.getEditText().setText("");
    }

    @Override
    public void itemClick(Categorias categorias) {
        RregistraCategoria(categorias.getIdcategoria()+"",categorias.getNomCategoria(),"editar");
    }
}