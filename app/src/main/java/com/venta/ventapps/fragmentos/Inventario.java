package com.venta.ventapps.fragmentos;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;
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
    conexionSQLite conn;
    public static TextView cantCateg;

    public static ArrayList<Categorias> listacategorias;
    public static AdaptadorCategorias adapter;
    public static RecyclerView recyclerlista;

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
                Intent ListFruta = new Intent(getContext(),productos.class);
                startActivity(ListFruta);
                try {
                    finalize();
                } catch (Throwable e) {
                    e.printStackTrace();
                }
            }
        });

        creaCategoria.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogoCrearCategoriaFragment dialogoCrearCategoriaFragment=new DialogoCrearCategoriaFragment();
                dialogoCrearCategoriaFragment.show(getFragmentManager(),"Crear Categoria");
            }
        });

        return vista;
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
            cursor.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void itemClick(Categorias categorias) {
        //Toast.makeText(getContext(),categorias.getNomCategoria(),Toast.LENGTH_SHORT).show();
        DialogoCrearCategoriaFragment dialogoCrearCategoriaFragment=new DialogoCrearCategoriaFragment();
        dialogoCrearCategoriaFragment.comprobar="envio";
        dialogoCrearCategoriaFragment.idenviado=categorias.getIdcategoria();
        dialogoCrearCategoriaFragment.nombreEnviado=categorias.getNomCategoria();
        dialogoCrearCategoriaFragment.show(getActivity().getSupportFragmentManager(),"Crear Categoria");
    }
}