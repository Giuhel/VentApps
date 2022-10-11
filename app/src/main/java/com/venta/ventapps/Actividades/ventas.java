package com.venta.ventapps.Actividades;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.venta.ventapps.R;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class ventas extends AppCompatActivity {

    LinearLayout inputF;
    TextView fec;
    private int dia, mes, anio;

    Spinner spnMetpago;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ventas);

        inputF = findViewById(R.id.inputfecha);
        fec = findViewById(R.id.txtfecha);
        spnMetpago = findViewById(R.id.MetodoPago);

        ObtenerFecha();
        llenarSpiner();

    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.inputfecha:
                seleccionarFecha();
                break;
            case R.id.btnAtrasV:
                finish();
                break;
        }
    }

    private void seleccionarFecha() {
        final Calendar c = Calendar.getInstance();
        dia = c.get(Calendar.DAY_OF_MONTH);
        mes = c.get(Calendar.MONTH);
        anio = c.get(Calendar.YEAR);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int año, int mes, int dia) {
                String diaformato, mesformato;
                if (dia < 10) {
                    diaformato = "0" + String.valueOf(dia);
                } else {
                    diaformato = String.valueOf(dia);
                }
                int MES = mes + 1;
                if (MES < 10) {
                    mesformato = "0" + String.valueOf(MES);
                } else {
                    mesformato = String.valueOf(MES);
                }
                fec.setText(diaformato + "/" + mesformato + "/" + año);
            }
        }, anio, mes, dia);
        datePickerDialog.show();
    }

    public void ObtenerFecha() {
        int dia, mes, año;
        String diaformato, mesformato, fecha;
        Calendar fecc = Calendar.getInstance();
        dia = fecc.get(Calendar.DAY_OF_MONTH);
        mes = fecc.get(Calendar.MONTH);
        mes = mes + 1;
        año = fecc.get(Calendar.YEAR);

        if (dia < 10) {
            diaformato = "0" + String.valueOf(dia);
        } else {
            diaformato = String.valueOf(dia);
        }
        if (mes < 10) {
            mesformato = "0" + String.valueOf(mes);
        } else {
            mesformato = String.valueOf(mes);
        }

        fecha = diaformato + "/" + mesformato + "/" + año;
        fec.setText(fecha);
    }

    private void llenarSpiner() {
        String[] Metodos = {"Efectivo", "Tarjeta", "YAPE", "Transferencia Bancaria", "otros"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_activated_1, Metodos);
        spnMetpago.setAdapter(adapter);
    }
}