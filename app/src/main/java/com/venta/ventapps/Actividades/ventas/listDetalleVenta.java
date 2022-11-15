package com.venta.ventapps.Actividades.ventas;

import androidx.appcompat.app.AppCompatActivity;
import com.venta.ventapps.R;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

public class listDetalleVenta extends AppCompatActivity {

    TextView numventaa;
    ImageButton atras;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_detalle_venta);

        numventaa=findViewById(R.id.numventaDetalle);
        atras=findViewById(R.id.atraaaas);

        Bundle miBundle=this.getIntent().getExtras();
        if(miBundle!=null){
            String ide=miBundle.getString("NUMV");

            numventaa.setText(ide);
        }

        eventosClick();
    }

    private void eventosClick(){
        atras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}