package com.venta.ventapps.dialogos;

import android.app.Activity;
import android.app.Dialog;
import android.app.FragmentManager;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputLayout;
import com.venta.ventapps.Adapters.AdaptadorCategorias;
import com.venta.ventapps.Entidades.Categorias;
import com.venta.ventapps.Entidades.conexionSQLite;
import com.venta.ventapps.R;
import com.venta.ventapps.fragmentos.Inventario;
import com.venta.ventapps.utilidades.Utilidades;

import java.util.ArrayList;

public class DialogoCrearCategoriaFragment extends DialogFragment implements AdaptadorCategorias.RecylerItemCLick {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    ImageButton cerrar;
    public String comprobar;
    TextView id;
    TextInputLayout nombre;
    MaterialButton registrar;

    Activity activity;

    conexionSQLite conn;
    int siguienteId;
    String accion="guardar";

    public int idenviado;
    public String nombreEnviado;


    public DialogoCrearCategoriaFragment() {
        // Required empty public constructor
    }

    @Override
    public Dialog onCreateDialog( Bundle savedInstanceState) {
        return CrearDialogoCrearCAtegoria();
    }

    private Dialog CrearDialogoCrearCAtegoria() {
        AlertDialog.Builder builder=new AlertDialog.Builder(getActivity());

        LayoutInflater inflater=getActivity().getLayoutInflater();
        View v=inflater.inflate(R.layout.fragment_dialogo_crear_categoria,null);
        builder.setView(v);

        cerrar=v.findViewById(R.id.btnCerrarD);
        id=v.findViewById(R.id.idcateloriga);
        nombre=v.findViewById(R.id.nombrecategoria);
        registrar=v.findViewById(R.id.RegistraCategoria);

        conn=new conexionSQLite(activity,"ventApps",null,1);

        if(comprobar==null){
            IdCategoria();
        }else{
            id.setText(idenviado+"");
            nombre.getEditText().setText(nombreEnviado);
        }

        eventoBotones();

        return builder.create();
    }

    private void eventoBotones() {
        cerrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
        registrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RrgistraCategoria();
            }
        });
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if(context instanceof Activity){
            this.activity= (Activity) context;
        }else {
            throw new RuntimeException(context.toString()
                    +" must implement OnFragmentinteractionListener");
        }
    }



    private void IdCategoria(){
        SQLiteDatabase db=conn.getReadableDatabase();
        try {
            Cursor cursor =db.rawQuery("select max("+ Utilidades.ID_CATEGORIA+") from "+Utilidades.TABLA_CATEGORIA,null);
            cursor.moveToFirst();
            siguienteId=cursor.getInt(0);
            if(siguienteId==0){
                siguienteId=1;
                id.setText(siguienteId+"");
            }else{
                siguienteId=siguienteId+1;
                id.setText(siguienteId+"");
            }
            cursor.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void RrgistraCategoria() {
        SQLiteDatabase db=conn.getWritableDatabase();
        ContentValues values=new ContentValues();

        values.put(Utilidades.ID_CATEGORIA,siguienteId);
        values.put(Utilidades.NOMBRE_CATEGORIA,nombre.getEditText().getText().toString());

        Long idresultante=db.insert(Utilidades.TABLA_CATEGORIA,Utilidades.ID_CATEGORIA,values);

        Toast.makeText(activity,"ID Registro: "+idresultante,Toast.LENGTH_SHORT).show();
        limpiacampos();
        TotalCategorias();
        consultarListaCategorias();
    }

    private void limpiacampos() {
        IdCategoria();
        nombre.getEditText().setText("");
    }

    public void TotalCategorias(){
        SQLiteDatabase db=conn.getReadableDatabase();
        int total;
        try {
            Cursor cursor =db.rawQuery("select count(*) from "+ Utilidades.TABLA_CATEGORIA,null);
            cursor.moveToFirst();
            total=cursor.getInt(0);
            Inventario.cantCateg.setText(total+"");
            cursor.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void consultarListaCategorias() {
        SQLiteDatabase db=conn.getReadableDatabase();
        Categorias categorias=null;
        Inventario.listacategorias = new ArrayList<>();

        Cursor cursor =db.rawQuery("select * from "+ Utilidades.TABLA_CATEGORIA,null);
        while (cursor.moveToNext()){
            categorias=new Categorias();
            categorias.setIdcategoria(cursor.getInt(0));
            categorias.setNomCategoria(cursor.getString(1));
            Inventario.listacategorias.add(categorias);
        }
        Inventario.adapter=new AdaptadorCategorias(Inventario.listacategorias,  this);
        Inventario.recyclerlista.setAdapter(Inventario.adapter);
        db.close();
    }

    @Override
    public void itemClick(Categorias categorias) {
        Toast.makeText(activity,categorias.getNomCategoria(),Toast.LENGTH_SHORT).show();

    }


}