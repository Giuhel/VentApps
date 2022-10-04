package com.venta.ventapps.fragmentos;

import android.content.Intent;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.google.android.material.button.MaterialButton;
import com.venta.ventapps.Actividades.productos;
import com.venta.ventapps.MainActivity;
import com.venta.ventapps.R;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Inventario#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Inventario extends Fragment {

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

    MaterialButton crearProd;
    MainActivity mainActivity=new MainActivity();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View vista = inflater.inflate(R.layout.fragment_inventario, container, false);

        crearProd=vista.findViewById(R.id.BtnCreaProdu);


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

        return vista;
    }
}