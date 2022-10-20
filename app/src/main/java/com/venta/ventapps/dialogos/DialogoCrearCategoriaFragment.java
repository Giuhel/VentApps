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

public class DialogoCrearCategoriaFragment extends DialogFragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    Activity activity;

    public DialogoCrearCategoriaFragment() {
        // Required empty public constructor
    }

    @Override
    public Dialog onCreateDialog( Bundle savedInstanceState) {
        return CrearDialogoCrearCAtegoria();
    }

    public Dialog CrearDialogoCrearCAtegoria() {
        AlertDialog.Builder builder=new AlertDialog.Builder(getActivity());

        LayoutInflater inflater=getActivity().getLayoutInflater();
        View v=inflater.inflate(R.layout.fragment_dialogo_crear_categoria,null);
        builder.setView(v);

        return builder.create();
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
}