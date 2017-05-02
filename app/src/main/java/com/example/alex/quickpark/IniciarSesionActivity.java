package com.example.alex.quickpark;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class IniciarSesionActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_iniciarses);

        Button bVolver = (Button) findViewById(R.id.bVolver);
        TextView tvIni = (TextView) findViewById(R.id.tVIniciar);
        TextView tvInici = (TextView) findViewById(R.id.tVIniciaSesiondatos);
        EditText correo = (EditText) findViewById(R.id.edTCorreoElectronico);
        EditText eTContra = (EditText) findViewById(R.id.edTContraseña);
        Button biniciar = (Button) findViewById(R.id.bIniciar);

        eTContra.setInputType(InputType.TYPE_CLASS_TEXT |
                InputType.TYPE_TEXT_VARIATION_PASSWORD);

        tvIni.setTypeface(myFont(this));
        tvInici.setTypeface(myFont(this));
        correo.setTypeface(myFont(this));
        eTContra.setTypeface(myFont(this));
        biniciar.setTypeface(myFont(this));


        bVolver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goregistro = new Intent(IniciarSesionActivity.this, FirstScreen.class);
                startActivity(goregistro);
                overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
                finish();
            }
        });
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            Intent goregistro = new Intent(IniciarSesionActivity.this, FirstScreen.class);
            startActivity(goregistro);
            overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
            finish();
            return true;
        }

        return super.onKeyDown(keyCode, event);
    }

    // FUNCION PARA ASIGNAR LA FUENTE
    public static Typeface myFont(Context context) {
        return Typeface.createFromAsset(context.getAssets(), "fonts/Walkway SemiBold.ttf");
    }
}
