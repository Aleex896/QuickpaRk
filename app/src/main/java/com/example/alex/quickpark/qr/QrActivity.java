package com.example.alex.quickpark.qr;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.alex.quickpark.maps.MapsActivity;
import com.example.alex.quickpark.R;
import com.example.alex.quickpark.ScanActivity;
import com.example.alex.quickpark.ajustesusuario.AjustesActivity;
import com.google.android.gms.vision.barcode.Barcode;

import java.io.Serializable;

import static com.google.zxing.integration.android.IntentIntegrator.REQUEST_CODE;

public class QrActivity extends AppCompatActivity implements Serializable {

    TextView result;
    String textoqr,user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_qr_screen);
        ImageButton bMaps = (ImageButton) findViewById(R.id.bMaps);
        ImageButton bQr = (ImageButton) findViewById(R.id.bQr);
        ImageButton bAju = (ImageButton) findViewById(R.id.bPref);
        ImageButton bIniQr = (ImageButton) findViewById(R.id.biniQr);
        result = (TextView) findViewById(R.id.result);

        user = getIntent().getStringExtra("user");


        ActivityCompat.requestPermissions(this,
                new String[]{android.Manifest.permission.CAMERA}, PackageManager.PERMISSION_GRANTED);


        bIniQr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(QrActivity.this, ScanActivity.class);
                startActivityForResult(intent, REQUEST_CODE);
            }
        });

        bMaps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goQr = new Intent(QrActivity.this, MapsActivity.class);
                goQr.putExtra("user",user);
                startActivity(goQr);
                finish();
                overridePendingTransition(R.anim.slide_from_left, R.anim.slide_to_right);
            }
        });

        bQr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        bAju.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goaju = new Intent(QrActivity.this, AjustesActivity.class);
                goaju.putExtra("user",user);
                startActivity(goaju);
                finish();
                overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        if(requestCode == REQUEST_CODE && resultCode == RESULT_OK)
        {
            if(data!= null) {
                final Barcode barcode = data.getParcelableExtra("barcode");
                result.post(new Runnable() {
                    @Override
                    public void run() {
                        textoqr = barcode.displayValue;
                        result.setText(textoqr);
                        Toast.makeText(QrActivity.this, "texto:"+barcode.displayValue, Toast.LENGTH_SHORT).show();
                    }
                });
            }
        }
    }

    @Override
    public void onBackPressed() {
        Intent goregistro = new Intent(QrActivity.this, MapsActivity.class);
        goregistro.putExtra("user",user);
        startActivity(goregistro);
        finish();
        overridePendingTransition(R.anim.slide_from_left, R.anim.slide_to_right);
    }
}
