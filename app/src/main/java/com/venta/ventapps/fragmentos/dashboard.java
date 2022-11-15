package com.venta.ventapps.fragmentos;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.venta.ventapps.Actividades.Ayuda;
import com.venta.ventapps.Actividades.clientes.clientes;
import com.venta.ventapps.Actividades.ventas.ListaVentas;
import com.venta.ventapps.Actividades.ventas.ventas;
import com.venta.ventapps.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link dashboard#newInstance} factory method to
 * create an instance of this fragment.
 */
public class dashboard extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public dashboard() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment dashboard.
     */
    // TODO: Rename and change types and number of parameters
    public static dashboard newInstance() {

        Bundle args = new Bundle();

        dashboard fragment = new dashboard();
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

    LinearLayout menuClientes,menuAyuda,menuventa,listaVentas;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View vista = inflater.inflate(R.layout.fragment_dashboard, container, false);

        menuClientes=vista.findViewById(R.id.menuClientes);
        menuAyuda=vista.findViewById(R.id.menuAyuda);
        menuventa=vista.findViewById(R.id.menuVenta);
        listaVentas=vista.findViewById(R.id.menuListaVentas);

        menuClientes();
        menuAyudaa();
        menuVenta();
        menulistaVentas();

        return vista;
    }

    private void menuClientes(){
        menuClientes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), clientes.class);
                startActivity(intent);
            }
        });
    }

    private void menuAyudaa(){
        menuAyuda.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), Ayuda.class);
                startActivity(intent);
            }
        });
    }

    private void menuVenta(){
        menuventa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), ventas.class);
                startActivity(intent);
            }
        });
    }

    private void menulistaVentas(){
        listaVentas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), ListaVentas.class);
                startActivity(intent);
            }
        });
    }
}