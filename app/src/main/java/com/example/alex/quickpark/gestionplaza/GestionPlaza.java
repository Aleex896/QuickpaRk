package com.example.alex.quickpark.gestionplaza;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.alex.quickpark.R;
import com.example.alex.quickpark.qr.IniciarQR;

public class GestionPlaza extends AppCompatActivity {

    private String textoqr;
    public TextView mClockView;
    TextView clock;
    TextView fecha;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gestion_plaza);

        Button continuar = (Button)findViewById(R.id.bContinuar);

        continuar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goselectiempo = new Intent(GestionPlaza.this, SelectorTiempo.class);
                startActivity(goselectiempo);
                finish();
                overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
            }
        });


        textoqr = getIntent().getStringExtra("textoqr");
        Toast.makeText(GestionPlaza.this, "texto:" + textoqr, Toast.LENGTH_SHORT).show();
    }


    @Override
    public void onBackPressed() {
        Intent goregistro = new Intent(GestionPlaza.this, IniciarQR.class);
        startActivity(goregistro);
        finish();
        overridePendingTransition(R.anim.slide_from_left, R.anim.slide_to_right);
    }






}
