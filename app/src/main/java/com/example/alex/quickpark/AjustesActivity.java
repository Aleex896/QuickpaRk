package com.example.alex.quickpark;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class AjustesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ajustes);

        ImageButton bMaps = (ImageButton) findViewById(R.id.bMaps);
        ImageButton bQr = (ImageButton) findViewById(R.id.bQr);
        ImageButton bAju = (ImageButton) findViewById(R.id.bPref);
        ImageButton bAjuUs = (ImageButton) findViewById(R.id.bAjustesUsu);

        bAjuUs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goajuusu = new Intent(AjustesActivity.this, AjustesUsActivity.class);
                startActivity(goajuusu);
                finish();
                overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
            }
        });

        bMaps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goaju = new Intent(AjustesActivity.this, MapsActivity.class);
                startActivity(goaju);
                finish();
                overridePendingTransition(R.anim.slide_from_left, R.anim.slide_to_right);
            }
        });

        bQr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goQr = new Intent(AjustesActivity.this, QrActivity.class);
                startActivity(goQr);
                finish();
                overridePendingTransition(R.anim.slide_from_left, R.anim.slide_to_right);
            }
        });

        bAju.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

    }
    @Override
    public void onBackPressed() {

        Intent goregistro = new Intent(AjustesActivity.this, MapsActivity.class);
        startActivity(goregistro);
        finish();
        overridePendingTransition(R.anim.slide_from_left, R.anim.slide_to_right);
    }
}
