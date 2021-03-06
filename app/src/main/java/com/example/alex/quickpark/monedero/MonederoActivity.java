package com.example.alex.quickpark.monedero;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.ButtonBarLayout;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TableLayout;
import android.widget.TextView;

import com.example.alex.quickpark.R;
import com.example.alex.quickpark.ajustesusuario.AjustesActivity;
import com.example.alex.quickpark.maps.MapsActivity;
import com.example.alex.quickpark.pagos.PagoActivity;
import com.example.alex.quickpark.pagos.RecargarActivity;

import java.io.Serializable;

public class MonederoActivity extends AppCompatActivity implements Serializable {

    Button btRecargar,bVolver;
    public static String user;
    public static TextView tvSaldo;
    public static ScrollView llListado;
    public static TableLayout table;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_monedero);

        user =getIntent().getStringExtra("user");
        tvSaldo = (TextView)findViewById(R.id.tvSaldo);
        llListado = (ScrollView)findViewById(R.id.svlistado);
        table = (TableLayout)findViewById(R.id.tablelayout);


        btRecargar = (Button)findViewById(R.id.btRecargar);
        bVolver = (Button)findViewById(R.id.botonAtras);

        bVolver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goajustes = new Intent(MonederoActivity.this, AjustesActivity.class);
                goajustes.putExtra("user",user);
                startActivity(goajustes);
                finish();
                overridePendingTransition(R.anim.slide_from_left, R.anim.slide_to_right);
            }
        });

        btRecargar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goajustes = new Intent(MonederoActivity.this, RecargarActivity.class);
                goajustes.putExtra("user",user);
                startActivity(goajustes);
                finish();
                overridePendingTransition(R.anim.slide_from_left, R.anim.slide_to_right);
            }
        });

        new MonederoHttp(this).execute();
        new TransaccionesHttp(this).execute();
    }

    @Override
    public void onBackPressed() {
        Intent goregistro = new Intent(MonederoActivity.this, AjustesActivity.class);
        goregistro.putExtra("user",user);
        startActivity(goregistro);
        finish();
        overridePendingTransition(R.anim.slide_from_left, R.anim.slide_to_right);
    }


}
