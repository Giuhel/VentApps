package com.venta.ventapps.Actividades;

import androidx.annotation.DrawableRes;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.animation.LayoutTransition;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.transition.AutoTransition;
import android.transition.TransitionManager;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.venta.ventapps.R;

public class Ayuda extends AppCompatActivity {

    TextView texto1,texto2,texto3,texto4,texto5;
    LinearLayout desplegar1,desplegar2,desplegar3,desplegar4,desplegar5;
    ImageView img1,img2,img3,img4,img5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ayuda);

        inicializaComponentes();
    }

    private void inicializaComponentes(){
        texto1=findViewById(R.id.texto1);
        desplegar1=findViewById(R.id.despliega1);
        img1=findViewById(R.id.imgdesplega1);

        texto2=findViewById(R.id.texto2);
        desplegar2=findViewById(R.id.despliega2);
        img2=findViewById(R.id.imgdesplega2);

        texto3=findViewById(R.id.texto3);
        desplegar3=findViewById(R.id.despliega3);
        img3=findViewById(R.id.imgdesplega3);

        texto3=findViewById(R.id.texto3);
        desplegar3=findViewById(R.id.despliega3);
        img3=findViewById(R.id.imgdesplega3);

        texto4=findViewById(R.id.texto4);
        desplegar4=findViewById(R.id.despliega4);
        img4=findViewById(R.id.imgdesplega4);

        texto5=findViewById(R.id.texto5);
        desplegar5=findViewById(R.id.despliega5);
        img5=findViewById(R.id.imgdesplega5);
    }

    public void onClick(View view) {
        switch (view.getId()){
            case R.id.despliega1:
                desplegar1(view);
                break;
            case R.id.despliega2:
                desplegar2(view);
                break;
            case R.id.despliega3:
                desplegar3(view);
                break;
            case R.id.despliega4:
                desplegar4(view);
                break;
            case R.id.despliega5:
                desplegar5(view);
                break;
            case R.id.imgAtrasAyuda:
                finish();
                break;
        }
    }

    private void desplegar1(View view){
        int V=(texto1.getVisibility()==view.GONE)? view.VISIBLE: view.GONE;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            TransitionManager.beginDelayedTransition(desplegar1, new AutoTransition());
        }
        if(texto1.getVisibility()==view.VISIBLE){
            img1.setImageResource(R.drawable.ic_desplegar_abajo);
        }else{
            img1.setImageResource(R.drawable.ic_desplegar_arriba);
        }
        texto1.setVisibility(V);
    }

    private void desplegar2(View view){
        int V=(texto2.getVisibility()==view.GONE)? view.VISIBLE: view.GONE;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            TransitionManager.beginDelayedTransition(desplegar2, new AutoTransition());
        }
        if(texto2.getVisibility()==view.VISIBLE){
            img2.setImageResource(R.drawable.ic_desplegar_abajo);
        }else{
            img2.setImageResource(R.drawable.ic_desplegar_arriba);
        }
        texto2.setVisibility(V);
    }

    private void desplegar3(View view){
        int V=(texto3.getVisibility()==view.GONE)? view.VISIBLE: view.GONE;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            TransitionManager.beginDelayedTransition(desplegar3, new AutoTransition());
        }
        if(texto3.getVisibility()==view.VISIBLE){
            img3.setImageResource(R.drawable.ic_desplegar_abajo);
        }else{
            img3.setImageResource(R.drawable.ic_desplegar_arriba);
        }
        texto3.setVisibility(V);
    }

    private void desplegar4(View view){
        int V=(texto4.getVisibility()==view.GONE)? view.VISIBLE: view.GONE;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            TransitionManager.beginDelayedTransition(desplegar4, new AutoTransition());
        }
        if(texto4.getVisibility()==view.VISIBLE){
            img4.setImageResource(R.drawable.ic_desplegar_abajo);
        }else{
            img4.setImageResource(R.drawable.ic_desplegar_arriba);
        }
        texto4.setVisibility(V);
    }

    private void desplegar5(View view){
        int V=(texto5.getVisibility()==view.GONE)? view.VISIBLE: view.GONE;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            TransitionManager.beginDelayedTransition(desplegar5, new AutoTransition());
        }
        if(texto5.getVisibility()==view.VISIBLE){
            img5.setImageResource(R.drawable.ic_desplegar_abajo);
        }else{
            img5.setImageResource(R.drawable.ic_desplegar_arriba);
        }
        texto5.setVisibility(V);
    }
}