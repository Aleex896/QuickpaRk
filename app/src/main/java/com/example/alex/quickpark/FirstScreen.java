package com.example.alex.quickpark;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class FirstScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_login);


        // ASIGNACION DE LAS FUENTES A LOS BOTONES
        Button bregi = (Button) findViewById(R.id.bCrearCuent);
        Button Binises = (Button) findViewById(R.id.bIniSes);
        bregi.setTypeface(myFont(this));
        Binises.setTypeface(myFont(this));

        bregi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goregistro = new Intent(FirstScreen.this, RegistroActivity.class);
                startActivity(goregistro);
            }
        });
    }

    // FUNCION PARA ASIGNAR LA FUENTE
    public static Typeface myFont(Context context) {
        return Typeface.createFromAsset(context.getAssets(), "fonts/Walkway SemiBold.ttf");
    }


    @Override
    protected void onResume() {
        super.onResume();
    }
}


