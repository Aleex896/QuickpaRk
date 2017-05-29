package com.example.alex.quickpark.qr;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.alex.quickpark.R;
import com.example.alex.quickpark.gestionplaza.GestionPlaza;
import com.example.alex.quickpark.maps.MapsActivity;
import com.google.android.gms.vision.barcode.Barcode;

import java.io.Serializable;

import static com.google.zxing.integration.android.IntentIntegrator.REQUEST_CODE;

public class IniciarQR extends AppCompatActivity implements Serializable {

    private static final int MY_PERMISSIONS_REQUEST_READ_CONTACTS = 1;
    private String textoqr;
    private String user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_iniciar_qr);

        if (ContextCompat.checkSelfPermission(IniciarQR.this,
                Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(IniciarQR.this,
                    Manifest.permission.CAMERA)) {

                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.

            } else {

                // No explanation needed, we can request the permission.

                ActivityCompat.requestPermissions(IniciarQR.this,
                        new String[]{Manifest.permission.CAMERA},
                        MY_PERMISSIONS_REQUEST_READ_CONTACTS);

                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
        }

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
                ActivityCompat.requestPermissions(IniciarQR.this,new String[]{android.Manifest.permission.CAMERA}, PackageManager.PERMISSION_GRANTED);
                try{
                    Thread.sleep(500);
                }catch (Exception ex)
                {

                }
                Intent intent = new Intent(IniciarQR.this, ScanActivity.class);
                intent.putExtra("user",user);
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
                gestionPlaza.putExtra("user",user);
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
