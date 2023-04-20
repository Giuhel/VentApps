package com.venta.ventapps.fragmentos;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.venta.ventapps.Entidades.conexionSQLite;
import com.venta.ventapps.R;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link estadisticas#newInstance} factory method to
 * create an instance of this fragment.
 */
public class estadisticas extends Fragment {

    conexionSQLite datosDB;
    SQLiteDatabase sqLiteDatabase;
    PieDataSet pieDataSet=new PieDataSet(null,null);
    PieDataSet pieDataSet2=new PieDataSet(null,null);
    PieChart pieChart;
    PieChart pieChart2;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public estadisticas() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *cas.
     */
    // TODO: Rename and change types and number of parameters
    public static estadisticas newInstance() {

        Bundle args = new Bundle();

        estadisticas fragment = new estadisticas();
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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View vista = inflater.inflate(R.layout.fragment_estadisticas, container, false);

        pieChart=vista.findViewById(R.id.pastelProdVendidos);
        pieChart2=vista.findViewById(R.id.pastelclientesFrec);
        datosDB=new conexionSQLite(getContext(),"ventApps",null,1);
        sqLiteDatabase=datosDB.getWritableDatabase();

        //Productos
        pastelProductos();

        //Clientes
        pastelCientes();

        return vista;
    }

    private void pastelProductos(){
        pieDataSet.setValues(getDataValuesProductos());
        pieDataSet.setColors(ColorTemplate.MATERIAL_COLORS);
        pieDataSet.setValueTextColor(Color.BLACK);
        pieDataSet.setValueTextSize(16f);
        pieDataSet.setFormLineWidth(4);
        PieData pieData=new PieData(pieDataSet);
        pieChart.setData(pieData);
        pieChart.setDrawHoleEnabled(true);
        pieChart.setEntryLabelColor(Color.BLACK);
        pieChart.getDescription().setEnabled(false);
        pieChart.animate();

        Legend l =pieChart.getLegend();
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.LEFT);
        l.setOrientation(Legend.LegendOrientation.VERTICAL);
        l.setDrawInside(true);
        l.setEnabled(true);
    }
    private void pastelCientes(){
        pieDataSet2.setValues(getDataValuesClientes());
        pieDataSet2.setColors(ColorTemplate.COLORFUL_COLORS);
        pieDataSet2.setValueTextColor(Color.BLACK);
        pieDataSet2.setValueTextSize(16f);
        pieDataSet2.setFormLineWidth(4);
        PieData pieData2=new PieData(pieDataSet2);
        pieChart2.setData(pieData2);
        pieChart2.setDrawHoleEnabled(true);
        pieChart2.setEntryLabelColor(Color.BLACK);
        pieChart2.getDescription().setEnabled(false);
        pieChart2.animate();

        Legend l =pieChart2.getLegend();
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.LEFT);
        l.setOrientation(Legend.LegendOrientation.VERTICAL);
        l.setDrawInside(true);
        l.setEnabled(true);
    }


    private ArrayList<PieEntry> getDataValuesProductos(){
        ArrayList<PieEntry> datavalues= new ArrayList<>();
        Cursor cursor=datosDB.getValues();

        for (int i=0;i<cursor.getCount();i++){
            cursor.moveToNext();
            datavalues.add(new PieEntry(cursor.getInt(0), String.valueOf(cursor.getString(1))));
        }
        return datavalues;
    }
    private ArrayList<PieEntry> getDataValuesClientes(){
        ArrayList<PieEntry> datavalues= new ArrayList<>();
        Cursor cursor=datosDB.getValuesClientes();

        for (int i=0;i<cursor.getCount();i++){
            cursor.moveToNext();
            datavalues.add(new PieEntry(cursor.getInt(0), String.valueOf(cursor.getString(1))));
        }
        return datavalues;
    }
}