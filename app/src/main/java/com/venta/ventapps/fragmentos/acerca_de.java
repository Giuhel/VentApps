package com.venta.ventapps.fragmentos;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.venta.ventapps.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link acerca_de#newInstance} factory method to
 * create an instance of this fragment.
 *
 */
public class acerca_de extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    
    public static acerca_de newInstance() {

        Bundle args = new Bundle();

        acerca_de fragment = new acerca_de();
        fragment.setArguments(args);
        return fragment;
    }

    public acerca_de() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    LinearLayout instragram,facebook,tiktok;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View vista =inflater.inflate(R.layout.fragment_acerca_de, container, false);

        instragram=vista.findViewById(R.id.btnIG);
        facebook=vista.findViewById(R.id.btnFB);
        tiktok=vista.findViewById(R.id.btntiktok);

        eventoBotones();

        return vista;
    }

    private void eventoBotones() {

        Intent intent=null;

        instragram.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                llamarWEB(intent,"ig");
            }
        });
        facebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                llamarWEB(intent,"fb");
            }
        });
        tiktok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                llamarWEB(intent,"tiktok");
            }
        });
    }
    private void llamarWEB(Intent intent,String web){
        String url = null;
        if(web=="ig"){
            url="https://www.instagram.com/helio_pizarro/";
        }else if(web=="fb"){
            url="https://www.facebook.com/helio.pizarro/";
        }else if(web=="tiktok"){
            url="https://www.tiktok.com/@giuhel_pip?lang=es";
        }
        intent= new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        startActivity(intent);
    }

    public void onClick(View view) {
        //Intent intent=null;
        switch (view.getId()){
            case R.id.btnIG:
                //intent= new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.instagram.com/helio_pizarro/"));
                Toast.makeText(getContext(),"instagram",Toast.LENGTH_SHORT).show();
                break;
        }
        //startActivity(intent);
    }


    public void enlace(View view) {
    }
}