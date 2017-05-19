package com.example.alex.quickpark.ajustesusuario;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TextView;

import com.example.alex.quickpark.R;

import java.io.Serializable;

public class ListaVehiculosActivity extends AppCompatActivity implements Serializable {

    public static String user;
    public static LinearLayout llListado;
    public static TableLayout table;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_vehiculos);

        user = getIntent().getStringExtra("user");
        llListado =(LinearLayout)findViewById(R.id.llListado);
        table = (TableLayout) findViewById(R.id.tablelayout);

        try{
            context=getApplicationContext();
            new ListaCarsHttp(context,ListaVehiculosActivity.this).execute();
        }catch (Exception ex){
            ex.printStackTrace();
        }

        Button btVolver = (Button) findViewById(R.id.bVolver);
        ImageButton btAddCar = (ImageButton)findViewById(R.id.btAddCar);
        TextView tvLCar = (TextView)findViewById(R.id.tVTusVehiculos);
        TextView tvListado = (TextView)findViewById(R.id.tVListado);
        TextView trMatricula = (TextView)findViewById(R.id.trMatricula);
        TextView trColor = (TextView)findViewById(R.id.trColor);

        tvListado.setTypeface(myFont(this));
        tvLCar.setTypeface(myFont(this));
        trColor.setTypeface(myFont(this));
        trMatricula.setTypeface(myFont(this));

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
        goregistro.putExtra("user",user);
        startActivity(goregistro);
        finish();
        overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
    }
}
