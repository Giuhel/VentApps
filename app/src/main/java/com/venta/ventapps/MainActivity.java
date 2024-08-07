package com.venta.ventapps;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.venta.ventapps.Entidades.conexionSQLite;
import com.venta.ventapps.fragmentos.Inventario;
import com.venta.ventapps.fragmentos.acerca_de;
import com.venta.ventapps.fragmentos.dashboard;
import com.venta.ventapps.fragmentos.estadisticas;

public class MainActivity extends AppCompatActivity {

    BottomNavigationView btnmenu;
    public static Fragment fragment;
    public static FragmentManager manager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        conexionSQLite conn=new conexionSQLite(this,"ventApps",null,1);

        inicializarObjetos();
        initvalues();
        AccionBtnMenu();
    }

    private void inicializarObjetos(){
        btnmenu=findViewById(R.id.menunavegador);
    }

    private void initvalues(){
        manager=getSupportFragmentManager();
        inicializarDrashbord();
    }

    private void inicializarDrashbord(){
        fragment = dashboard.newInstance();
        abrirFragment(fragment);
    }

    private void AccionBtnMenu(){
        btnmenu.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int idmenu=item.getItemId();
                switch (idmenu){
                    case R.id.menu_home:
                        fragment = dashboard.newInstance();
                        abrirFragment(fragment);
                        return true;
                    case R.id.menu_inventario:
                        fragment = Inventario.newInstance();
                        abrirFragment(fragment);
                        return true;
                    case R.id.menu_estadisticas:
                        fragment = estadisticas.newInstance();
                        abrirFragment(fragment);
                        return true;
                    case R.id.menu_acercade:
                        fragment = acerca_de.newInstance();
                        abrirFragment(fragment);
                        return true;
                }
                return false;
            }
        });
    }

    public static void abrirFragment(Fragment fragment){
        manager.beginTransaction()
                .replace(R.id.freamecontenedor,fragment).commit();
    }
}