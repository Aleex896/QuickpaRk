package com.example.alex.quickpark.ajustesusuario;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;


import com.example.alex.quickpark.R;
import com.example.alex.quickpark.maps.MapsActivity;
import com.example.alex.quickpark.qr.IniciarQR;

import java.io.Serializable;

public class AjustesActivity extends AppCompatActivity implements Serializable {

    String user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ajustes);

        ImageButton bMaps = (ImageButton) findViewById(R.id.bMaps);
        ImageButton bQr = (ImageButton) findViewById(R.id.bQr);
        ImageButton bAju = (ImageButton) findViewById(R.id.bPref);
        ImageButton bAjuUs = (ImageButton) findViewById(R.id.bAjustesUsu);
        ImageButton bCars = (ImageButton) findViewById(R.id.bAjustesVehi);

        user =getIntent().getStringExtra("user");

        bAjuUs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goajuusu = new Intent(AjustesActivity.this, AjustesUsActivity.class);
                goajuusu.putExtra("user",user);
                startActivity(goajuusu);
                finish();
                overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
            }
        });

        bMaps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goaju = new Intent(AjustesActivity.this, MapsActivity.class);
                goaju.putExtra("user",user);
                startActivity(goaju);
                finish();
                overridePendingTransition(R.anim.slide_from_left, R.anim.slide_to_right);
            }
        });

        bQr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goQr = new Intent(AjustesActivity.this, IniciarQR.class);
                goQr.putExtra("user",user);
                startActivity(goQr);
                finish();
                overridePendingTransition(R.anim.slide_from_left, R.anim.slide_to_right);
            }
        });

        bAju.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        bCars.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent goCars = new Intent(AjustesActivity.this, ListaVehiculosActivity.class);
                goCars.putExtra("user",user);
                startActivity(goCars);
                finish();
                overridePendingTransition(R.anim.slide_from_left, R.anim.slide_to_right);
            }
        });

    }
    @Override
    public void onBackPressed() {

        Intent goregistro = new Intent(AjustesActivity.this, MapsActivity.class);
        goregistro.putExtra("user",user);
        startActivity(goregistro);
        finish();
        overridePendingTransition(R.anim.slide_from_left, R.anim.slide_to_right);
    }
}
