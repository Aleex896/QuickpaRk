package com.example.alex.quickpark.screeninicial;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.example.alex.quickpark.R;
import com.example.alex.quickpark.inicioregistro.IniciarSesionActivity;
import com.example.alex.quickpark.inicioregistro.RegistroActivity;

public class FirstScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_login);



        // ASIGNACION DE LAS FUENTES A LOS BOTONES
        Button bregi = (Button) findViewById(R.id.bCrearCuent);
        Button binises = (Button) findViewById(R.id.bIniSes);


        bregi.setTypeface(myFont(this));
        binises.setTypeface(myFont(this));

        bregi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goregistro = new Intent(FirstScreen.this, RegistroActivity.class);
                startActivity(goregistro);
                overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
            }
        });

        binises.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goini = new Intent(FirstScreen.this, IniciarSesionActivity.class);
                startActivity(goini);
                overridePendingTransition(R.anim.slide_from_left, R.anim.slide_to_right);
            }
        });
    }

    // FUNCION PARA ASIGNAR LA FUENTE //////////////////////////////////////////////////////////////
    public static Typeface myFont(Context context) {
        return Typeface.createFromAsset(context.getAssets(), "fonts/Walkway SemiBold.ttf");
    }
    ////////////////////////////////////////////////////////////////////////////////////////////////

    // FUNCION PARA DOBLE CLICK EN BACK = CERRAR APP ///////////////////////////////////////////////
    @Override
    public void onBackPressed() {
        finishAffinity();
    }
    ////////////////////////////////////////////////////////////////////////////////////////////////
}


