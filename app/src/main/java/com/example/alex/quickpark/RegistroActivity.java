package com.example.alex.quickpark;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class RegistroActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        Button bVolver = (Button) findViewById(R.id.bVolver);
        TextView tVRegi = (TextView) findViewById(R.id.tVRegi);
        TextView tVDatosUs = (TextView) findViewById(R.id.tVDatosUsu);
        EditText eTNombre = (EditText) findViewById(R.id.edTNombre);
        EditText eTApellidos = (EditText) findViewById(R.id.edTApellidos);
        EditText eTCorreo = (EditText) findViewById(R.id.edTCorreo);
        EditText eTContra = (EditText) findViewById(R.id.edTContraseña);
        EditText eTContraR = (EditText) findViewById(R.id.edTContraseñaR);
        Button  bCrear = (Button) findViewById(R.id.bCrear);

        eTContra.setInputType(InputType.TYPE_CLASS_TEXT |
                InputType.TYPE_TEXT_VARIATION_PASSWORD);

        eTContraR.setInputType(InputType.TYPE_CLASS_TEXT |
                InputType.TYPE_TEXT_VARIATION_PASSWORD);

        tVRegi.setTypeface(myFont(this));
        tVDatosUs.setTypeface(myFont(this));
        eTNombre.setTypeface(myFont(this));
        eTApellidos.setTypeface(myFont(this));
        eTCorreo.setTypeface(myFont(this));
        eTContra.setTypeface(myFont(this));
        eTContraR.setTypeface(myFont(this));
        bCrear.setTypeface(myFont(this));


        bVolver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goregistro = new Intent(RegistroActivity.this, FirstScreen.class);
                startActivity(goregistro);
                overridePendingTransition(R.anim.slide_from_left, R.anim.slide_to_right);
            }
        });


    }

    // FUNCION PARA ASIGNAR LA FUENTE
    public static Typeface myFont(Context context) {
        return Typeface.createFromAsset(context.getAssets(), "fonts/Walkway SemiBold.ttf");
    }
}
