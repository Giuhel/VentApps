package com.venta.ventapps.Actividades;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.venta.ventapps.R;

public class productos extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_productos);
    }

    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btnAtrasP:
                finish();break;
        }
    }
}