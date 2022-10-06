package com.venta.ventapps.Actividades;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.animation.LayoutTransition;
import android.os.Build;
import android.os.Bundle;
import android.transition.AutoTransition;
import android.transition.TransitionManager;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.venta.ventapps.R;

public class Ayuda extends AppCompatActivity {

    TextView texto1;
    LinearLayout desplegar1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ayuda);

        texto1=findViewById(R.id.texto1);
        desplegar1=findViewById(R.id.despliega1);
        desplegar1.getLayoutTransition().enableTransitionType(LayoutTransition.CHANGING);

    }


    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.despliega1:
                int V=(texto1.getVisibility()==view.GONE)? view.VISIBLE: view.GONE;
                TransitionManager.beginDelayedTransition(desplegar1, new AutoTransition());
                texto1.setVisibility(V);
                break;
            case R.id.imgAtrasAyuda:
                finish();
                break;
        }
    }
}