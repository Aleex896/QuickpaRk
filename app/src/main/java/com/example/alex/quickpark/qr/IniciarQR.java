package com.example.alex.quickpark.qr;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.alex.quickpark.R;
import com.example.alex.quickpark.gestionplaza.GestionPlaza;
import com.example.alex.quickpark.maps.MapsActivity;
import com.google.android.gms.vision.barcode.Barcode;

import static com.google.zxing.integration.android.IntentIntegrator.REQUEST_CODE;

public class IniciarQR extends AppCompatActivity {

    private String textoqr;
    private String user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_iniciar_qr);

        Button iniqr = (Button) findViewById(R.id.button2);
        TextView titulo = (TextView) findViewById(R.id.tVtitulo);
        TextView texto = (TextView) findViewById(R.id.textExplicacion);
        Button botonatras = (Button) findViewById(R.id.botonAtras);

        titulo.setTypeface(myFont(this));
        texto.setTypeface(myFont(this));

        user = getIntent().getStringExtra("user");

        iniqr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //ActivityCompat.requestPermissions(this,new String[]{android.Manifest.permission.CAMERA}, PackageManager.PERMISSION_GRANTED);

                Intent intent = new Intent(IniciarQR.this, ScanActivity.class);
                startActivityForResult(intent, REQUEST_CODE);
            }
        });

        botonatras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goregistro = new Intent(IniciarQR.this, MapsActivity.class);
                goregistro.putExtra("user",user);
                startActivity(goregistro);
                finish();
                overridePendingTransition(R.anim.slide_from_left, R.anim.slide_to_right);
            }
        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        if(requestCode == REQUEST_CODE && resultCode == RESULT_OK)
            if (data != null) {
                final Barcode barcode = data.getParcelableExtra("barcode");
                textoqr = barcode.displayValue;
                Intent gestionPlaza = new Intent(IniciarQR.this, GestionPlaza.class);
                gestionPlaza.putExtra("textoqr",textoqr);
                startActivity(gestionPlaza);
                finish();
                overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);

            }
    }
    // FUNCION PARA ASIGNAR LA FUENTE
    public static Typeface myFont(Context context) {
        return Typeface.createFromAsset(context.getAssets(), "fonts/Walkway SemiBold.ttf");
    }
    @Override
    public void onBackPressed() {
        Intent goregistro = new Intent(IniciarQR.this, MapsActivity.class);
        goregistro.putExtra("user",user);
        startActivity(goregistro);
        finish();
        overridePendingTransition(R.anim.slide_from_left, R.anim.slide_to_right);
    }
}
