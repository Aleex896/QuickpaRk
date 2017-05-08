package com.example.alex.quickpark;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import java.io.Serializable;

public class ListaVehiculosActivity extends AppCompatActivity implements Serializable {

    String user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_vehiculos);

        Button btVolver = (Button) findViewById(R.id.bVolver);
        ImageButton btAddCar = (ImageButton)findViewById(R.id.btAddCar);
        TextView tvLCar = (TextView)findViewById(R.id.tVTusVehiculos);
        TextView tvListado = (TextView)findViewById(R.id.tVListado);

        tvListado.setTypeface(myFont(this));
        tvLCar.setTypeface(myFont(this));

        user = getIntent().getStringExtra("user");

        btVolver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goajustes = new Intent(ListaVehiculosActivity.this, AjustesActivity.class);
                goajustes.putExtra("user",user);
                startActivity(goajustes);
                finish();
                overridePendingTransition(R.anim.slide_from_left, R.anim.slide_to_right);
            }
        });

        btAddCar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent goadd = new Intent(ListaVehiculosActivity.this, AddCarActivity.class);
                goadd.putExtra("user",user);
                startActivity(goadd);
                finish();
                overridePendingTransition(R.anim.slide_from_left, R.anim.slide_to_right);
            }
        });
    }

    public static Typeface myFont(Context context) {
        return Typeface.createFromAsset(context.getAssets(), "fonts/Walkway SemiBold.ttf");
    }

    @Override
    public void onBackPressed() {
        Intent goregistro = new Intent(ListaVehiculosActivity.this, AjustesActivity.class);
        startActivity(goregistro);
        finish();
        overridePendingTransition(R.anim.slide_from_left, R.anim.slide_to_right);
    }
}
