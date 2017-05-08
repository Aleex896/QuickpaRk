package com.example.alex.quickpark;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.Serializable;

public class AjustesUsActivity extends AppCompatActivity implements Serializable {

    public static String nombre;
    public static String apellido;
    public static String email;
    public static String pass;
    public static EditText eTNombre;
    public static EditText eTApellidos;
    public static EditText eTCorreo;
    public static EditText eTContra;
    public static EditText eTContraR;

    private String passR,user;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ajustes_us);

        Button bVolver = (Button) findViewById(R.id.bVolver);
        TextView tVRegi = (TextView) findViewById(R.id.tVIniciar);
        TextView tVDatosUs = (TextView) findViewById(R.id.tVIniciaSesiondatos);
        eTNombre = (EditText) findViewById(R.id.edTCorreoElectronico);
        eTApellidos = (EditText) findViewById(R.id.edTApellidos);
        eTCorreo = (EditText) findViewById(R.id.edTCorreo);
        eTContra = (EditText) findViewById(R.id.edTContraseña);
        eTContraR = (EditText) findViewById(R.id.edTContraseñaR);
        Button  bCrear = (Button) findViewById(R.id.bGuardarDatos);

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

        user = getIntent().getStringExtra("user");

        bVolver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goregistro = new Intent(AjustesUsActivity.this, AjustesActivity.class);
                startActivity(goregistro);
                finish();
                overridePendingTransition(R.anim.slide_from_left, R.anim.slide_to_right);
            }
        });

        bCrear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                pass = eTContra.getText().toString();
                passR = eTContraR.getText().toString();
                nombre = eTNombre.getText().toString();
                apellido = eTApellidos.getText().toString();
                email = eTCorreo.getText().toString();

                if (!pass.equals("") && !passR.equals("") && !nombre.equals("") && !apellido.equals("") && !email.equals("")) {
                    if (passR.equals(pass)) {
                        context = getApplicationContext();
                        try {
                            new UpdateHttp(context, AjustesUsActivity.this).execute();
                            // TODO: PANTALLA DE CARGA
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    } else {
                        Toast toast1 = Toast.makeText(getApplicationContext(), "Contraseñas Incorrectas", Toast.LENGTH_LONG);
                        toast1.show();
                        eTContra.setText(null);
                        eTContraR.setText(null);
                    }
                } else {
                    Toast toast2 = Toast.makeText(getApplicationContext(), "Todos los campos deben rellenarse", Toast.LENGTH_LONG);
                    toast2.show();
                }
            }
        });



    }

    @Override
    public void onBackPressed() {

        Intent goregistro = new Intent(AjustesUsActivity.this, AjustesActivity.class);
        startActivity(goregistro);
        finish();
        overridePendingTransition(R.anim.slide_from_left, R.anim.slide_to_right);
    }

    // FUNCION PARA ASIGNAR LA FUENTE
    public static Typeface myFont(Context context) {
        return Typeface.createFromAsset(context.getAssets(), "fonts/Walkway SemiBold.ttf");
    }
}
